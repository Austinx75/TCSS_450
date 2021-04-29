package edu.uw.harmony.UI.Auth.LogIn;

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

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uw.harmony.databinding.FragmentLogInBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class LogInFragment extends Fragment {

    private FragmentLogInBinding binding;
    private LogInViewModel mSignInModel;

    public LogInFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSignInModel = new ViewModelProvider(getActivity())
                .get(LogInViewModel.class);
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLogInBinding.inflate(inflater);
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

        binding.buttonLoginFragmentLogin.setOnClickListener(button->Verify(view));


        mSignInModel.addResponseObserver(
                getViewLifecycleOwner(),
                this::observeResponse);


        LogInFragmentArgs args = LogInFragmentArgs.fromBundle(getArguments());
        binding.editTextEmail.setText(args.getEmail().equals("default") ? "" : args.getEmail());
        binding.editTextPassword.setText(args.getPassword().equals("default") ? "" : args.getPassword());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void Verify(View view){
        if(binding.editTextEmail.getText().toString().isEmpty()) {
            binding.editTextEmail.setError("Empty Email");
        } else if(binding.editTextPassword.getText().toString().isEmpty()){
            binding.editTextPassword.setError("Empty Password");
        }

        verifyAuthWithServer();

    }

    /**
     * This helper method is creating a JSON Web Token (JWT). In future labs, the JWT will
     * be created and sent to us from the Web Service. For now, we will "fake" that and create
     * the JWT client-side. This is ANTI-PATTERN!!! Do not create JWTs client-side.
     *
     * @param email the email used to encode into the JWT
     * @return the resulting JWT
     */
    private String generateJwt(final String email) {
        String token;
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret key don't use a string literal in " +
                    "production code!!!");
            token = JWT.create()
                    .withIssuer("auth0")
                    .withClaim("email", email)
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException("JWT Failed to Create.");
        }
        return token;
    }

    private void verifyAuthWithServer() {
        mSignInModel.connect(
                binding.editTextEmail.getText().toString(),
                binding.editTextPassword.getText().toString());

        //This is an Asynchronous call. No statements after should rely on the result of connect.

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
                            "Error Authenticating: " +
                                    response.getJSONObject("data").getString("message"));
                } catch (JSONException e) {
                    Log.e("JSON Parse Error", e.getMessage());
                }
            } else {

                try {

                    navigateToSuccess(
                            binding.editTextEmail.getText().toString(), response.getString("token")
                    );
                } catch (JSONException e) {
                    Log.e("JSON Parse Error", e.getMessage());
                }
            }
        } else {
            Log.d("JSON Response", "No Response");
        }

    }

}