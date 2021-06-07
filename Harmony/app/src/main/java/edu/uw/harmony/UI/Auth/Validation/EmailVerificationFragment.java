package edu.uw.harmony.UI.Auth.Validation;

import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.transition.Visibility;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uw.harmony.R;
import edu.uw.harmony.UI.Auth.LogIn.LogInViewModel;
import edu.uw.harmony.UI.Auth.Register.RegisterFragmentArgs;
import edu.uw.harmony.UI.Auth.Register.RegisterFragmentDirections;
import edu.uw.harmony.UI.model.UserInfoViewModel;
import edu.uw.harmony.UI.settings.SettingsViewModel;
import edu.uw.harmony.databinding.FragmentEmailVerificationBinding;
import edu.uw.harmony.databinding.FragmentRegisterBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmailVerificationFragment extends Fragment {
    FragmentEmailVerificationBinding binding;
    EmailVerificationViewModel mModel;
    UserInfoViewModel mUserModel;
    SettingsViewModel settingsViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        BottomNavigationView navBar = getActivity().findViewById(R.id.nav_view);
        navBar.setVisibility(View.GONE);
        mModel = new ViewModelProvider(getActivity())
                .get(EmailVerificationViewModel.class);
        mUserModel = new ViewModelProvider(getActivity()).get(UserInfoViewModel.class);
        settingsViewModel = new ViewModelProvider(getActivity()).get(SettingsViewModel.class);
        binding = FragmentEmailVerificationBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(settingsViewModel.getCurrentThemeID() == R.style.Theme_1_Harmony){
            binding.textView2.setTextColor(Color.BLACK);
            binding.editTextEmail2.setTextColor(Color.BLACK);
            binding.editTextEmail2.setHintTextColor(Color.BLACK);
        } else {
            binding.textView2.setTextColor(Color.WHITE);
            binding.editTextEmail2.setTextColor(Color.WHITE);
            binding.editTextEmail2.setHintTextColor(Color.WHITE);
        }
        binding.button2.setOnClickListener(button -> attemptCodeSend());
        mModel.addResponseObserver(
                getViewLifecycleOwner(),
                this::observeResponse);
    }

    public void attemptCodeSend() {
        if (binding.editTextEmail2.getText().length() == 0) {
            binding.editTextEmail2.setError("Please enter the code sent to you");
        } else {
            attemptConnection();
        }
    }

    public void attemptConnection() {
        mModel.connect(mUserModel.getJwt(), binding.editTextEmail2.getText().toString());
    }

    private void navToSuccess() {
        Navigation.findNavController(getView())
                .navigate(
                        EmailVerificationFragmentDirections
                                .actionEmailVerificationFragmentToNavigationHome());
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        BottomNavigationView navBar = getActivity().findViewById(R.id.nav_view);
//        navBar.setVisibility(View.VISIBLE);
//    }

    /**
     * An observer on the HTTP Response from the web server. This observer should be
     * attached to SignInViewModel.
     *
     * @param response the Response from the server
     */
    private void observeResponse(final JSONObject response) {
        Log.e("Response", response + "");
        if (response.length() > 0) {
            if (response.has("code")) {
                try {
                    binding.editTextEmail2.setError(
                            "Error Authenticating: " + response.getJSONObject("data").getString("message"));
                } catch (JSONException e) {
                    Log.e("JSON Parse Error", e.getMessage());
                }
            } else {
                try {
                    Log.e("Verified account", response.get("success") + "");
                    if (response.getBoolean("success")){
                        mUserModel.setVerified(1);
                        SharedPreferences prefs =
                                getActivity().getSharedPreferences(
                                        getString(R.string.keys_shared_prefs),
                                        Context.MODE_PRIVATE);//Store the credentials in SharedPrefs
                        prefs.edit().putString(getString(R.string.keys_prefs_verified), 1+"").apply();
                        navToSuccess();
                    }
                } catch (JSONException e) {
                    Log.e("JSON Parse Error", e.getMessage());
                }
            }
        } else {
            Log.d("JSON Response", "No Response");
        }
    }

}