package edu.uw.harmony.UI.Chat.page;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.harmony.R;
import edu.uw.harmony.UI.Chat.page.ChatListFragmentDirections;
import edu.uw.harmony.UI.model.NewMessageCountViewModel;
import edu.uw.harmony.UI.model.UserInfoViewModel;
import edu.uw.harmony.UI.settings.SettingsViewModel;
import edu.uw.harmony.databinding.FragmentChatListBinding;
import edu.uw.harmony.databinding.FragmentLogInBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatListFragment extends Fragment {
    /** Chatlist view model*/
    private ChatListViewModel mModel;
    /** View model for the user*/
    private UserInfoViewModel mUserModel;
    /** View binding*/
    private FragmentChatListBinding binding;
    /** ViewModel for settings */
    private SettingsViewModel settingsViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingsViewModel = new ViewModelProvider(getActivity()).get(SettingsViewModel.class);
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        mModel = new ViewModelProvider(getActivity()).get(ChatListViewModel.class);
        mUserModel = provider.get(UserInfoViewModel.class);

        mModel.connectGet(mUserModel.getJwt(), mUserModel.getEmail());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChatListBinding.inflate(inflater);

        /** Dependent on the theme, this will set all text / image fields to a certain color. */
        if(settingsViewModel.getCurrentThemeID() == R.style.Theme_1_Harmony){
            binding.buttonNewChat.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.orange)));
            binding.buttonNewChat.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.tan)));
        } else {
            binding.buttonNewChat.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.teal_200)));
            binding.buttonNewChat.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentChatListBinding binding = FragmentChatListBinding.bind(getView());

        mModel.addBlogListObserver(getViewLifecycleOwner(), blogList -> {
            Log.d("string", ""+blogList.size());
            if (!blogList.isEmpty()) {
                Log.d("string", "not empty");
                binding.listRoot.setAdapter( new ChatRecyclerViewAdapter(blogList, settingsViewModel)
                );
            }
        });

        binding.buttonNewChat.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(
                        ChatListFragmentDirections.actionNavigationChatListToNavigationNewChat()));
    }
    @Override
    public void onResume() {
        super.onResume();
        mModel.connectGet(mUserModel.getJwt(), mUserModel.getEmail());
    }
}