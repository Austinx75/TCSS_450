package edu.uw.harmony.UI.Home;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import edu.uw.harmony.R;
import edu.uw.harmony.UI.settings.SettingsViewModel;
import edu.uw.harmony.databinding.FragmentNotificationCardBinding;

/**
 * @author Austin Scott
 * @version 1.1
 * A simple {@link Fragment} subclass.
 */
public class NotificationCardFragment extends Fragment {

    /** This is the view model for settings*/
    SettingsViewModel settingsViewModel;

    /** Binding for Notification Card Binding*/
    private FragmentNotificationCardBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNotificationCardBinding.inflate(inflater);
        settingsViewModel = new ViewModelProvider(getActivity()).get(SettingsViewModel.class);

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    /**
     * It calls upon the current Theme, and determining what it is it will set the proper colors.
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(settingsViewModel.getCurrentThemeID() == R.style.Theme_1_Harmony){
            binding.cardRoot.setBackgroundColor(getResources().getColor(R.color.colorOffWhite));
            binding.imageMessageNotificationHome.setColorFilter(Color.BLACK);
            binding.textMessageHome.setTextColor(getResources().getColor(R.color.black));
            binding.textToPersonHome.setTextColor(getResources().getColor(R.color.black));
            binding.textTOHome.setTextColor(getResources().getColor(R.color.black));
        } else {
            binding.cardRoot.setBackgroundColor(Color.BLACK);
            binding.imageMessageNotificationHome.setColorFilter(Color.WHITE);
            binding.textMessageHome.setTextColor(getResources().getColor(R.color.teal_200));
            binding.textToPersonHome.setTextColor(getResources().getColor(R.color.teal_200));
            binding.textTOHome.setTextColor(getResources().getColor(R.color.teal_200));
        }
    }
}