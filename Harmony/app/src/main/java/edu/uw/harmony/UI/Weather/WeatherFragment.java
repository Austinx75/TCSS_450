package edu.uw.harmony.UI.Weather;

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
    private WeatherViewModel mModel;

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = new ViewModelProvider(getActivity()).get(WeatherViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mModel.connectGet();

        //Note argument sent to the ViewModelProvider constructor. It is the Activity that
        //holds this fragment.
        UserInfoViewModel model = new ViewModelProvider(getActivity())
                .get(UserInfoViewModel.class);
        mModel.setJWT(model.getJwt());

        FragmentWeatherBinding binding = FragmentWeatherBinding.bind(getView());
        mModel.setWeatherBinding(binding);

        mModel.addHourlyForecastItemListObserver(getViewLifecycleOwner(), hourlyList -> {
            if (binding.hourlyListRoot instanceof RecyclerView) {
                (binding.hourlyListRoot).setAdapter(
                        new HourlyForecastRecyclerViewAdapter(hourlyList));
            }
        });
        mModel.addWeeklyForecastItemListObserver(getViewLifecycleOwner(), weeklyList -> {
            if (binding.weeklyListRoot instanceof RecyclerView) {
                (binding.weeklyListRoot).setAdapter(
                        new WeeklyForecastRecyclerViewAdapter(weeklyList));
            }
        });

        binding.layoutWait.setVisibility(View.GONE);
    }
}