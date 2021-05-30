package edu.uw.harmony.UI.Weather;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.harmony.R;
import edu.uw.harmony.UI.settings.SettingsViewModel;
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

    private SettingsViewModel sModel;

    public HourlyForecastRecyclerViewAdapter(List<HourlyForecastItem> items, SettingsViewModel model) {
        this.mHourlyForecasts = items;
        this.sModel = model;
    }

    @Override
    public int getItemCount() {
        return mHourlyForecasts.size();
    }

    @NonNull
    @Override
    public HourlyForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HourlyForecastViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_weather_hourly_forecast_card, parent, false), sModel);
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
        private SettingsViewModel settingsViewModel;

        public HourlyForecastViewHolder(View view, SettingsViewModel model) {
            super(view);
            mView = view;
            binding = FragmentWeatherHourlyForecastCardBinding.bind(view);
            settingsViewModel = model;
        }

        void setHourlyForecastItem(final HourlyForecastItem hourlyForecast) {
            mHourlyForecast = hourlyForecast;

            //Set the text for what time (hour)
            int currentHour = mHourlyForecast.getHour();
            String currentHourText;
            if(currentHour == 0) {
                currentHourText = "12 AM";
            } else {
                currentHourText = currentHour <= 12 ? (currentHour + " AM") : (currentHour % 12 + " PM");
            }
            binding.textHourlyTime.setText(currentHourText);

            //Set the image
            this.binding.imageViewHourlyForecastCondition.setImageResource(
                    determineImageFromDescription(mHourlyForecast.getDescription(), currentHour));
            //Set the text for the temperature
            binding.textHourlyTemp.setText((int)mHourlyForecast.getTemp() + "°");
            if(settingsViewModel.getCurrentThemeID() == R.style.Theme_1_Harmony){
                binding.cardRoot.setCardBackgroundColor(binding.getRoot().getResources().getColor(R.color.offwhite));
                binding.textHourlyTime.setBackgroundColor(binding.getRoot().getResources().getColor(R.color.tan));
                binding.textHourlyTime.setTextColor(binding.getRoot().getResources().getColor(R.color.black));
                binding.textHourlyTemp.setTextColor(binding.getRoot().getResources().getColor(R.color.black));
            } else {
                binding.cardRoot.setCardBackgroundColor(binding.getRoot().getResources().getColor(R.color.black));
                binding.textHourlyTime.setBackgroundColor(binding.getRoot().getResources().getColor(R.color.teal_200));
                binding.textHourlyTime.setTextColor(binding.getRoot().getResources().getColor(R.color.black));
                binding.textHourlyTemp.setTextColor(binding.getRoot().getResources().getColor(R.color.teal_200));
            }
        }
    }
}
