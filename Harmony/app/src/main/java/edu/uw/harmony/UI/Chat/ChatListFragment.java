package edu.uw.harmony.UI.Chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import edu.uw.harmony.R;
import edu.uw.harmony.UI.Weather.HourlyForecastItemGenerator;
import edu.uw.harmony.UI.Weather.HourlyForecastRecyclerViewAdapter;
import edu.uw.harmony.databinding.FragmentChatBinding;
import edu.uw.harmony.databinding.FragmentChatListBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatListFragment extends Fragment {
    private ChatListViewModel mModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_list, container, false);
        if (view instanceof RecyclerView) {
            //Try out a grid layout to achieve rows AND columns. Adjust the widths of the
            //cards on display
// ((RecyclerView) view).setLayoutManager(new GridLayoutManager(getContext(), 2));
            //Try out horizontal scrolling. Adjust the widths of the card so that it is
            //obvious that there are more cards in either direction. i.e. don't have the cards
            //span the entire witch of the screen. Also, when considering horizontal scroll
            //on recycler view, ensure that thre is other content to fill the screen.
// ((LinearLayoutManager)((RecyclerView) view).getLayoutManager())
// .setOrientation(LinearLayoutManager.HORIZONTAL);
            ((RecyclerView) view).setAdapter(
                    new ChatRecyclerViewAdapter(ChatGenerator.getBlogList()));
        }
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
                //binding.layoutWait.setVisibility(View.GONE);
            }
        });

        binding.buttonNewChat.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(
                        ChatListFragmentDirections.actionNavigationChatListToNavigationNewChat()));

//        binding.buttonNewChat.setOnClickListener(button ->
//                Navigation.findNavController(getView()).navigate(
//                        ChatsFragmentDirections.actionNavigationChatsToNewChatFragment()));
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = new ViewModelProvider(getActivity()).get(ChatListViewModel.class);
        mModel.connectGet();
    }
}