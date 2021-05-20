package edu.uw.harmony.UI.Weather;

import java.util.Arrays;
import java.util.List;

/**
 * Creates a list of sample weekly forecast items used for dummy data in the 5 day forecast section of WeatherFragment.
 *
 * @author  Gary Kono
 * @version 1.1
 */
public class WeeklyForecastItemGenerator {
    private static final WeeklyForecastItem[] WEEKLY_FORECAST_ITEMS;

    /**
     * Create 5 WeeklyForecastItems representing a hypothetical Monday-Friday list
     */
    static {
        WEEKLY_FORECAST_ITEMS = new WeeklyForecastItem[5];
        for (int i = 0; i < WEEKLY_FORECAST_ITEMS.length; i++) {
            int day = i;
            WEEKLY_FORECAST_ITEMS[i] = new WeeklyForecastItem
                    .Builder(day,
                    "clear sky",
                    70)
                    .build();
        }
    }

    /**
     *
     * @return A list of artificial HourlyForecastItems.
     */
    public static List<WeeklyForecastItem> getWeeklyForecastList() {
        return Arrays.asList(WEEKLY_FORECAST_ITEMS);
    }

    /**
     *
     * @return An array of artificial HourlyForecastItems.
     */
    public static WeeklyForecastItem[] getWEEKLYFORECASTLIST() {
        return Arrays.copyOf(WEEKLY_FORECAST_ITEMS, WEEKLY_FORECAST_ITEMS.length);
    }

    private WeeklyForecastItemGenerator() {

    }
}
