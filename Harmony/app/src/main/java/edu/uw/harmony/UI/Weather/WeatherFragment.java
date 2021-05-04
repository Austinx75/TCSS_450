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
import edu.uw.harmony.databinding.FragmentWeatherBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment {
    private FragmentWeatherBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        View hourlyRecyclerView = view.findViewById(R.id.hourly_list_root);
        View weeklyRecyclerView = view.findViewById(R.id.weekly_list_root);

        if (hourlyRecyclerView instanceof RecyclerView) {
            ((RecyclerView) hourlyRecyclerView).setAdapter(
                    new HourlyForecastRecyclerViewAdapter(HourlyForecastItemGenerator.getHourlyForecastList()));
        }

        if (weeklyRecyclerView instanceof RecyclerView) {
            ((RecyclerView) weeklyRecyclerView).setAdapter(
                    new WeeklyForecastRecyclerViewAdapter(WeeklyForecastItemGenerator.getWeeklyForecastList()));
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Note argument sent to the ViewModelProvider constructor. It is the Activity that
        // holds this fragment.
        //NOTE: ENABLE THIS IN FUTURE IF ARGUMENTS ARE NEEDED
//        UserInfoViewModel model = new ViewModelProvider(getActivity())
//                .get(UserInfoViewModel.class);UserInfoViewModel model = new ViewModelProvider(getActivity())
//                .get(UserInfoViewModel.class);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}