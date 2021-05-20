package edu.uw.harmony.UI.settings.Password;

import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uw.harmony.R;
import edu.uw.harmony.UI.Auth.LogIn.LogInFragmentArgs;
import edu.uw.harmony.UI.Auth.LogIn.LogInFragmentDirections;
import edu.uw.harmony.UI.Auth.Password.PasswordRecoveryViewModel;
import edu.uw.harmony.UI.model.UserInfoViewModel;
import edu.uw.harmony.UI.settings.SettingsViewModel;
import edu.uw.harmony.databinding.FragmentPasswordChangeBinding;
import edu.uw.harmony.util.PasswordValidator;

import static edu.uw.harmony.util.PasswordValidator.checkClientPredicate;
import static edu.uw.harmony.util.PasswordValidator.checkExcludeWhiteSpace;
import static edu.uw.harmony.util.PasswordValidator.checkPwdDigit;
import static edu.uw.harmony.util.PasswordValidator.checkPwdLength;
import static edu.uw.harmony.util.PasswordValidator.checkPwdLowerCase;
import static edu.uw.harmony.util.PasswordValidator.checkPwdSpecialChar;
import static edu.uw.harmony.util.PasswordValidator.checkPwdUpperCase;

/**
 * A simple {@link Fragment} subclass.
 */
public class PasswordChangeFragment extends Fragment {

    private FragmentPasswordChangeBinding binding;
    private PasswordChangeViewModel mModel;
    private UserInfoViewModel mUserModel;
    private boolean mIsValidating;
    private SettingsViewModel settingsViewModel;
    private PasswordValidator mPassWordValidator =
            checkClientPredicate(pwd -> pwd.equals(binding.passwordChangeNew.getText().toString()))
                    .and(checkPwdLength(7))
                    .and(checkPwdSpecialChar())
                    .and(checkExcludeWhiteSpace())
                    .and(checkPwdDigit())
                    .and(checkPwdLowerCase().or(checkPwdUpperCase()));

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mIsValidating = true;
        settingsViewModel = new ViewModelProvider(getActivity()).get(SettingsViewModel.class);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPasswordChangeBinding.inflate(inflater, container, false);
        mModel = new ViewModelProvider(getActivity()).get(PasswordChangeViewModel.class);
        mModel.addResponseObserver(
                getViewLifecycleOwner(),
                this::observeResponse
                );
        if(settingsViewModel.getCurrentThemeID() == R.style.Theme_1_Harmony){
            binding.passwordChangeCurrent.setTextColor(Color.BLACK);
            binding.passwordChangeCurrent.setHintTextColor(Color.BLACK);
            binding.passwordChangeNew.setTextColor(Color.BLACK);
            binding.passwordChangeNew.setHintTextColor(Color.BLACK);
            binding.passwordChangeConfirmNew.setTextColor(Color.BLACK);
            binding.passwordChangeConfirmNew.setHintTextColor(Color.BLACK);
        } else {
            binding.passwordChangeCurrent.setTextColor(Color.WHITE);
            binding.passwordChangeCurrent.setHintTextColor(Color.WHITE);
            binding.passwordChangeNew.setTextColor(Color.WHITE);
            binding.passwordChangeNew.setHintTextColor(Color.WHITE);
            binding.passwordChangeConfirmNew.setTextColor(Color.WHITE);
            binding.passwordChangeConfirmNew.setHintTextColor(Color.WHITE);
        }
        mUserModel = new ViewModelProvider(getActivity()).get(UserInfoViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.passwordChangeConfirm.setOnClickListener(this::attemptChange);
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
                    binding.passwordChangeCurrent.setError( "Error: "
                            + response.getJSONObject("data").getString("message"));
                } catch (JSONException e) {
                    Log.e("JSON Parse Error", e.getMessage());
                }
            } else {
                if (!this.mIsValidating)
                    navigateToSuccess();
            }
        } else {
            Log.d("JSON Response", "No Response");
        }
    }

    private void navigateToSuccess() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getContext().
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
        Navigation.findNavController(getView())
                .navigate(PasswordChangeFragmentDirections
                        .actionPasswordChangeFragmentToPasswordChangeSuccessFragment());

    }

    private void attemptChange(final View button) {  validateNewPasswords();}

    private void validateNewPasswords() {
        if (binding.passwordChangeNew.getText().toString().equals(binding.passwordChangeConfirmNew.getText().toString())) {
            this.confirmPassword();
        } else {
            binding.passwordChangeNew.setError("Passwords do not match");
        }
    }

    private void confirmPassword() {
        mPassWordValidator.processResult(
                mPassWordValidator.apply(binding.passwordChangeNew.getText().toString()),
                this::verifyAuthWithServer,
                result -> binding.passwordChangeNew.setError("Password must contain: " +
                        "\nGreater than six characters," +
                        "\nAt least one digit, " +
                        "\nOne uppercase letter," +
                        "\nOne lowercase letter, " +
                        "\nOne special character, " +
                        "\nNo spaces."));
    }

    private void verifyAuthWithServer() {
        this.mIsValidating = false;
        mModel.connect(
                binding.passwordChangeCurrent.getText().toString(),
                binding.passwordChangeNew.getText().toString(),
                mUserModel.getJwt()
        );
        //This is an Asynchronous call. No statements after should rely on the
        // result of connect().
    }
}