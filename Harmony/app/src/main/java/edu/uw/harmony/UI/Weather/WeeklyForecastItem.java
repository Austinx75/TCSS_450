package edu.uw.harmony.UI.Weather;

import java.io.Serializable;

/**
 * This class represents a single day on the 5 day forecast list used in WeatherFragment.
 *
 * @author  Gary Kono
 * @version 1.0
 */
public class WeeklyForecastItem implements Serializable {
    private final String mDay;
    //TODO: Add an image that represents the condition (sunny, rainy, etc.)
    private final int mTemp;

    /**
     * Helper class for building Credentials.
     */
    public static class Builder {
        private final String mDay;
        //private final String mConditionImage;
        private final int mTemp;


        /**
         * Constructs a new Builder.
         *
         * @param day The day of the week this card is associated with
         * @param temp the temperature for this day provided by the api
         */
        public Builder(String day, int temp) {
            this.mDay = day;
            this.mTemp = temp;
        }

        public WeeklyForecastItem build() {
            return new WeeklyForecastItem(this);
        }
    }

    private WeeklyForecastItem(final WeeklyForecastItem.Builder builder) {
        this.mDay = builder.mDay;
        this.mTemp = builder.mTemp;
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
     * @return The expected/observed average temperature for this day.
     */
    public int getTemp() {
        return this.mTemp;
    }
}
