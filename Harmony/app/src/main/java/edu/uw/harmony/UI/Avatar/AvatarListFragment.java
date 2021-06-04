package edu.uw.harmony.UI.Avatar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import edu.uw.harmony.R;
import edu.uw.harmony.UI.Auth.Register.RegisterFragmentDirections;
import edu.uw.harmony.databinding.FragmentAvatarListBinding;


public class AvatarListFragment extends Fragment {
    private FragmentAvatarListBinding binding;
    private AvatarViewModel aModel;

    private List<Avatar> mAvatarList;

    public ArrayList<ImageView> imageViews;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aModel = new ViewModelProvider(getActivity()).get(AvatarViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAvatarListBinding.inflate(inflater);
        binding.currentAvatar.setImageResource(R.drawable.contact_boy_512);
        binding.avatarID.setText(String.valueOf(2131230854));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        FragmentAvatarListBinding binding = FragmentAvatarListBinding.bind(getView());

        aModel.addAvatarListObserver(getViewLifecycleOwner(), avatarList -> {
            binding.recyclerView.setAdapter(new AvatarAdapter(AvatarGenerator.getAvatarList(), aModel, view));
        });


        binding.setAvatar.setOnClickListener(button -> {
            FragmentAvatarListBinding mbinding = FragmentAvatarListBinding.bind(getView());
            AvatarListFragmentDirections.ActionAvatarListFragmentToRegisterFragment directions =
            AvatarListFragmentDirections.actionAvatarListFragmentToRegisterFragment(Integer.parseInt(mbinding.avatarID.getText().toString()));

            directions.setAvatar(Integer.parseInt(mbinding.avatarID.getText().toString()));
            Navigation.findNavController(getView()).navigate(directions);
        });
    }

}