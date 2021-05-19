package edu.uw.harmony.UI.Auth.Password;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uw.harmony.R;
import edu.uw.harmony.UI.Auth.Register.RegisterFragmentDirections;
import edu.uw.harmony.UI.model.UserInfoViewModel;
import edu.uw.harmony.UI.settings.SettingsViewModel;
import edu.uw.harmony.databinding.FragmentPasswordRecoveryBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class PasswordRecoveryFragment extends Fragment {

    private FragmentPasswordRecoveryBinding binding;
    private SettingsViewModel settingsViewModel;
    private PasswordRecoveryViewModel mModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mModel = new ViewModelProvider(getActivity()).get(PasswordRecoveryViewModel.class);
        binding = FragmentPasswordRecoveryBinding.inflate(inflater);
        settingsViewModel = new ViewModelProvider(getActivity()).get(SettingsViewModel.class);
        mModel.addResponseObserver(
                getViewLifecycleOwner(),
                response -> {
                    Log.e("RESPONSE FOR RECOVER PASSWORD", response + "");
                });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.buttonPasswordRecoverFragmentContinue.setOnClickListener(button -> {
            View current = getActivity().getCurrentFocus();
            if (current != null) {
                InputMethodManager imm = (InputMethodManager) getContext().
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(current.getWindowToken(), 0);
                }
            }
            mModel.connect(binding.editTextEmail.getText().toString());
            PasswordRecoveryFragmentDirections.ActionPasswordRecoveryFragmentToRecoveryCompleteFragment directions =
                    PasswordRecoveryFragmentDirections.actionPasswordRecoveryFragmentToRecoveryCompleteFragment();
            directions.setEmail(binding.editTextEmail.getText().toString());
            Navigation.findNavController(getView()).navigate(directions);
        });
    }
}