package edu.uw.harmony.UI.Auth.Register;

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

import edu.uw.harmony.databinding.FragmentRegisterBinding;


/**
 * A simple {@link Fragment} subclass.

 */
public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;

    private RegisterViewModel mRegisterModel;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRegisterModel = new ViewModelProvider(getActivity())
                .get(RegisterViewModel.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonRegisterFragmentRegister.setOnClickListener(button->onVerify(view));
    }


    private void onVerify(View view) {
        boolean check = true;

        if (binding.editTextFirstName.getText().toString().isEmpty()) {
            binding.editTextFirstName.setError("Empty First Name");
            check = false;
        }
        if (binding.editTextLastName.getText().toString().isEmpty()) {
            binding.editTextLastName.setError("Empty Last Name");
            check = false;
        }
        if (binding.editTextEmail.getText().toString().isEmpty() || !binding.editTextEmail.getText().toString().contains("@")) {
            binding.editTextEmail.setError("Email is Empty or missing @ Symbol");
            check = false;
        }
        if (binding.editTextPassword.getText().toString().isEmpty()) {
            binding.editTextPassword.setError("Empty Password");
            check = false;
        }
        if (binding.editTextPassword1.getText().toString().isEmpty()) {
            binding.editTextPassword1.setError("Re-Enter Password");
            check = false;
        }
        if(binding.editTextPassword.getText().toString().length() < 6){
            binding.editTextPassword.setError("Password is less than six characters");
            check = false;
        }
        if(!binding.editTextPassword.getText().toString().equals(binding.editTextPassword1.getText().toString())){
            binding.editTextPassword.setError("Password does not match");
            check = false;
        }
        if(!binding.editTextPassword1.getText().toString().equals(binding.editTextPassword.getText().toString())){
            binding.editTextPassword1.setError("Password does not match");
            check = false;
        }
        if(binding.editTextPassword.getText().toString().contains(" ")){
            binding.editTextPassword.setError("Cannot have space in password");
            check = false;
        }

        verifyAuthWithServer();
        if(check){
            mRegisterModel.addResponseObserver(getViewLifecycleOwner(),
                    this::observeResponse);
        }

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
        mRegisterModel.connect(
                binding.editTextFirstName.getText().toString(),
                binding.editTextLastName.getText().toString(),
                binding.editTextEmail.getText().toString(),
                binding.editTextPassword.getText().toString());

        //This is an Asynchronous call. No statemnts after should rely on the result of connect.
    }

    private void navigateToLogin() {
//        Navigation.findNavController(getView())
//                .navigate(RegisterFragmentDirections
//                .actionRegisterFragmentToSignInFragment());

        RegisterFragmentDirections.ActionRegisterFragmentToLogInFragment directions =
                RegisterFragmentDirections.actionRegisterFragmentToLogInFragment();

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
        if(response.length() > 0){
            if(response.has("code")){
                try{
                    binding.editTextEmail.setError(
                            "Error Authenticationg: " +
                                    response.getJSONObject("data").getString("message"));
                } catch (JSONException e){
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