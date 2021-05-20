package edu.uw.harmony.UI.Weather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.harmony.R;
import edu.uw.harmony.databinding.FragmentWeatherWeeklyForecastCardBinding;

import static edu.uw.harmony.util.WeatherUtils.determineImageFromDescription;

/**
 * The RecyclerView adapter that makes an WeeklyForecastItem list compatible with the 5 day forecast
 * Recycler View used in WeatherFragment.
 *
 * @author  Gary Kono
 * @version 1.1
 */
public class WeeklyForecastRecyclerViewAdapter extends
        RecyclerView.Adapter<WeeklyForecastRecyclerViewAdapter.WeeklyForecastViewHolder> {
    //Store all of the blogs to present
    private final List<WeeklyForecastItem> mWeeklyForecasts;

    /**
     * Constructor for an WeeklyForecastRecyclerViewAdapter
     *
     * @param items The WeeklyForecastItems that will be displayed on the 5 day forecast of the weather fragment
     */
    public WeeklyForecastRecyclerViewAdapter(List<WeeklyForecastItem> items) {
        this.mWeeklyForecasts = items;
    }

    @Override
    public int getItemCount() {
        return mWeeklyForecasts.size();
    }

    @NonNull
    @Override
    public WeeklyForecastRecyclerViewAdapter.WeeklyForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WeeklyForecastRecyclerViewAdapter.WeeklyForecastViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_weather_weekly_forecast_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WeeklyForecastRecyclerViewAdapter.WeeklyForecastViewHolder holder, int position) {
        holder.setWeeklyForecastItem(mWeeklyForecasts.get(position));
    }

    /**
     * Objects from this class represent an Individual row View from the List
     * of rows in the Blog Recycler View.
     */
    public class WeeklyForecastViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentWeatherWeeklyForecastCardBinding binding;
        private WeeklyForecastItem mWeeklyForecast;

        /**
         * Constructor for WeeklyForecastViewHolder
         *
         * @param view The view this will be displayed on
         */
        public WeeklyForecastViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentWeatherWeeklyForecastCardBinding.bind(view);
        }

        /**
         * Set the display component data for this individual WeeklyForecastItem
         *
         * @param weeklyForecast The individual WeeklyForecastItem that will be altered
         */
        void setWeeklyForecastItem(final WeeklyForecastItem weeklyForecast) {
            mWeeklyForecast = weeklyForecast;

            //Set the text for what day of the week
            String currentDay = mWeeklyForecast.getDay();
            binding.textWeeklyDay.setText(mWeeklyForecast.getDay());

            //Set the image
            this.binding.imageViewWeeklyForecastCondition.setImageResource(
                    determineImageFromDescription(mWeeklyForecast.getDescription()));

            //Set the text for the temperature
            binding.textWeeklyTemp.setText((int)mWeeklyForecast.getTemp() + "°");
        }
    }

}
