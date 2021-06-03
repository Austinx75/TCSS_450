package edu.uw.harmony.UI.Weather;

import android.app.Activity;
import android.app.Application;
import android.graphics.Color;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import edu.uw.harmony.R;
import edu.uw.harmony.UI.Home.HomeFragment;
import edu.uw.harmony.UI.model.LocationViewModel;
import edu.uw.harmony.UI.settings.SettingsViewModel;
import edu.uw.harmony.databinding.FragmentHomeBinding;
import edu.uw.harmony.databinding.FragmentWeatherBinding;
import edu.uw.harmony.databinding.FragmentWeatherLocationBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntFunction;

import static edu.uw.harmony.util.WeatherUtils.determineImageFromDescription;

public class WeatherViewModel extends AndroidViewModel {
    Activity mActivity;
    private String mJwt;

    private WeatherLocationSource mWeatherLocationSource;
    private String mZipCode;
    private double mLatitude;
    private double mLongitude;

    private MutableLiveData<List<HourlyForecastItem>> mHourlyList;
    private MutableLiveData<List<WeeklyForecastItem>> mWeeklyList;
    /** ViewModel for settings */
    private SettingsViewModel settingsViewModel;

    private FragmentHomeBinding mHomeBinding;
    private LocationViewModel mLocationModel;
    private FragmentWeatherBinding mWeatherBinding;
    private FragmentWeatherLocationBinding mWeatherLocationBinding;
    private WeatherLocationFragment mWeatherLocationFragment;
    private HomeFragment mHomeFragment;

    private boolean mLocationIsValid;

    public WeatherViewModel(@NonNull Application application) {
        super(application);

        mWeatherLocationSource = WeatherLocationSource.LAT_LONG;

        mHourlyList = new MutableLiveData<>();
        mHourlyList.setValue(new ArrayList<>());

        mWeeklyList = new MutableLiveData<>();
        mWeeklyList.setValue(new ArrayList<>());
    }

