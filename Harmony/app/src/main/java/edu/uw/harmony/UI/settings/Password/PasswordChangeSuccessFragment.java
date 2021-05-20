package edu.uw.harmony.UI.settings.Password;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.harmony.R;
import edu.uw.harmony.UI.settings.SettingsViewModel;
import edu.uw.harmony.databinding.FragmentPasswordChangeSuccessBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class PasswordChangeSuccessFragment extends Fragment {
    /** The view binding*/
    private FragmentPasswordChangeSuccessBinding binding;
    /** The settings viwe model*/
    private SettingsViewModel settingsViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPasswordChangeSuccessBinding
                .inflate(inflater, container, false);
        return binding.getRoot();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingsViewModel = new ViewModelProvider(getActivity()).get(SettingsViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(settingsViewModel.getCurrentThemeID() == R.style.Theme_1_Harmony){
            binding.textView4.setTextColor(Color.BLACK);
        } else {
            binding.textView4.setTextColor(Color.WHITE);
        }
        binding.passwordChangeSuccesBack.setOnClickListener(button -> {
           Navigation.findNavController(getView())
                   .navigate(PasswordChangeSuccessFragmentDirections
                           .actionPasswordChangeSuccessFragmentToNavigationHome());
        });
    }
}