package edu.uw.harmony.UI.Weather;

import java.util.Arrays;
import java.util.List;

public class HourlyForecastItemGenerator {
    private static final HourlyForecastItem[] HOURLY_FORECAST_ITEMS;
    public static final int STARTING_HOUR = 6;
    public static final int ENDING_HOUR = 22;


    static {
        HOURLY_FORECAST_ITEMS = new HourlyForecastItem[ENDING_HOUR - STARTING_HOUR + 1];
        for (int i = 0; i < HOURLY_FORECAST_ITEMS.length; i++) {
            HOURLY_FORECAST_ITEMS[i] = new HourlyForecastItem
                    .Builder(i + STARTING_HOUR,
                    80)
                    .build();
        }
    }

    public static List<HourlyForecastItem> getHourlyForecastList() {
        return Arrays.asList(HOURLY_FORECAST_ITEMS);
    }

    public static HourlyForecastItem[] getHOURLYFORECASTLIST() {
        return Arrays.copyOf(HOURLY_FORECAST_ITEMS, HOURLY_FORECAST_ITEMS.length);
    }

    private HourlyForecastItemGenerator() {

    }
}
