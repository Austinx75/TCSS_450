package edu.uw.harmony.UI.Contacts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.uw.harmony.databinding.FragmentAddNewContactCardBinding;

/**
 * This is a Fragment of a single contact card instance.
 *
 * @author Jack Lin
 * @version 1.0
 */
public class AddNewContactCardFragment extends Fragment {

    /** binding variable that allows interaction with views */
    private FragmentAddNewContactCardBinding binding;
    AddNewContactViewModel mModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddNewContactCardBinding.inflate(inflater);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ContactCardFragmentArgs args = ContactCardFragmentArgs.fromBundle(getArguments());
        binding = FragmentAddNewContactCardBinding.bind(getView());
    }

}