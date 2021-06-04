package edu.uw.harmony.UI.Avatar;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.uw.harmony.R;
import edu.uw.harmony.databinding.FragmentAvatarBinding;

public class AvatarFragment extends Fragment {

    private FragmentAvatarBinding binding;

    public AvatarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAvatarBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@Nullable View view, @NonNull Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        AvatarFragmentArgs args = AvatarFragmentArgs.fromBundle(getArguments());
        FragmentAvatarBinding binding = FragmentAvatarBinding.bind(getView());
        binding.imageView.setImageResource(args.getAvatar().getImageSource());
    }

}