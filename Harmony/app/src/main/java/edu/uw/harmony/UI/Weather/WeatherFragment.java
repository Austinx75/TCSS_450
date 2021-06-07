package edu.uw.harmony.UI.Weather;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.harmony.R;
import edu.uw.harmony.UI.Auth.LogIn.LogInViewModel;
import edu.uw.harmony.UI.model.LocationViewModel;
import edu.uw.harmony.UI.settings.SettingsViewModel;
import edu.uw.harmony.databinding.FragmentLogInBinding;

import edu.uw.harmony.UI.model.UserInfoViewModel;
import edu.uw.harmony.databinding.FragmentWeatherBinding;

/**
 * This fragment displays the current temperature, conditions, and user's city, as well as a 24 hour and 5 day forecast
 * for that city.
 *
 * @author  Gary Kono
 * @version 1.0
 */
public class WeatherFragment extends Fragment {
    private WeatherViewModel mWeatherModel;
    private FragmentWeatherBinding binding;


    /** ViewModel for settings */
    private SettingsViewModel settingsViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingsViewModel = new ViewModelProvider(getActivity()).get(SettingsViewModel.class);
        mWeatherModel = new ViewModelProvider(getActivity()).get(WeatherViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWeatherBinding.inflate(inflater);

        if (binding.hourlyListRoot instanceof RecyclerView) {
            ((RecyclerView) binding.hourlyListRoot).setAdapter(
                    new HourlyForecastRecyclerViewAdapter(HourlyForecastItemGenerator.getHourlyForecastList(), settingsViewModel));
        }

        if (binding.weeklyListRoot instanceof RecyclerView) {
            ((RecyclerView) binding.weeklyListRoot).setAdapter(
                    new WeeklyForecastRecyclerViewAdapter(WeeklyForecastItemGenerator.getWeeklyForecastList(), settingsViewModel));
        }

        return inflater.inflate(R.layout.fragment_weather, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.layoutComponents.setVisibility(View.GONE);
        binding.layoutWait.setVisibility(View.VISIBLE);

        //Note argument sent to the ViewModelProvider constructor. It is the Activity that
        //holds this fragment.
        UserInfoViewModel model = new ViewModelProvider(getActivity())
                .get(UserInfoViewModel.class);
        mWeatherModel.setJWT(model.getJwt());

        binding = FragmentWeatherBinding.bind(getView());

        //Putting these in onViewCreated may cause a bug. Try moving to oncreate
        mWeatherModel.addCurrentWeatherObserver(getViewLifecycleOwner(), currentWeather -> {
            binding.textViewCityPlaceholder.setText(currentWeather.city);
            binding.imageViewMainConditionsPlaceholder.setImageResource(currentWeather.image);
            binding.textViewMainTemperaturePlaceholder.setText(
                    Math.round(
                            Double.parseDouble(currentWeather.temp)) + "°");
            binding.textViewWindSpeedPlaceholder.setText("Wind Speed: " +
                    Math.round((Double.parseDouble(currentWeather.windSpeed) * 100.0) / 100.0) + " mph");
        });
        mWeatherModel.addHourlyForecastItemListObserver(getViewLifecycleOwner(), hourlyList -> {
            if (binding.hourlyListRoot instanceof RecyclerView) {
                (binding.hourlyListRoot).setAdapter(
                        new HourlyForecastRecyclerViewAdapter(hourlyList, settingsViewModel));
            }
        });
        mWeatherModel.addWeeklyForecastItemListObserver(getViewLifecycleOwner(), weeklyList -> {
            if (binding.weeklyListRoot instanceof RecyclerView) {
                (binding.weeklyListRoot).setAdapter(
                        new WeeklyForecastRecyclerViewAdapter(weeklyList, settingsViewModel));
            }
        });

        mWeatherModel.addServerRespondedObserver(getViewLifecycleOwner(), locationIsValid -> {
            //This is just an indicator that the view model's connectGet() is finished, so set components to visible
            binding.layoutComponents.setVisibility(View.VISIBLE);
            binding.layoutWait.setVisibility(View.GONE);
        });

        if(mWeatherModel.getNavigatingFromWeatherLocation()) {
            mWeatherModel.setNavigatingFromWeatherLocation(false);
        } else {
            if(mWeatherModel.getServerHasResponded()) {
                if (mWeatherModel.getStartingLocationHasBeenInitialized()) {
                    mWeatherModel.connectGet(false);
                }
            }
        }

        /** Dependent on the theme, this will set all text / image fields to a certain color. */
        if(settingsViewModel.getCurrentThemeID() == R.style.Theme_1_Harmony){
            binding.textViewCityPlaceholder.setTextColor(Color.BLACK);
            binding.textViewMainTemperaturePlaceholder.setTextColor(Color.BLACK);
            binding.textViewWindSpeedPlaceholder.setTextColor(Color.BLACK);
        } else {
            binding.textViewCityPlaceholder.setTextColor(Color.WHITE);
            binding.textViewMainTemperaturePlaceholder.setTextColor(Color.WHITE);
            binding.textViewWindSpeedPlaceholder.setTextColor(Color.WHITE);
        }
    }
}