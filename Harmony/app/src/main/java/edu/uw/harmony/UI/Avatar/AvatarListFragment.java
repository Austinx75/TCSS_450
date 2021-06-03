package edu.uw.harmony.UI.Avatar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.uw.harmony.R;
import edu.uw.harmony.databinding.FragmentAvatarListBinding;


public class AvatarListFragment extends Fragment {
    private FragmentAvatarListBinding binding;
    private AvatarViewModel aModel;

    private ArrayList<Avatar> mAvatarList;

    public int[] images = {R.drawable.contact_boy_512, R.drawable.contact_hacker_512,R.drawable.contact_barista_512,
            R.drawable.contact_kitty_512,R.drawable.contact_man_512,R.drawable.contact_man_1_512,
            R.drawable.contact_man_2_512,R.drawable.contact_user_512,R.drawable.contact_woman_512,
            R.drawable.contact_woman_1_512};



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aModel = new ViewModelProvider(getActivity()).get(AvatarViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAvatarListBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        FragmentAvatarListBinding binding = FragmentAvatarListBinding.bind(getView());
        aModel.addAvatarListObserver(getViewLifecycleOwner(), avatarList -> {
            binding.recyclerView.setAdapter(new AvatarAdapter(avatarList, aModel));
        });
    }
}