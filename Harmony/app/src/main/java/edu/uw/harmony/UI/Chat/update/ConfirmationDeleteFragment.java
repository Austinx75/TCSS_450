package edu.uw.harmony.UI.Chat.update;

import android.content.res.ColorStateList;
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

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import edu.uw.harmony.R;
import edu.uw.harmony.UI.Contacts.ContactListViewModel;
import edu.uw.harmony.UI.Contacts.ContactRecyclerViewAdapter;
import edu.uw.harmony.UI.model.UserInfoViewModel;
import edu.uw.harmony.UI.settings.SettingsViewModel;
import edu.uw.harmony.databinding.FragmentConfirmationDeleteBinding;
import edu.uw.harmony.databinding.FragmentUpdateChatBinding;

/**
 * A fragment used to confirm to the user that they left the chat room that they were in.
 * A simple {@link Fragment} subclass.
 */
public class ConfirmationDeleteFragment extends Fragment {
    /** View binding for the delete confirmation fragment*/
    FragmentConfirmationDeleteBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentConfirmationDeleteBinding.inflate(inflater);

        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.continueButton.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(ConfirmationDeleteFragmentDirections.actionConfirmationDeleteFragmentToNavigationChatList()));
    }
}