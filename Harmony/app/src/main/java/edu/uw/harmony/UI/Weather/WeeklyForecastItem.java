package edu.uw.harmony.UI.Weather;

import android.util.Log;

import java.io.Serializable;

/**
 * This class represents a single day on the 5 day forecast list used in WeatherFragment.
 *
 * @author  Gary Kono
 * @version 1.1
 */
public class WeeklyForecastItem implements Serializable {
    private final String mDay;
    //TODO: Add an image that represents the condition (sunny, rainy, etc.)
    private final String mDescription;
    private final double mTemp;

    /**
     * Helper class for building Credentials.
     */
    public static class Builder {
        private final String mDay;
        private final String mDescription;
        private final double mTemp;


        /**
         * Constructs a new Builder.
         *
         * @param day The day of the week this card is associated with
         * @param temp the temperature for this day provided by the api
         */
        public Builder(int day, String description, double temp) {
            this.mDay = convertToDayString(day);
            this.mDescription = description;
            this.mTemp = temp;
        }

        public WeeklyForecastItem build() {
            return new WeeklyForecastItem(this);
        }
    }

    private WeeklyForecastItem(final WeeklyForecastItem.Builder builder) {
        this.mDay = builder.mDay;
        this.mDescription = builder.mDescription;
        this.mTemp = builder.mTemp;
    }

    /**
     * Helper function that converts an integer provided by the response of the GET request to our
     * web service endpoint into the day of the week it represents
     *
     * @param numberRepresentationOfDay An integer from 0-6 where 0=Monday, 1=Tuesday, etc.
     * @return The string representation of that weekday
     */
    private static String convertToDayString(int numberRepresentationOfDay) {
        switch(numberRepresentationOfDay) {
            case 0:
                return "Sunday";
            case 1:
                return "Monday";
            case 2:
                return "Tuesday";
            case 3:
                return "Wednesday";
            case 4:
                return "Thursday";
            case 5:
                return "Friday";
            case 6:
                return "Saturday";
            default:
                Log.e("ERROR!", "The weather endpoint gave an invalid day for the 5 day forecast");
                return "";
        }
    }

    /**
     *
     * @return The day this class represents.
     */
    public String getDay() {
        return this.mDay;
    }

    /**
     *
     * @return A short description of this hour's weather condition. (i.e. sunny, partly cloudy).
     */
    public String getDescription() {
        return this.mDescription;
    }

    /**
     *
     * @return The expected/observed average temperature for this day.
     */
    public double getTemp() {
        return this.mTemp;
    }
}
