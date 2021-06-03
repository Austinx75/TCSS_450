package edu.uw.harmony.UI.Contacts;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

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
    private ContactListViewModel mModel;
    private SettingsViewModel sModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserViewModel = new ViewModelProvider(getActivity()).get(UserInfoViewModel.class);
        mModel = new ViewModelProvider(getActivity()).get(ContactListViewModel.class);
        sModel = new ViewModelProvider(getActivity()).get(SettingsViewModel.class);
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
        binding.searchNewContact.setOnClickListener(button -> {
            if (!binding.enterName.getText().toString().equals("")) {
                mModel.contactAdd(mUserViewModel.getJwt(), Integer.parseInt(binding.enterName.getText().toString()));

                    binding.enterName.setText("");
                    binding.contactErrorMessage.setText(R.string.contact_add_sent);

            } else {
                binding.contactErrorMessage.setText(R.string.contact_add_non_exist);
            }
            binding.contactErrorMessage.setVisibility(View.VISIBLE);
        });
    }
}