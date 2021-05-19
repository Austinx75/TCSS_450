package edu.uw.harmony.UI.Home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.harmony.R;
import edu.uw.harmony.UI.Contacts.ContactCardFragmentArgs;
import edu.uw.harmony.UI.Contacts.ContactListFragmentDirections;
import edu.uw.harmony.databinding.FragmentContactCardBinding;
import edu.uw.harmony.databinding.FragmentNotificationCardBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationCardFragment extends Fragment {

    private FragmentNotificationCardBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNotificationCardBinding.inflate(inflater);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}