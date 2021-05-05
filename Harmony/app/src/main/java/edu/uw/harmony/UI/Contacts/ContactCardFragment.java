package edu.uw.harmony.UI.Contacts;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.View;

import java.util.Random;

import edu.uw.harmony.R;
import edu.uw.harmony.databinding.FragmentContactCardBinding;

public class ContactCardFragment extends Fragment {
    public static int[] images = {R.drawable.contact_boy_512, R.drawable.contact_hacker_512,R.drawable.contact_barista_512,
            R.drawable.contact_kitty_512,R.drawable.contact_man_512,R.drawable.contact_man_1_512,
            R.drawable.contact_man_2_512,R.drawable.contact_user_512,R.drawable.contact_woman_512,
            R.drawable.contact_woman_1_512};
    static Random rand = new Random();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ContactCardFragmentArgs args = ContactCardFragmentArgs.fromBundle(getArguments());
        FragmentContactCardBinding binding = FragmentContactCardBinding.bind(getView());
        binding.contactUsername.setText(args.getContact().getUsername());
        binding.contactStatus.setText(args.getContact().getStatus());
        binding.contactAvatar.setImageResource(images[rand.nextInt(images.length)]);
    }
}