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
import androidx.recyclerview.widget.RecyclerView;

import edu.uw.harmony.R;
import edu.uw.harmony.UI.model.UserInfoViewModel;
import edu.uw.harmony.UI.settings.SettingsViewModel;
import edu.uw.harmony.databinding.FragmentContactBinding;
import edu.uw.harmony.databinding.FragmentContactListBinding;

/**
 * This is a Fragment of a list of contact card instances.
 *
 * @author Jack Lin
 * @version 1.1
 */

public class ContactListFragment extends Fragment {

    /** Instantiate view models to be used*/
    private ContactListViewModel mModel;
    private UserInfoViewModel mUserModel;
    SettingsViewModel settingsViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingsViewModel = new ViewModelProvider(getActivity()).get(SettingsViewModel.class);
        mUserModel = new ViewModelProvider(getActivity()).get(UserInfoViewModel.class);
        mModel = new ViewModelProvider(getActivity()).get(ContactListViewModel.class);
        mModel.connectGet(mUserModel.getJwt());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        edu.uw.harmony.databinding.FragmentContactListBinding binding = FragmentContactListBinding.inflate(inflater);
//        View contactView = binding.listRoot;
//        if (contactView instanceof RecyclerView){
//            ((RecyclerView) contactView).setAdapter(new ContactRecyclerViewAdapter(ContactGenerator.getContactList()));
//        }
        if(settingsViewModel.getCurrentThemeID() == R.style.Theme_1_Harmony){
            binding.AddNewContact.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.orange)));
            binding.AddNewContact.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.tan)));
        } else {
            binding.AddNewContact.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.teal_200)));
            binding.AddNewContact.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
        }
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
        FragmentContactListBinding binding = FragmentContactListBinding.bind(getView());
        binding.layoutWait.setVisibility(View.GONE);
        mModel.addContactListObserver(getViewLifecycleOwner(), contactList -> {
            if (!contactList.isEmpty()) {
                binding.listRoot.setAdapter(
                        new ContactRecyclerViewAdapter(contactList,mModel,mUserModel, settingsViewModel));
                binding.layoutWait.setVisibility(View.GONE);
            }
        });
        binding.AddNewContact.setOnClickListener(button -> Navigation.findNavController(getView()).navigate(ContactListFragmentDirections.actionNavigationContactToAddNewContactFragment()));
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