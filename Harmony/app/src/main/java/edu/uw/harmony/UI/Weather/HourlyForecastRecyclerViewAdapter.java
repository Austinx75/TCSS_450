package edu.uw.harmony.UI.Weather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.harmony.R;
import edu.uw.harmony.databinding.FragmentWeatherHourlyForecastCardBinding;

import static edu.uw.harmony.util.WeatherUtils.determineImageFromDescription;

/**
 * The RecyclerView adapter that makes an HourlyForecastItem list compatible with the 24 hour forecast
 * Recycler View used in WeatherFragment.
 *
 * @author  Gary Kono
 * @version 1.0
 */
public class HourlyForecastRecyclerViewAdapter extends
        RecyclerView.Adapter<HourlyForecastRecyclerViewAdapter.HourlyForecastViewHolder> {
    //Store all of the blogs to present
    private final List<HourlyForecastItem> mHourlyForecasts;

    public HourlyForecastRecyclerViewAdapter(List<HourlyForecastItem> items) {
        this.mHourlyForecasts = items;
    }

    @Override
    public int getItemCount() {
        return mHourlyForecasts.size();
    }

    @NonNull
    @Override
    public HourlyForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HourlyForecastViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_weather_hourly_forecast_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HourlyForecastViewHolder holder, int position) {
        holder.setHourlyForecastItem(mHourlyForecasts.get(position));
    }

    /**
     * Objects from this class represent an Individual row View from the List
     * of rows in the Blog Recycler View.
     */
    public class HourlyForecastViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentWeatherHourlyForecastCardBinding binding;
        private HourlyForecastItem mHourlyForecast;

        public HourlyForecastViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentWeatherHourlyForecastCardBinding.bind(view);
        }

        void setHourlyForecastItem(final HourlyForecastItem hourlyForecast) {
            mHourlyForecast = hourlyForecast;

            //Set the text for what time (hour)
            int currentHour = mHourlyForecast.getHour();
            binding.textHourlyTime.setText(
                    currentHour <= 12 ? (currentHour + " AM") : (currentHour % 12 + " PM")
            );

            //Set the image
            this.binding.imageViewHourlyForecastCondition.setImageResource(
                    determineImageFromDescription(mHourlyForecast.getDescription()));

            //Set the text for the temperature
            binding.textHourlyTemp.setText((int)mHourlyForecast.getTemp() + "°");
        }
    }
}
