package edu.uw.harmony.UI.Contacts;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.harmony.R;
import edu.uw.harmony.UI.model.UserInfoViewModel;
import edu.uw.harmony.databinding.FragmentAddNewContactBinding;
import edu.uw.harmony.databinding.FragmentContactCardBinding;

/**
 * This is a a fragment for adding new contacts
 *
 * @author Jack Lin
 * @version 1.1
 */
public class AddNewContactFragment extends Fragment {

    private UserInfoViewModel mUserViewModel;
    private ContactListViewModel mModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserViewModel = new ViewModelProvider(getActivity()).get(UserInfoViewModel.class);
        mModel = new ViewModelProvider(getActivity()).get(ContactListViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_new_contact, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentAddNewContactBinding binding = FragmentAddNewContactBinding.bind(getView());
        binding.searchNewContact.setOnClickListener(button -> mModel.contactAdd(mUserViewModel.getJwt(), Integer.parseInt(binding.enterName.getText().toString())));
    }
}