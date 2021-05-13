package edu.uw.harmony.util;

import android.os.Binder;
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

import edu.uw.harmony.R;
import edu.uw.harmony.UI.Auth.LogIn.LogInFragmentArgs;
import edu.uw.harmony.UI.Auth.LogIn.LogInFragmentDirections;
import edu.uw.harmony.UI.Auth.LogIn.LogInViewModel;
import edu.uw.harmony.databinding.FragmentLogInBinding;
import edu.uw.harmony.databinding.FragmentSettingsBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment{

    private FragmentSettingsBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(inflater);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonTheme1.setOnClickListener(button ->
                    setThemeOne()
        );

        binding.buttonTheme2.setOnClickListener(button ->
                setThemeTwo()
        );
    }

    private void setThemeOne(){
        getActivity().setTheme(R.style.theme1);
        Log.d("Test", "Theme 1");
    }

    private void setThemeTwo(){
        getActivity().setTheme(R.style.theme2);
        Log.d("Test", "Theme 1");
    }



}