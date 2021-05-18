package edu.uw.harmony.UI.Chat.page;

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
import edu.uw.harmony.UI.model.UserInfoViewModel;
import edu.uw.harmony.databinding.FragmentChatListBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatListFragment extends Fragment {
    private ChatListViewModel mModel;
    private UserInfoViewModel mUserModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        mModel = new ViewModelProvider(getActivity()).get(ChatListViewModel.class);
        mUserModel = provider.get(UserInfoViewModel.class);

        mModel.connectGet(mUserModel.getJwt(), mUserModel.getEmail());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentChatListBinding binding = FragmentChatListBinding.bind(getView());

        mModel.addBlogListObserver(getViewLifecycleOwner(), blogList -> {
            Log.d("string", ""+blogList.size());
            if (!blogList.isEmpty()) {
                Log.d("string", "not empty");
                binding.listRoot.setAdapter( new ChatRecyclerViewAdapter(blogList)
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