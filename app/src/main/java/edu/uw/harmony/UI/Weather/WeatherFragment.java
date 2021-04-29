package edu.uw.harmony.UI.Weather;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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
        binding = FragmentWeatherBinding.inflate(inflater, container, false);
        return binding.getRoot();
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