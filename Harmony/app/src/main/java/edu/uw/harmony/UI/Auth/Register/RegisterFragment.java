package edu.uw.harmony.UI.Auth.Register;


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
import edu.uw.harmony.UI.Avatar.AvatarViewModel;
import edu.uw.harmony.UI.settings.SettingsViewModel;
import edu.uw.harmony.databinding.FragmentRegisterBinding;
import edu.uw.harmony.util.PasswordValidator;

import static edu.uw.harmony.util.PasswordValidator.checkClientPredicate;
import static edu.uw.harmony.util.PasswordValidator.checkExcludeWhiteSpace;
import static edu.uw.harmony.util.PasswordValidator.checkPwdDigit;
import static edu.uw.harmony.util.PasswordValidator.checkPwdLength;
import static edu.uw.harmony.util.PasswordValidator.checkPwdLowerCase;
import static edu.uw.harmony.util.PasswordValidator.checkPwdSpecialChar;
import static edu.uw.harmony.util.PasswordValidator.checkPwdUpperCase;


/**
 * @author Austin Scott
 * A simple {@link Fragment} subclass.
 * The register fragment allows the user to enter a
 * First name, Last name, email, password, and retyped password.
 * The main chunk of this fragment is verifying all the credentials.
 * Once all criteria is met, it calls the auth method which uses a connect method to
 * verify with our backend.
 * It then passes the email and password to login fragment.
 */
public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;

    private RegisterViewModel mRegisterModel;

    /** ViewModel for settings */
    private SettingsViewModel settingsViewModel;
    private AvatarViewModel avatarViewModel;

    private PasswordValidator mNameValidator = checkPwdLength(1);

    private PasswordValidator mEmailValidator = checkPwdLength(2)
            .and(checkExcludeWhiteSpace())
            .and(checkPwdSpecialChar("@"));

    private PasswordValidator mPassWordValidator =
            checkClientPredicate(pwd -> pwd.equals(binding.editTextPassword1.getText().toString()))
                    .and(checkPwdLength(7))
                    .and(checkPwdSpecialChar())
                    .and(checkExcludeWhiteSpace())
                    .and(checkPwdDigit())
                    .and(checkPwdLowerCase().or(checkPwdUpperCase()));

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingsViewModel = new ViewModelProvider(getActivity()).get(SettingsViewModel.class);
        mRegisterModel = new ViewModelProvider(getActivity())
                .get(RegisterViewModel.class);
        avatarViewModel = new ViewModelProvider(getActivity()).get(AvatarViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater);
        /** Dependent on the theme, this will set all text / image fields to a certain color. */
        if(settingsViewModel.getCurrentThemeID() == R.style.Theme_1_Harmony){
            binding.editTextPassword.setTextColor(Color.BLACK);
            binding.editTextEmail.setTextColor(Color.BLACK);
            binding.editTextPassword.setHintTextColor(Color.BLACK);
            binding.editTextEmail.setHintTextColor(Color.BLACK);
            binding.editTextFirstName.setHintTextColor(Color.BLACK);
            binding.editTextFirstName.setTextColor(Color.BLACK);
            binding.editTextLastName.setTextColor(Color.BLACK);
            binding.editTextLastName.setHintTextColor(Color.BLACK);
            binding.editTextPassword1.setHintTextColor(Color.BLACK);
            binding.editTextPassword1.setTextColor(Color.BLACK);
        } else {
            binding.editTextPassword.setTextColor(Color.WHITE);
            binding.editTextPassword.setHintTextColor(Color.WHITE);
            binding.editTextEmail.setHintTextColor(Color.WHITE);
            binding.editTextEmail.setTextColor(Color.WHITE);
            binding.editTextFirstName.setHintTextColor(Color.WHITE);
            binding.editTextFirstName.setTextColor(Color.WHITE);
            binding.editTextLastName.setTextColor(Color.WHITE);
            binding.editTextLastName.setHintTextColor(Color.WHITE);
            binding.editTextPassword1.setHintTextColor(Color.WHITE);
            binding.editTextPassword1.setTextColor(Color.WHITE);
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RegisterFragmentArgs args = RegisterFragmentArgs.fromBundle(getArguments());
        binding.buttonRegisterFragmentRegister.setOnClickListener(this::attemptRegister);
        binding.changeAvatar.setImageResource(args.getAvatar());
        binding.changeAvatar.setOnClickListener(button ->  Navigation.findNavController(getView()).
                navigate(RegisterFragmentDirections.actionRegisterFragmentToAvatarListFragment()));
        mRegisterModel.addResponseObserver(getViewLifecycleOwner(),
                this::observeResponse);
    }

    private void attemptRegister(final View button) {
        validateFirst();
    }

    private void validateFirst() {
        mNameValidator.processResult(
                mNameValidator.apply(binding.editTextFirstName.getText().toString().trim()),
                this::validateLast,
                result -> binding.editTextFirstName.setError("Please enter a first name."));
    }

    private void validateLast() {
        mNameValidator.processResult(
                mNameValidator.apply(binding.editTextLastName.getText().toString().trim()),
                this::validateEmail,
                result -> binding.editTextLastName.setError("Please enter a last name."));
    }

    private void validateEmail() {
        mEmailValidator.processResult(
                mEmailValidator.apply(binding.editTextEmail.getText().toString().trim()),
                this::validatePasswordsMatch,
                result -> binding.editTextEmail.setError("Please enter a valid Email address."));
    }

    private void validatePasswordsMatch() {
        PasswordValidator matchValidator =
                checkClientPredicate(
                        pwd -> pwd.equals(binding.editTextPassword1.getText().toString().trim()));

        mEmailValidator.processResult(
                matchValidator.apply(binding.editTextPassword.getText().toString().trim()),
                this::validatePassword,
                result -> binding.editTextPassword.setError("Passwords must match."));
    }

    /**
     * If the password does not meet certain criteria, It will set an error listing all criteria.
     */
    private void validatePassword() {
        mPassWordValidator.processResult(
                mPassWordValidator.apply(binding.editTextPassword.getText().toString()),
                this::verifyAuthWithServer,
                result -> binding.editTextPassword.setError("Password must contain: " +
                        "\nGreater than six characters," +
                        "\nAt least one digit, " +
                        "\nOne uppercase letter," +
                        "\nOne lowercase letter, " +
                        "\nOne special character, " +
                        "\nNo spaces."));
    }

    private void verifyAuthWithServer() {
        RegisterFragmentArgs args = RegisterFragmentArgs.fromBundle(getArguments());
        mRegisterModel.connect(
                binding.editTextFirstName.getText().toString(),
                binding.editTextLastName.getText().toString(),
                binding.editTextEmail.getText().toString(),
                binding.editTextPassword.getText().toString(),
                args.getAvatar());
        //This is an Asynchronous call. No statements after should rely on the
        // result of connect().
    }

    /**
     * Navigates to login upon successful registration.
     */
    private void navigateToLogin() {

        RegisterFragmentDirections.ActionRegisterFragmentToValidationFragment directions =
                RegisterFragmentDirections.actionRegisterFragmentToValidationFragment();

        directions.setEmail(binding.editTextEmail.getText().toString());
        directions.setPassword(binding.editTextPassword.getText().toString());

        Navigation.findNavController(getView()).navigate(directions);

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
                    binding.editTextEmail.setError( "Error Authenticating: "
                            + response.getJSONObject("data").getString("message"));
                } catch (JSONException e) {
                    Log.e("JSON Parse Error", e.getMessage());
                }
            } else {
                navigateToLogin();
            }
        } else {
            Log.d("JSON Response", "No Response");
        }
    }
}
