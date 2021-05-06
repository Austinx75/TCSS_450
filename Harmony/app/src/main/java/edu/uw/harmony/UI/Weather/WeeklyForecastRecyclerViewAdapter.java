package edu.uw.harmony.UI.Weather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.harmony.R;
import edu.uw.harmony.databinding.FragmentWeeklyForecastCardBinding;

/**
 * The RecyclerView adapter that makes an HourlyForecastItem list compatible with the 24 hour forecast
 * Recycler View used in WeatherFragment.
 *
 * @author  Gary Kono
 * @version 1.0
 */
public class WeeklyForecastRecyclerViewAdapter extends
        RecyclerView.Adapter<WeeklyForecastRecyclerViewAdapter.WeeklyForecastViewHolder> {
    //Store all of the blogs to present
    private final List<WeeklyForecastItem> mWeeklyForecasts;

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
                .inflate(R.layout.fragment_weekly_forecast_card, parent, false));
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
        public FragmentWeeklyForecastCardBinding binding;
        private WeeklyForecastItem mWeeklyForecast;

        public WeeklyForecastViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentWeeklyForecastCardBinding.bind(view);
        }

        void setWeeklyForecastItem(final WeeklyForecastItem weeklyForecast) {
            mWeeklyForecast = weeklyForecast;

            //Set the text for what day of the week
            String currentDay = mWeeklyForecast.getDay();
            binding.textWeeklyDay.setText(mWeeklyForecast.getDay());

            //TODO: Set the image
            //Currently just uses a default image

            //Set the text for the temperature
            binding.textWeeklyTemp.setText(mWeeklyForecast.getTemp() + "°");
        }
    }

}
