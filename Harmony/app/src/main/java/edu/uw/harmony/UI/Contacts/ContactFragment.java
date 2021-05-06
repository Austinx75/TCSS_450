package edu.uw.harmony.UI.Contacts;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Random;

import edu.uw.harmony.R;
import edu.uw.harmony.UI.Chat.ChatsFragmentDirections;
import edu.uw.harmony.databinding.FragmentChatBinding;
import edu.uw.harmony.databinding.FragmentContactBinding;
import edu.uw.harmony.databinding.FragmentContactCardBinding;


public class ContactFragment extends Fragment {

    public int[] images = {R.drawable.contact_boy_512, R.drawable.contact_hacker_512,R.drawable.contact_barista_512,
            R.drawable.contact_kitty_512,R.drawable.contact_man_512,R.drawable.contact_man_1_512,
            R.drawable.contact_man_2_512,R.drawable.contact_user_512,R.drawable.contact_woman_512,
            R.drawable.contact_woman_1_512};
    Random rand = new Random();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("testing", "Im testing");
        return inflater.inflate(R.layout.fragment_contact, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ContactFragmentArgs args = ContactFragmentArgs.fromBundle(getArguments());
        FragmentContactBinding binding = FragmentContactBinding.bind(getView());
        binding.contactUsername.setText(args.getUsername());
        binding.contactStatus.setText(args.getStatus());
        binding.contactAvatar.setImageResource(images[rand.nextInt(images.length)]);
    }

}