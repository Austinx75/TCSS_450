package edu.uw.harmony.UI.Weather;

import android.app.Activity;
import android.app.Application;
import android.graphics.Color;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import edu.uw.harmony.R;
import edu.uw.harmony.UI.settings.SettingsViewModel;
import edu.uw.harmony.databinding.FragmentWeatherBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntFunction;

import static edu.uw.harmony.util.WeatherUtils.determineImageFromDescription;

public class WeatherViewModel extends AndroidViewModel {
    private String mJwt;
    private MutableLiveData<List<HourlyForecastItem>> mHourlyList;
    private MutableLiveData<List<WeeklyForecastItem>> mWeeklyList;
    /** ViewModel for settings */
    private SettingsViewModel settingsViewModel;



    private FragmentWeatherBinding weatherBinding;

    public WeatherViewModel(@NonNull Application application) {
        super(application);
        mHourlyList = new MutableLiveData<>();
        mHourlyList.setValue(new ArrayList<>());
        //settingsViewModel = new ViewModelProvider().get(SettingsViewModel.class);

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
        Log.e("CONNECTION ERROR", "" + error.getLocalizedMessage());
        throw new IllegalStateException(error.getMessage());
    }

    private void handleResult(final JSONObject result) {
        IntFunction<String> getString = getApplication().getResources()::getString;
        try {
            //Current conditions
            if (result.has(getString.apply(R.string.keys_current_weather))) {
                JSONObject currentWeather = result.getJSONObject(getString.apply(
                        R.string.keys_current_weather));
                handleCurrentWeather(currentWeather);
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
    }

    public void connectGet() {
        String url = "https://team-9-tcss450-backend.herokuapp.com/weather?lat=47.474190&long=-122.206650";
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

    public void setJWT(String jwt) {
        this.mJwt = jwt;
    }

    public void setWeatherBinding(FragmentWeatherBinding binding) {
        this.weatherBinding = binding;
    }

    private void handleCurrentWeather(JSONObject currentWeather) throws JSONException {
        IntFunction<String> getString = getApplication().getResources()::getString;

        int image = R.drawable.weather_clouds;

        this.weatherBinding.imageViewMainConditionsPlaceholder.setImageResource(
                determineImageFromDescription(
                        currentWeather.getString(
                                getString.apply(
                                        R.string.keys_description))
                )
        );

        //TODO: Update city name

        this.weatherBinding.textViewMainTemperaturePlaceholder.setText(
                (int) Double.parseDouble(
                        currentWeather.getString(
                                getString.apply(
                                        R.string.keys_temp))) + "°");
//        if(settingsViewModel.getCurrentThemeID() == R.style.Theme_1_Harmony){
//            weatherBinding.textViewMainTemperaturePlaceholder.setTextColor(Color.BLACK);
//        } else {
//            weatherBinding.textViewMainTemperaturePlaceholder.setTextColor(Color.WHITE);
//        }
    }
}
