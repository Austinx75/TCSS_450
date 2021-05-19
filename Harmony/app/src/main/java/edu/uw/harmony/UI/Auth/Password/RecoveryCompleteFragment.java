package edu.uw.harmony.UI.Auth.Password;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.harmony.R;
import edu.uw.harmony.UI.settings.SettingsViewModel;
import edu.uw.harmony.databinding.FragmentRecoveryCompleteBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecoveryCompleteFragment extends Fragment {

    private FragmentRecoveryCompleteBinding binding;
    private SettingsViewModel settingsViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRecoveryCompleteBinding.inflate(inflater);
        settingsViewModel = new ViewModelProvider(getActivity()).get(SettingsViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.buttonPasswordRecoveryFragmentContinue.setOnClickListener(button -> {
            RecoveryCompleteFragmentArgs args = RecoveryCompleteFragmentArgs.fromBundle(getArguments());
            RecoveryCompleteFragmentDirections.ActionRecoveryCompleteFragmentToLogInFragment directions =
                    RecoveryCompleteFragmentDirections.actionRecoveryCompleteFragmentToLogInFragment();
            directions.setEmail(args.getEmail());
            Navigation.findNavController(getView()).navigate(directions);
        });
    }
}