package edu.uw.harmony.UI.Contacts;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.View;

import edu.uw.harmony.databinding.FragmentContactCardBinding;

public class ContactCardFragment extends Fragment {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ContactCardFragmentArgs args = ContactCardFragmentArgs.fromBundle(getArguments());
        FragmentContactCardBinding binding = FragmentContactCardBinding.bind(getView());
        binding.contactUsername.setText(args.getContact().getUsername());
        binding.contactStatus.setText(args.getContact().getStatus());
    }
}