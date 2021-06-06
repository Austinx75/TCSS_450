package edu.uw.harmony.UI.Weather;

import android.app.Activity;
import android.app.Application;
import android.graphics.Color;
import android.location.Location;
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

/**
 * Manages all weather information based on a selected location. If the location is changed, this class
 * handles updating components listening for a location change.
 *
 *
 * @author  Gary Kono
 * @version 1.2
 */
public class WeatherViewModel extends AndroidViewModel {
    Activity mActivity;
    private String mJwt;

    /** What type of location was selected to get weather information about (i.e. Map, Zip, etc.) */
    private WeatherLocationSource mWeatherLocationSource;

    /* These are locations that are actually saved in the view model because they successfully retrieved
    weather info about their location */
    private double mLatitude;
    private double mLongitude;
    private String mZipCode;

    /* These are variables that store parameters used in the most recent connectGet() call */
    private double mLatitudeRequest;
    private double mLongitudeRequest;
    private String mZipCodeRequest;
    /** The type of location used in the most recent connectGet() call */
    private WeatherLocationSource mLocationSourceUsedInRequest;

    private MutableLiveData<CurrentWeatherContainer> mCurrentWeatherContainer;
    private MutableLiveData<List<HourlyForecastItem>> mHourlyList;
    private MutableLiveData<List<WeeklyForecastItem>> mWeeklyList;
    /** A flag whether the most recent location selected was a valid location. */
    private MutableLiveData<Boolean> mLocationIsValid;
    /** A flag whether the server has responded after a request. */
    private MutableLiveData<Boolean> mServerHasResponded;
    /** A flag whether the the view model has been initialized with current location or not */
    private boolean mGetStartingLocationHasBeenInitialized;

    /** A flag to indicate whether there is a navigation from the weather location tab to the weather report tab */
    private boolean mNavigatingFromWeatherLocation;

    /** ViewModel for settings */
    private SettingsViewModel settingsViewModel;

    private LocationViewModel mLocationModel;


    public WeatherViewModel(@NonNull Application application) {
        super(application);

        mWeatherLocationSource = WeatherLocationSource.CURRENT;

        mCurrentWeatherContainer = new MutableLiveData<>();
        mCurrentWeatherContainer.setValue(new CurrentWeatherContainer(
                "Seattle",
                R.drawable.weather_clouds,
                "0.0",
                "0"));

        if(mHourlyList == null)
        mHourlyList = new MutableLiveData<>();
        mHourlyList.setValue(new ArrayList<>());

        mWeeklyList = new MutableLiveData<>();
        mWeeklyList.setValue(new ArrayList<>());

        mLocationIsValid = new MutableLiveData<>();
        mLocationIsValid.setValue(false);

        mServerHasResponded = new MutableLiveData<>();
        mServerHasResponded.setValue(true);

        mLocationSourceUsedInRequest = WeatherLocationSource.CURRENT;
        mNavigatingFromWeatherLocation = false;
        mGetStartingLocationHasBeenInitialized = false;
    }

    /**
     * Add an observer to the CurrentWeather
     */
    public void addCurrentWeatherObserver(@NonNull LifecycleOwner owner,
                                                  @NonNull Observer<? super CurrentWeatherContainer> observer) {
        mCurrentWeatherContainer.observe(owner, observer);
    }

