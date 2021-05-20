package edu.uw.harmony.UI.Home;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.ColorFilter;
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
        binding = FragmentNotificationCardBinding.inflate(inflater, container, false);
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

    }
}