    public void addHourlyForecastItemListObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super List<HourlyForecastItem>> observer) {
        mHourlyList.observe(owner, observer);
    }

    public void addWeeklyForecastItemListObserver(@NonNull LifecycleOwner owner,
                                                  @NonNull Observer<? super List<WeeklyForecastItem>> observer) {
        mWeeklyList.observe(owner, observer);
    }

    private void handleError(final VolleyError error) {
        //you should add much better error handling in a production release.
        // i.e. YOUR PROJECT
        String expectedBadLocationRequestError = "Location for given latitude/longitude or zip code not found";

        String data = new String(error.networkResponse.data, Charset.defaultCharset());
        JSONObject dataJSON;
        try {
            dataJSON = new JSONObject(data);
            if(dataJSON.get("message").toString().equals(expectedBadLocationRequestError)) {
                //Update that the location is unsuccessful
                this.mLocationIsValid = false;
                if(mWeatherLocationFragment != null
                        && mWeatherLocationFragment.isVisible()
                        && mWeatherLocationFragment.getUserVisibleHint()) {
                    mWeatherLocationFragment.afterServerResponse();
                }
            } else {
                Log.e("Connection to web service error: ", dataJSON.get("message").toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //throw new IllegalStateException(error.getMessage());
    }

    private void handleResult(final JSONObject result) {
        mWeatherBinding.layoutComponents.setVisibility(View.GONE);
        mWeatherBinding.layoutWait.setVisibility(View.VISIBLE);

        IntFunction<String> getString = getApplication().getResources()::getString;
        try {
            //Current conditions
            if (result.has(getString.apply(R.string.keys_current_weather))
                    && result.has(getString.apply(R.string.keys_city))) {
                String city = result.getString(getString.apply(R.string.keys_city));
                JSONObject currentWeather = result.getJSONObject(getString.apply(
                        R.string.keys_current_weather));
                handleCurrentWeather(city, currentWeather);
            } else {
                Log.e("ERROR!", "No current conditions object in weather endpoint response");
            }

            //Hourly forecast
            if (result.has(getString.apply(R.string.keys_hourly_forecast))) {
                mHourlyList.setValue(new ArrayList<>());
                JSONArray hourlyForecastData = result.getJSONArray(getString.apply(
                        R.string.keys_hourly_forecast));
                for(int i = 0; i < hourlyForecastData.length(); i++) {
                    JSONObject hourlyForecastDataItem = hourlyForecastData.getJSONObject(i);
                    HourlyForecastItem hourlyItem = new HourlyForecastItem.Builder(
                            Integer.parseInt(
                                    hourlyForecastDataItem.getString(
                                            getString.apply(
                                                    R.string.keys_hour_time))),
                            hourlyForecastDataItem.getString(
                                    getString.apply(
                                            R.string.keys_description)),
                            Double.parseDouble(
                                    hourlyForecastDataItem.getString(
                                            getString.apply(
                                                    R.string.keys_temp))))
                            .build();
                    mHourlyList.getValue().add(hourlyItem);
                }
                mHourlyList.setValue(mHourlyList.getValue());
            } else {
                    Log.e("ERROR!", "No hourly forecast array in weather endpoint response");
            }

            //5 day forecast
            if (result.has(getString.apply(R.string.keys_five_day_forecast))) {
                mWeeklyList.setValue(new ArrayList<>());
                JSONArray fiveDayForecastData = result.getJSONArray(getString.apply(
                        R.string.keys_five_day_forecast));
                for(int i = 0; i < fiveDayForecastData.length(); i++) {
                    JSONObject fiveDayForecastDataItem = fiveDayForecastData.getJSONObject(i);
                    WeeklyForecastItem weeklyItem = new WeeklyForecastItem.Builder(
                            Integer.parseInt(
                                    fiveDayForecastDataItem.getString(
                                            getString.apply(
                                                    R.string.keys_day_time))),
                            fiveDayForecastDataItem.getString(
                                    getString.apply(
                                            R.string.keys_description)),
                            Double.parseDouble(
                                    fiveDayForecastDataItem.getString(
                                            getString.apply(
                                                    R.string.keys_temp))))
                            .build();
                    mWeeklyList.getValue().add(weeklyItem);
                }
                mWeeklyList.setValue(mWeeklyList.getValue());
            } else {
                Log.e("ERROR!", "No 5 day forecast array in weather endpoint response");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ERROR!", e.getMessage());
        }
        mWeatherBinding.layoutComponents.setVisibility(View.VISIBLE);
        mWeatherBinding.layoutWait.setVisibility(View.GONE);

        mLocationIsValid = true;
        if(mWeatherLocationFragment != null
                && mWeatherLocationFragment.isVisible()
                && mWeatherLocationFragment.getUserVisibleHint()) {
            mWeatherLocationFragment.afterServerResponse();
        }
    }

    public void connectGet() {
        String url = "https://team-9-tcss450-backend.herokuapp.com/weather?";

        if(mWeatherLocationSource.equals(WeatherLocationSource.ZIP)) {
            url += "zip=" + this.mZipCode;
        } else {
            url += "lat=" + this.mLatitude + "&long=" + this.mLongitude;
        };

        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, //no body for this get request
                this::handleResult,
                this::handleError) {
            @Override public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                headers.put("Authorization", mJwt);
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Instantiate the RequestQueue and add the request to the queue
        Volley.newRequestQueue(getApplication().getApplicationContext())
                .add(request);
    }

    private void handleCurrentWeather(String city, JSONObject currentWeather)
            throws JSONException {
        IntFunction<String> getString = getApplication().getResources()::getString;

        //Update weather image
        int image = determineImageFromDescription(
                currentWeather.getString(
                        getString.apply(
                                R.string.keys_description)),
                Integer.parseInt(currentWeather.getString(
                        getString.apply(
                                R.string.keys_hour_time))));
        this.mWeatherBinding.imageViewMainConditionsPlaceholder.setImageResource(image);

        //Update city name
        this.mWeatherBinding.textViewCityPlaceholder.setText(city);

        //Update current temperature
        String temperature = (int) Double.parseDouble(
                currentWeather.getString(
                        getString.apply(
                                R.string.keys_temp))) + "°";
        this.mWeatherBinding.textViewMainTemperaturePlaceholder.setText(temperature);


        //Update home fragment with the above values
        if(mHomeFragment != null && mHomeFragment.isVisible()) {
            this.mHomeBinding.imageViewMainConditionsPlaceholder.setImageResource(image);
            this.mHomeBinding.textDegHome.setText(temperature);
            mHomeFragment.setUpdatedByWeatherFragment(true);
        }


        //Update wind speed
        this.mWeatherBinding.textViewWindSpeedPlaceholder.setText(
                "Wind Speed: " +
                Math.round((Double.parseDouble(
                            currentWeather.getString(
                                    getString.apply(
                                            R.string.keys_wind_speed))) * 100.0) / 100.0) + " mph"
        );
    }

    public void setJWT(String jwt) {
        this.mJwt = jwt;
    }

    public void setHomeBinding(FragmentHomeBinding binding) {
        this.mHomeBinding = binding;
    }

    public void setWeatherBinding(FragmentWeatherBinding binding) {
        this.mWeatherBinding = binding;
    }

    public void setWeatherLocationBinding(FragmentWeatherLocationBinding binding) {
        this.mWeatherLocationBinding = binding;
    }

    /**
     * Connect this view model to an activity so that it can access components such as the user's
     * current location
     *
     * @param activity
     */
    public void setCurrentActivity(Activity activity) {
        mActivity = activity;
    }

    /**
     * Connect the weather view model to the location model in the current activity
     */
    public void setupLocationModel() {
        mLocationModel = new ViewModelProvider((ViewModelStoreOwner) mActivity)
                .get(LocationViewModel.class);
    }

    public void setHomeFragment(HomeFragment homeFragment) {
        mHomeFragment = homeFragment;
    }

    public void setWeatherLocationFragment(WeatherLocationFragment weatherLocationFragment) {
        mWeatherLocationFragment = weatherLocationFragment;
    }

    public void useCurrentLocation() {
        mWeatherLocationSource = WeatherLocationSource.LAT_LONG;
        mLatitude = mLocationModel.getCurrentLocation().getLatitude();
        mLongitude = mLocationModel.getCurrentLocation().getLongitude();
        connectGet();
    }

    public void useZipLocation(String zip) {
        mWeatherLocationSource = WeatherLocationSource.ZIP;
        mZipCode = zip;
        connectGet();
    }

    public void useMapLocation(double latitude, double longitude) {
        mWeatherLocationSource = WeatherLocationSource.LAT_LONG;
        mLatitude = latitude;
        mLongitude = longitude;
        connectGet();
    }

    public void useDefaultLocation() {
        mLatitude = 47.474190;
        mLongitude = -122.206650;
        mWeatherLocationSource = WeatherLocationSource.LAT_LONG;
        connectGet();
    }

    public boolean getLocationIsValid() {
        return mLocationIsValid;
    }

    public WeatherLocationSource getWeatherLocationSource() {
        return mWeatherLocationSource;
    }

    public enum WeatherLocationSource {
        ZIP,
        LAT_LONG,
        MAP
    }
}
