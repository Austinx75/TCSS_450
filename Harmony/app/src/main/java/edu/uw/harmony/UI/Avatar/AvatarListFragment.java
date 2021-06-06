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

/**
 * This is a Fragment of a list of avatar instances.
 *
 * @author Jack Lin
 * @version 1.1
 */
public class AvatarListFragment extends Fragment {
    /** binding variable that allows interaction with views */
    private FragmentAvatarListBinding binding;
    /** Instantiate view models to be used*/
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

    /**
     * Override onViewCreated, add a observer to contact list and update it based on changes
     * @param view View
     * @param savedInstanceState Bundle
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        FragmentAvatarListBinding binding = FragmentAvatarListBinding.bind(getView());

        aModel.addAvatarListObserver(getViewLifecycleOwner(), avatarList -> {
            AvatarAdapter aa = new AvatarAdapter(AvatarGenerator.getAvatarList(), aModel, view);
            binding.recyclerView.setAdapter(aa);

            binding.currentAvatar.setOnClickListener(button -> {
                Log.d("AvatarID", String.valueOf(aa.getCurrentAvatar()));
            });

            binding.setAvatar.setOnClickListener(button -> {

                AvatarListFragmentDirections.ActionAvatarListFragmentToRegisterFragment directions =
                        AvatarListFragmentDirections.actionAvatarListFragmentToRegisterFragment(aa.getCurrentAvatar());

                directions.setAvatar(aa.getCurrentAvatar());
                Navigation.findNavController(getView()).navigate(directions);
            });
        });


    }

    @Override
    public void onResume(){
        super.onResume();
    }

}