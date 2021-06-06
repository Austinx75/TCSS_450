package edu.uw.harmony.UI.settings;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.List;

import edu.uw.harmony.R;
import edu.uw.harmony.UI.Avatar.Avatar;
import edu.uw.harmony.UI.Avatar.AvatarAdapter;
import edu.uw.harmony.UI.Avatar.AvatarGenerator;
import edu.uw.harmony.UI.model.UserInfoViewModel;
import edu.uw.harmony.UI.settings.AvatarListFragmentDirections;
import edu.uw.harmony.UI.Avatar.AvatarViewModel;
import edu.uw.harmony.databinding.FragmentAvatarList2Binding;

/**
 * This is a Fragment of a list of avatar instances.
 *
 * @author Jack Lin
 * @version 1.1
 */
public class AvatarListFragment extends Fragment {
    /**
     * binding variable that allows interaction with views
     */
    private FragmentAvatarList2Binding binding;
    /**
     * Instantiate view models to be used
     */
    private AvatarViewModel aModel;
    private UserInfoViewModel uModel;

    private List<Avatar> mAvatarList;

    public ArrayList<ImageView> imageViews;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aModel = new ViewModelProvider(getActivity()).get(AvatarViewModel.class);
        uModel = new ViewModelProvider(getActivity()).get(UserInfoViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAvatarList2Binding.inflate(inflater);
        binding.currentAvatar.setImageResource(R.drawable.contact_boy_512);
        binding.avatarID.setText(String.valueOf(2131230854));
        return binding.getRoot();
    }

    /**
     * Override onViewCreated, add a observer to contact list and update it based on changes
     *
     * @param view               View
     * @param savedInstanceState Bundle
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentAvatarList2Binding binding = FragmentAvatarList2Binding.bind(getView());

        aModel.addAvatarListObserver(getViewLifecycleOwner(), avatarList -> {
            AvatarAdapter aa = new AvatarAdapter(AvatarGenerator.getAvatarList(), aModel, view);
            binding.recyclerView.setAdapter(aa);

            binding.setAvatar.setOnClickListener(button -> {
                Navigation.findNavController(getView()).navigate(AvatarListFragmentDirections.actionAvatarListFragment2ToSettingsFragment());
                aModel.connectAdd(uModel.getJwt(), aa.getCurrentAvatar());

            });
        });


    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