    /**
     * Add an observer to the HourlyForecastList
     */
    public void addHourlyForecastItemListObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super List<HourlyForecastItem>> observer) {
        mHourlyList.observe(owner, observer);
    }

    /**
     * Add an observer to the WeeklyForecastList
     */
    public void addWeeklyForecastItemListObserver(@NonNull LifecycleOwner owner,
                                                  @NonNull Observer<? super List<WeeklyForecastItem>> observer) {
        mWeeklyList.observe(owner, observer);
    }

    /**
     * Add an observer listening to whether the last searched location to the weather endpoint was a valid
     * location or not
     */
    public void addLocationIsValidObserver(@NonNull LifecycleOwner owner,
                                                  @NonNull Observer<? super Boolean> observer) {
        mLocationIsValid.observe(owner, observer);
    }

    /**
     * Add an observer listening to whether the response for the request to the web service has
     * been processed by this view model
     */
    public void addServerRespondedObserver(@NonNull LifecycleOwner owner,
                                           @NonNull Observer<? super Boolean> observer) {
        mServerHasResponded.observe(owner, observer);
    }

    /**
     * Handle an error from a request to the weather service endpoint. Mainly updates an error message
     * shown in a fragment if a location could not be found.
     */
    private void handleError(final VolleyError error) {
        String expectedBadLocationRequestError = "Location for given latitude/longitude or zip code not found";

        String data = new String(error.networkResponse.data, Charset.defaultCharset());
        JSONObject dataJSON;
        try {
            dataJSON = new JSONObject(data);
            if(dataJSON.get("message").toString().equals(expectedBadLocationRequestError)) {
                //Update that the location is unsuccessful
                this.mLocationIsValid.setValue(false);
                serverRespond();
            } else {
                Log.e("Connection to web service error: ", dataJSON.get("message").toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //throw new IllegalStateException(error.getMessage());
    }

    /**
     * Handle the response given by the request to the web service endpoint. Updates the weather report.
     */
    private void handleResult(final JSONObject result) {
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
        mLocationIsValid.setValue(true);
        serverRespond();
    }

    /**
     * Make a request to the web service endpoint for weather information about the selected location.
     *
     * @param useTemporaryRequestCoordinates Determines whether this call is trying to find information
     *                                       about a new location (true) or is using the saved location (false)
     */
    public void connectGet(boolean useTemporaryRequestCoordinates) {
        mServerHasResponded.setValue(false);
        String url = "https://team-9-tcss450-backend.herokuapp.com/weather?";
        if(useTemporaryRequestCoordinates) {
            if(mLocationSourceUsedInRequest.equals(WeatherLocationSource.ZIP)) {
                url += "zip=" + this.mZipCodeRequest;
            } else {
                url += "lat=" + this.mLatitudeRequest + "&long=" + this.mLongitudeRequest;
            }
        } else {
            if(mWeatherLocationSource.equals(WeatherLocationSource.ZIP)) {
                url += "zip=" + this.mZipCode;
            } else {
                url += "lat=" + this.mLatitude + "&long=" + this.mLongitude;
            }
        }

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

    /**
     * Updates the weather information for the current weather in this view model.
     * @param city Name of the location's city.
     * @param currentWeather A JSON from the webservice endpoint that contains current weather info.
     * @throws JSONException
     */
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

        //Update current temperature
        String temperature = currentWeather.getString(
                getString.apply(
                        R.string.keys_temp));
        //Update wind speed
        String windspeed =  currentWeather.getString(
                getString.apply(
                        R.string.keys_wind_speed));

        mCurrentWeatherContainer.setValue(
                new CurrentWeatherContainer(city, image, temperature, windspeed));
    }

    public void setJWT(String jwt) {
        this.mJwt = jwt;
    }

    /**
     * Connect the weather view model to the location model in the current activity
     */
    public void setLocationModel(LocationViewModel locationModel) {
        mLocationModel = locationModel;
    }

    public void updateLocationCoordinates(Location location) {
        if(mWeatherLocationSource == WeatherLocationSource.CURRENT) {
            if(!mGetStartingLocationHasBeenInitialized) {
                useCurrentLocationObserveLocationChange(location);
                mGetStartingLocationHasBeenInitialized = true;
            } else {
                mLatitude = location.getLatitude();
                mLongitude = location.getLongitude();
            }
        }
    }

    /**
     * Update the weather information stored in this view model and make a request to the endpoint based
     * on the user's current location.
     */
    public void useCurrentLocationObserveLocationChange(Location location) {
        mLocationSourceUsedInRequest = WeatherLocationSource.CURRENT;
        mLatitudeRequest = location.getLatitude();
        mLongitudeRequest = location.getLongitude();
        connectGet(true);
    }

    /**
     * Update the weather information stored in this view model and make a request to the endpoint based
     * on the user's current location.
     */
    public void useCurrentLocation() {
        mLocationSourceUsedInRequest = WeatherLocationSource.CURRENT;
        mLatitudeRequest = mLocationModel.getCurrentLocation().getLatitude();
        mLongitudeRequest = mLocationModel.getCurrentLocation().getLongitude();
        connectGet(true);
    }

    /**
     * Update the weather information stored in this view model and make a request to the endpoint based
     * on the zip code provided.
     */
    public void useZipLocation(String zip) {
        mLocationSourceUsedInRequest = WeatherLocationSource.ZIP;
        mZipCodeRequest = zip;
        connectGet(true);
    }

    /**
     * Update the weather information stored in this view model and make a request to the endpoint based
     * on the location selected on the map.
     */
    public void useMapLocation(double latitude, double longitude) {
        mLocationSourceUsedInRequest = WeatherLocationSource.MAP;
        mLatitudeRequest = latitude;
        mLongitudeRequest = longitude;
        connectGet(true);
    }

    /**
     * After receiving a response from the server using connectGet(), if the server indicated that the
     * the location was valid, then save the location information in the view model.
     *
     */
    public void serverRespond() {
        if(mLocationIsValid.getValue()) {
            mWeatherLocationSource = mLocationSourceUsedInRequest;
            switch(mLocationSourceUsedInRequest) {
                case CURRENT:
                case MAP:
                    mLatitude = mLatitudeRequest;
                    mLongitude = mLongitudeRequest;
                    break;
                case ZIP:
                    mZipCode = mZipCodeRequest;
                    break;
            }
        }

        mServerHasResponded.setValue(true);
        //If the location is invalid, no need to do anything
    }

    /**
     * A method that serves as a temporary solution for errors with updating the location. The location is Seattle.
     */
    public void useDefaultLocation() {
//        mLatitude = 47.474190;
//        mLongitude = -122.206650;
//        mWeatherLocationSource = WeatherLocationSource.LAT_LONG;
//        connectGet();
    }

    public WeatherLocationSource getWeatherLocationSource() {
        return mWeatherLocationSource;
    }

    public WeatherLocationSource getWeatherLocationSourceRequest() {
        return mLocationSourceUsedInRequest;
    }

    public boolean getLocationIsValid() {
        return mLocationIsValid.getValue();
    }

    public boolean getServerHasResponded() {
        return mServerHasResponded.getValue();
    }

    public boolean getNavigatingFromWeatherLocation() {
        return mNavigatingFromWeatherLocation;
    }

    public void setNavigatingFromWeatherLocation(boolean navigatingFromWeatherLocation) {
        mNavigatingFromWeatherLocation = navigatingFromWeatherLocation;
    }

    public boolean getStartingLocationHasBeenInitialized() {
        return mGetStartingLocationHasBeenInitialized;
    }

    public enum WeatherLocationSource {
        CURRENT,
        ZIP,
        MAP
    }

    /**
     * A class that wraps all current weather information into one object so that action listeners
     * only need to listen to one thing.
     */
    public class CurrentWeatherContainer {
        public String city;
        public int image;
        public String temp;
        public String windSpeed;

        public CurrentWeatherContainer(String city, int image, String temp, String windSpeed) {
            this.city = city;
            this.image = image;
            this.temp = temp;
            this.windSpeed = windSpeed;
        }
    }
}
