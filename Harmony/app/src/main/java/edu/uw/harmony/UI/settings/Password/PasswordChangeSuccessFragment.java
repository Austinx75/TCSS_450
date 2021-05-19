package edu.uw.harmony.UI.settings.Password;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.harmony.R;
import edu.uw.harmony.databinding.FragmentPasswordChangeSuccessBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class PasswordChangeSuccessFragment extends Fragment {

    private FragmentPasswordChangeSuccessBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPasswordChangeSuccessBinding
                .inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.passwordChangeSuccesBack.setOnClickListener(button -> {
           Navigation.findNavController(getView())
                   .navigate(PasswordChangeSuccessFragmentDirections
                           .actionPasswordChangeSuccessFragmentToNavigationHome());
        });
    }
}