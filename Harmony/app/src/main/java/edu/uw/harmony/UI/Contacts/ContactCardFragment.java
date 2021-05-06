package edu.uw.harmony.UI.Contacts;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Random;

import edu.uw.harmony.R;
import edu.uw.harmony.UI.Auth.LogIn.LogInFragmentDirections;
import edu.uw.harmony.UI.Auth.Register.RegisterFragmentDirections;
import edu.uw.harmony.UI.Chat.ChatsFragmentDirections;
import edu.uw.harmony.databinding.FragmentContactCardBinding;
import edu.uw.harmony.databinding.FragmentLogInBinding;

/**
 * This is a Fragment of a single contact card instance.
 *
 * @author Jack Lin
 * @version 1.0
 */

public class ContactCardFragment extends Fragment {
    private FragmentContactCardBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContactCardBinding.inflate(inflater);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ContactCardFragmentArgs args = ContactCardFragmentArgs.fromBundle(getArguments());
        binding = FragmentContactCardBinding.bind(getView());
        binding.contactCard.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(
                        ContactListFragmentDirections.actionNavigationContactToContactFragment(binding.contactUsername.getText().toString(),  binding.contactStatus.getText().toString())));

        binding.contactMessage.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(
                        ContactListFragmentDirections.actionNavigationContactToNavigationNewChat()));

    }

}