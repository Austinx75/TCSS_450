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
import androidx.recyclerview.widget.RecyclerView;

import edu.uw.harmony.R;
import edu.uw.harmony.UI.settings.SettingsViewModel;
import edu.uw.harmony.databinding.FragmentContactBinding;
import edu.uw.harmony.databinding.FragmentContactListBinding;

/**
 * This is a Fragment of a list of contact card instances.
 *
 * @author Jack Lin
 * @version 1.0
 */

public class ContactListFragment extends Fragment {

    private ContactListViewModel mModel;

    SettingsViewModel settingsViewModel;

    private FragmentContactListBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingsViewModel = new ViewModelProvider(getActivity()).get(SettingsViewModel.class);
        mModel = new ViewModelProvider(getActivity()).get(ContactListViewModel.class);
        mModel.connectGet();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContactListBinding.inflate(inflater);
        View contactView = binding.listRoot;
        if (contactView instanceof RecyclerView){
            ((RecyclerView) contactView).setAdapter(new ContactRecyclerViewAdapter(ContactGenerator.getContactList()));
        }
        if(settingsViewModel.getCurrentThemeID() == R.style.Theme_1_Harmony){
            binding.AddNewContact.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.orange)));
            binding.AddNewContact.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.tan)));
        } else {
            binding.AddNewContact.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.teal_200)));
            binding.AddNewContact.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentContactListBinding binding = FragmentContactListBinding.bind(getView());
        binding.layoutWait.setVisibility(View.GONE);
    }
}