package edu.uw.harmony.UI.Auth.LogIn;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uw.harmony.R;
import edu.uw.harmony.UI.settings.SettingsViewModel;
import edu.uw.harmony.databinding.FragmentLogInBinding;
import edu.uw.harmony.util.PasswordValidator;

import static edu.uw.harmony.util.PasswordValidator.checkExcludeWhiteSpace;
import static edu.uw.harmony.util.PasswordValidator.checkPwdLength;
import static edu.uw.harmony.util.PasswordValidator.checkPwdSpecialChar;


/**
 * A simple {@link Fragment} subclass.
 * This Fragment subclass enables the user to
 * type in a username and password. It then validates the credentials,
 * and an observer will pass on a token to the main activity.
 */
public class LogInFragment extends Fragment {

    private FragmentLogInBinding binding;
    private LogInViewModel mSignInModel;

    /** ViewModel for settings */
    private SettingsViewModel settingsViewModel;

    private PasswordValidator mEmailValidator = checkPwdLength(2)
            .and(checkExcludeWhiteSpace())
            .and(checkPwdSpecialChar("@"));

    private PasswordValidator mPassWordValidator = checkPwdLength(1)
            .and(checkExcludeWhiteSpace());

    public LogInFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingsViewModel = new ViewModelProvider(getActivity()).get(SettingsViewModel.class);
        mSignInModel = new ViewModelProvider(getActivity())
                .get(LogInViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLogInBinding.inflate(inflater);
        if(settingsViewModel.getCurrentThemeID() == R.style.Theme_1_Harmony){
            binding.editTextPassword.setTextColor(Color.BLACK);
            binding.editTextEmail.setTextColor(Color.BLACK);
            binding.editTextPassword.setHintTextColor(Color.BLACK);
            binding.editTextEmail.setHintTextColor(Color.BLACK);
            binding.buttonLoginFragmentRegister.setTextColor(getResources().getColor(R.color.orange));
            binding.loginHarmonyLogo.setColorFilter(Color.BLACK);
        } else {
            binding.editTextPassword.setTextColor(Color.WHITE);
            binding.editTextPassword.setHintTextColor(Color.WHITE);
            binding.editTextEmail.setHintTextColor(Color.WHITE);
            binding.editTextEmail.setTextColor(Color.WHITE);
            binding.buttonLoginFragmentRegister.setTextColor(getResources().getColor(R.color.teal_200));
            binding.loginHarmonyLogo.setColorFilter(Color.WHITE);
        }
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonLoginFragmentRegister.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(
                        LogInFragmentDirections.actionLogInFragmentToRegisterFragment()
                ));

        binding.buttonLoginFragmentLogin.setOnClickListener(this::attemptSignIn);

        mSignInModel.addResponseObserver(
                getViewLifecycleOwner(),
                this::observeResponse);

        LogInFragmentArgs args = LogInFragmentArgs.fromBundle(getArguments());
        binding.editTextEmail.setText(args.getEmail().equals("default") ? "" : args.getEmail());
        binding.editTextPassword.setText(args.getPassword().equals("default") ? "" : args.getPassword());
    }

    private void attemptSignIn(final View button) {
        validateEmail();
    }

    private void validateEmail() {
        mEmailValidator.processResult(
                mEmailValidator.apply(binding.editTextEmail.getText().toString().trim()),
                this::validatePassword,
                result -> binding.editTextEmail.setError("Please enter a valid Email address."));
    }

    private void validatePassword() {
        mPassWordValidator.processResult(
                mPassWordValidator.apply(binding.editTextPassword.getText().toString()),
                this::verifyAuthWithServer,
                result -> binding.editTextPassword.setError("Please enter a valid Password."));
    }


    /**
     * This connects and verifies with our web service by using a connect method
     * that is instantiated in the view model.
     */

    private void verifyAuthWithServer() {
        mSignInModel.connect(
                binding.editTextEmail.getText().toString(),
                binding.editTextPassword.getText().toString());
        //This is an Asynchronous call. No statements after should rely on the
        // result of connect().

    }

    /**
     * Helper to abstract the navigation to the Activity past Authentication.
     * @param email users email
     * @param jwt the JSON Web Token supplied by the server
     */
    private void navigateToSuccess(final String email, final String jwt) {
        Navigation.findNavController(getView())
                .navigate(LogInFragmentDirections
                        .actionLogInFragmentToMainActivity(jwt, email));
        getActivity().finish();
    }

    /**
     * An observer on the HTTP Response from the web server. This observer should be
     * attached to SignInViewModel.
     *
     * @param response the Response from the server
     */
    private void observeResponse(final JSONObject response) {

        if (response.length() > 0) {
            if (response.has("code")) {
                try {
                    binding.editTextEmail.setError(
                            "Error Authenticating: " + response.getJSONObject("data").getString("message"));
                } catch (JSONException e) {
                    Log.e("JSON Parse Error", e.getMessage());
                }
            } else {
                try {
                    navigateToSuccess(
                            binding.editTextEmail.getText().toString(), response.getString("token") );
                } catch (JSONException e) {
                    Log.e("JSON Parse Error", e.getMessage());
                }
            }
        } else {
            Log.d("JSON Response", "No Response");
        }
    }
}
