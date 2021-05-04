package edu.uw.harmony.UI.Weather;

import java.util.Arrays;
import java.util.List;

public class WeeklyForecastItemGenerator {
    private static final WeeklyForecastItem[] WEEKLY_FORECAST_ITEMS;


    static {
        WEEKLY_FORECAST_ITEMS = new WeeklyForecastItem[5];
        for (int i = 0; i < WEEKLY_FORECAST_ITEMS.length; i++) {
            String day;
            switch(i) {
                case 0:
                    day = "Monday";
                    break;
                case 1:
                    day = "Tuesday";
                    break;
                case 2:
                    day = "Wednesday";
                    break;
                case 3:
                    day = "Thursday";
                    break;
                case 4:
                    day = "Friday";
                    break;
                default:
                    day = "";
                    break;
            }
            WEEKLY_FORECAST_ITEMS[i] = new WeeklyForecastItem
                    .Builder(day,
                    70)
                    .build();
        }
    }

    public static List<WeeklyForecastItem> getWeeklyForecastList() {
        return Arrays.asList(WEEKLY_FORECAST_ITEMS);
    }

    public static WeeklyForecastItem[] getWEEKLYFORECASTLIST() {
        return Arrays.copyOf(WEEKLY_FORECAST_ITEMS, WEEKLY_FORECAST_ITEMS.length);
    }

    private WeeklyForecastItemGenerator() {

    }
}
