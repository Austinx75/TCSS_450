package edu.uw.harmony.UI.Weather;

import java.io.Serializable;

/**
 * This class represents a single hour on the 24 hour forecast list used in WeatherFragment.
 *
 * @author  Gary Kono
 * @version 1.0
 */
public class HourlyForecastItem implements Serializable {
    private final int mHour;
    //TODO: Add an image that represents the condition (sunny, rainy, etc.)
    private final int mTemp;

    /**
     * Helper class for building Credentials.
     */
    public static class Builder {
        private final int mHour;
        //private final String mConditionImage;
        private final int mTemp;


        /**
         * Constructs a new Builder.
         *
         * @param hour the hour of the current day
         * @param temp the temperature for this hour provided by api
         */
        public Builder(int hour, int temp) {
            this.mHour = hour;
            this.mTemp = temp;
        }

        public HourlyForecastItem build() {
            return new HourlyForecastItem(this);
        }
    }

    private HourlyForecastItem(final Builder builder) {
        this.mHour = builder.mHour;
        this.mTemp = builder.mTemp;
    }

    /**
     *
     * @return The hour this class represents.
     */
    public int getHour() {
        return this.mHour;
    }

    /**
     *
     * @return The expected/observed average temperature for this hour.
     */
    public int getTemp() {
        return this.mTemp;
    }
}
