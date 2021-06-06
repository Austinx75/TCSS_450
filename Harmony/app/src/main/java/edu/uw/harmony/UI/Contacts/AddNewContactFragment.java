package edu.uw.harmony.UI.Contacts;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.harmony.R;
import edu.uw.harmony.UI.model.UserInfoViewModel;
import edu.uw.harmony.UI.settings.SettingsViewModel;
import edu.uw.harmony.databinding.FragmentAddNewContactBinding;
import edu.uw.harmony.databinding.FragmentContactCardBinding;
import edu.uw.harmony.databinding.FragmentHomeBinding;

/**
 * This is a a fragment for adding new contacts
 *
 * @author Jack Lin
 * @version 1.1
 */
public class AddNewContactFragment extends Fragment {

    private UserInfoViewModel mUserViewModel;
    private AddNewContactViewModel mModel;
    private ContactListViewModel cModel;
    private SettingsViewModel sModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserViewModel = new ViewModelProvider(getActivity()).get(UserInfoViewModel.class);
        mModel = new ViewModelProvider(getActivity()).get(AddNewContactViewModel.class);
        cModel = new ViewModelProvider(getActivity()).get(ContactListViewModel.class);
        sModel = new ViewModelProvider(getActivity()).get(SettingsViewModel.class);
        mModel.connectGet(mUserViewModel.getJwt());
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

        mModel.addNewContactObserver(getViewLifecycleOwner(), contactList -> {
            AddNewContactAdapter ad = new AddNewContactAdapter(contactList, mModel, mUserViewModel, sModel);
            binding.listRoot.setAdapter(ad);
            binding.enterName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    ad.getFilter().filter(s);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        });



        if(sModel.getCurrentThemeID() == R.style.Theme_1_Harmony){
            binding.searchNewContact.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.tan)));
            binding.searchNewContact.setColorFilter(getResources().getColor(R.color.orange));
            binding.enterName.setHintTextColor(Color.BLACK);
            binding.enterName.setTextColor(Color.BLACK);
        } else {
            binding.searchNewContact.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
            binding.searchNewContact.setColorFilter(getResources().getColor(R.color.teal_200));
            binding.enterName.setHintTextColor(Color.WHITE);
            binding.enterName.setTextColor(Color.WHITE);
            //binding.searchNewContact.setBackgroundColor(Color.WHITE);
        }
    }

}