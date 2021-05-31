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
import edu.uw.harmony.databinding.FragmentWeatherWeeklyForecastCardBinding;

import static edu.uw.harmony.util.WeatherUtils.determineImageFromDescription;

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

    private SettingsViewModel sModel;

    public WeeklyForecastRecyclerViewAdapter(List<WeeklyForecastItem> items, SettingsViewModel model) {
        this.mWeeklyForecasts = items;
        sModel = model;
    }

    @Override
    public int getItemCount() {
        return mWeeklyForecasts.size();
    }

    @NonNull
    @Override
    public WeeklyForecastRecyclerViewAdapter.WeeklyForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WeeklyForecastRecyclerViewAdapter.WeeklyForecastViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_weather_weekly_forecast_card, parent, false), sModel);
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
        private SettingsViewModel settingsViewModel;

        public WeeklyForecastViewHolder(View view, SettingsViewModel model) {
            super(view);
            mView = view;
            binding = FragmentWeatherWeeklyForecastCardBinding.bind(view);
            settingsViewModel = model;
        }

        void setWeeklyForecastItem(final WeeklyForecastItem weeklyForecast) {
            mWeeklyForecast = weeklyForecast;

            //Set the text for what day of the week
            String currentDay = mWeeklyForecast.getDay();
            binding.textWeeklyDay.setText(mWeeklyForecast.getDay());

            //Set the image
            //Uses hourOfDay:10 just to tell determineImageFromDescription that it's not night time.
            this.binding.imageViewWeeklyForecastCondition.setImageResource(
                    determineImageFromDescription(mWeeklyForecast.getDescription(), 10));

            //Set the text for the temperature
            binding.textWeeklyTemp.setText((int)mWeeklyForecast.getTemp() + "°");
            if(settingsViewModel.getCurrentThemeID() == R.style.Theme_1_Harmony){
                binding.cardRoot.setCardBackgroundColor(binding.getRoot().getResources().getColor(R.color.offwhite));
                binding.textWeeklyDay.setTextColor(Color.BLACK);
                binding.textWeeklyDay.setBackgroundColor(binding.getRoot().getResources().getColor(R.color.tan));
                binding.textWeeklyTemp.setTextColor(Color.BLACK);
            } else {
                binding.cardRoot.setCardBackgroundColor(binding.getRoot().getResources().getColor(R.color.black));
                binding.textWeeklyDay.setTextColor(binding.getRoot().getResources().getColor(R.color.black));
                binding.textWeeklyDay.setBackgroundColor(binding.getRoot().getResources().getColor(R.color.teal_200));
                binding.textWeeklyTemp.setTextColor(binding.getRoot().getResources().getColor(R.color.teal_200));
            }
        }
    }

}
