package edu.uw.harmony.UI.Contacts;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import edu.uw.harmony.R;
import edu.uw.harmony.UI.model.UserInfoViewModel;
import edu.uw.harmony.UI.settings.SettingsViewModel;
import edu.uw.harmony.databinding.FragmentContactListBinding;
import edu.uw.harmony.databinding.FragmentContactRequestListBinding;

/**
 * This is a Fragment of a list of contact card instances.
 *
 * @author Jack Lin
 * @version 1.1
 */

public class ContactRequestListFragment extends Fragment {

    /** Instantiate view models to be used*/
    private ContactRequestViewModel mModel;

    /** view model instance for the settings class */
    private UserInfoViewModel mUserModel;

    private FragmentContactRequestListBinding binding;
    private long mLastClickTime = 0;
    private boolean hasClickedNewContact = false;


    /** binding variable that allows interaction with views */
    SettingsViewModel settingsViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingsViewModel = new ViewModelProvider(getActivity()).get(SettingsViewModel.class);
        mUserModel = new ViewModelProvider(getActivity()).get(UserInfoViewModel.class);
        mModel = new ViewModelProvider(getActivity()).get(ContactRequestViewModel.class);
        mModel.connectGet(mUserModel.getJwt());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContactRequestListBinding.inflate(inflater);
        return binding.getRoot();
    }

    /**
     * Override onViewCreated, add a observer to contact list and update it based on changes
     * @param view View
     * @param savedInstanceState Bundle
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentContactRequestListBinding binding = FragmentContactRequestListBinding.bind(getView());
        mModel.addContactListObserver(getViewLifecycleOwner(), contactList -> {
            binding.listRoot.setAdapter(
                    new ContactRequestRecyclerViewAdapter(contactList,mModel, mUserModel, settingsViewModel));
        });
    }

    /**
     * Override onResume, connect to endpoint and refresh contact list upon resuming.
     */
    @Override
    public void onResume() {
        super.onResume();
        mModel.connectGet(mUserModel.getJwt());
    }
}