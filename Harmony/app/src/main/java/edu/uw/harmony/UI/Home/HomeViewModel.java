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

/**
 * This is a view model to capture the current weather for the home page.
 * @author Austin Scott
 * @version 1.1
 */
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


}
