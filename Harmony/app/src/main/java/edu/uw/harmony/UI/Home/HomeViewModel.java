package edu.uw.harmony.UI.Home;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntFunction;

import edu.uw.harmony.R;
import edu.uw.harmony.UI.Weather.HourlyForecastItem;
import edu.uw.harmony.UI.Weather.WeeklyForecastItem;
import edu.uw.harmony.UI.settings.SettingsViewModel;
import edu.uw.harmony.databinding.FragmentHomeBinding;
import edu.uw.harmony.databinding.FragmentWeatherBinding;

import static edu.uw.harmony.util.WeatherUtils.determineImageFromDescription;

public class HomeViewModel extends AndroidViewModel{

    /** JWT token*/
    private String mJwt;

    /** Binding for home fragment. */
    private FragmentHomeBinding homeBinding;

    /**
     * Constructor that calls super.
     * @param application
     */
    public HomeViewModel(@NonNull Application application) {
            super(application);
        }

    /**
     * Handles the error
     * @param error
     */
    private void handleError(final VolleyError error) {
        Log.e("CONNECTION ERROR", "" + error.getLocalizedMessage());
        throw new IllegalStateException(error.getMessage());
    }

    /**
     * Handles the result if the JSON object was returned.
     * It gets the current weather then sends it to handleCurrentWeather();
     * @param result
     */
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
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ERROR!", e.getMessage());
        }

    }

    /**
     * Makes a connection request to our backend. It then sends the results to handleResult, and handleError.
     */
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

    /**
     * Sets the JWT
     * @param jwt
     */
    public void setJWT(String jwt) {
        this.mJwt = jwt;
    }


    /**
     * Instantiates the Home Binding
     * @param binding
     */
    public void setHomeBinding(FragmentHomeBinding binding) {
        this.homeBinding = binding;
    }

    /**
     * It derives the current weather from JSON Object and sets the
     * home fragment weather.
     * @param currentWeather
     * @throws JSONException
     */
    private void handleCurrentWeather(JSONObject currentWeather) throws JSONException {
        IntFunction<String> getString = getApplication().getResources()::getString;
        int image = R.drawable.weather_clouds;

        this.homeBinding.imageViewMainConditionsPlaceholder.setImageResource(
                determineImageFromDescription(
                        currentWeather.getString(
                                getString.apply(
                                        R.string.keys_description))
                )
        );

        //TODO: Update city name
        this.homeBinding.textDegHome.setText(
                (int) Double.parseDouble(
                        currentWeather.getString(
                                getString.apply(
                                        R.string.keys_temp))) + "°");

    }

}
