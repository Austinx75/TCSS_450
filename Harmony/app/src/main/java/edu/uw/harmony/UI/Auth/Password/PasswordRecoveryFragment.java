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
 * @author  Larry
 * @version 1.0
 * A simple {@link Fragment} subclass.
 * This fragment allows the user to enter their email to recover their account
 */
public class PasswordRecoveryFragment extends Fragment {
    /** The binding for the password recovery fragment*/
    private FragmentPasswordRecoveryBinding binding;
    /** The settings view model*/
    private SettingsViewModel settingsViewModel;
    /** The password recovery view model*/
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingsViewModel = new ViewModelProvider(getActivity()).get(SettingsViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if(settingsViewModel.getCurrentThemeID() == R.style.Theme_1_Harmony){
            binding.editTextEmail.setTextColor(Color.BLACK);
            binding.editTextEmail.setHintTextColor(Color.BLACK);
            binding.textView.setTextColor(Color.BLACK);
        } else {
            binding.editTextEmail.setTextColor(Color.WHITE);
            binding.editTextEmail.setHintTextColor(Color.WHITE);
            binding.textView.setTextColor(Color.WHITE);
        }
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