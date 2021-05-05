package edu.uw.harmony.UI.Weather;

import java.io.Serializable;

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

    public int getHour() {
        return this.mHour;
    }

    public int getTemp() {
        return this.mTemp;
    }
}
