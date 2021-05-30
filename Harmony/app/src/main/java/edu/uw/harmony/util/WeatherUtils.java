package edu.uw.harmony.util;

import edu.uw.harmony.R;

public class WeatherUtils {
    public WeatherUtils() {

    }

    public static int determineImageFromDescription(String description, int hourOfDay) {
        if(hourOfDay <= 4 || hourOfDay >=19) {
            if (description.contains("clear sky")) {
                return R.drawable.weather_clear_night;
            } else if (description.contains("thunderstorm")) {
                return R.drawable.weather_thunderstorm_night;
            } else if (description.contains("rain")) {
                return R.drawable.weather_rain_night;
            } else if (description.contains("snow")) {
                return R.drawable.weather_snow;
            } else {
                return R.drawable.weather_clouds_night;
            }
        } else {
            if (description.contains("clear sky")) {
                return R.drawable.weather_clear;
            } else if (description.contains("thunderstorm")) {
                return R.drawable.weather_thunderstorm;
            } else if (description.contains("rain")) {
                return R.drawable.weather_rain;
            } else if (description.contains("snow")) {
                return R.drawable.weather_snow;
            } else {
                return R.drawable.weather_clouds;
            }
        }
    }
}
