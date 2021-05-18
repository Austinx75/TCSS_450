package edu.uw.harmony.UI.Chat.page;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.harmony.R;
import edu.uw.harmony.UI.settings.SettingsViewModel;
import edu.uw.harmony.databinding.FragmentChatBinding;
import edu.uw.harmony.databinding.FragmentNewChatBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewChatFragment extends Fragment {

    FragmentNewChatBinding binding;

    /** ViewModel for settings */
    private SettingsViewModel settingsViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingsViewModel = new ViewModelProvider(getActivity()).get(SettingsViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewChatBinding.inflate(inflater);
        if(settingsViewModel.getCurrentThemeID() == R.style.Theme_1_Harmony){
            binding.toWho.setTextColor(Color.BLACK);
            binding.enterName.setTextColor(Color.BLACK);
            binding.enterMessage.setTextColor(Color.BLACK);
            binding.sendMessage.setBackgroundColor(Color.WHITE);
            binding.sendMessage.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.orange)));
        } else {
            binding.toWho.setTextColor(Color.WHITE);
            binding.enterName.setTextColor(Color.WHITE);
            binding.enterMessage.setTextColor(Color.WHITE);
            binding.enterMessage.setHintTextColor(Color.WHITE);
            binding.enterName.setHintTextColor(Color.WHITE);
            binding.sendMessage.setBackgroundColor(getResources().getColor(R.color.dark_blue));
            binding.sendMessage.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.teal_200)));
        }

        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}