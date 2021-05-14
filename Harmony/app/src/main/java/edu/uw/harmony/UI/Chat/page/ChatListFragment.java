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
import edu.uw.harmony.databinding.FragmentChatListBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatListFragment extends Fragment {
    private ChatListViewModel mModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = new ViewModelProvider(getActivity()).get(ChatListViewModel.class);
        mModel.connectGet();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_list, container, false);
//        if (view instanceof RecyclerView) {
//            ((RecyclerView) view).setAdapter(
//                    new ChatRecyclerViewAdapter(ChatGenerator.getBlogList()));
//        }
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
}