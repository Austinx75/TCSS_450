package edu.uw.harmony.util;

import edu.uw.harmony.R;

public class WeatherUtils {
    public WeatherUtils() {

    }

    public static int determineImageFromDescription(String description) {
        if(description.contains("clear sky")) {
            return R.drawable.weather_sunny;
        } else if(description.contains("thunderstorm")) {
            return R.drawable.weather_thunderstorm;
        } else if(description.contains("rain")) {
            return R.drawable.weather_rain;
        } else if(description.contains("snow")) {
            return R.drawable.weather_snow;
        } else {
            return R.drawable.weather_clouds;
        }
    }
}
