package edu.uw.harmony.UI.Auth.Validation;

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
import edu.uw.harmony.UI.Auth.LogIn.LogInFragmentArgs;
import edu.uw.harmony.UI.Auth.LogIn.LogInFragmentDirections;
import edu.uw.harmony.UI.Auth.Register.RegisterFragmentDirections;
import edu.uw.harmony.UI.settings.SettingsViewModel;
import edu.uw.harmony.databinding.FragmentValidationBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class ValidationFragment extends Fragment {

    /** ViewModel for settings */
    private SettingsViewModel settingsViewModel;

    private FragmentValidationBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentValidationBinding.inflate(inflater);
        settingsViewModel = new ViewModelProvider(getActivity()).get(SettingsViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonValidationFragmentValidate.setOnClickListener(button ->{
            ValidationFragmentDirections.ActionValidationFragmentToLogInFragment directions =
                    ValidationFragmentDirections.actionValidationFragmentToLogInFragment();

            ValidationFragmentArgs args = ValidationFragmentArgs.fromBundle(getArguments());
            if(settingsViewModel.getCurrentThemeID() == R.style.Theme_1_Harmony){
                binding.textVerificationValidation.setTextColor(Color.BLACK);
            } else {
                binding.textVerificationValidation.setTextColor(Color.WHITE);
            }


            directions.setEmail(args.getEmail());
            directions.setPassword(args.getPassword());

            Navigation.findNavController(getView()).navigate(directions);
        });
    }
}