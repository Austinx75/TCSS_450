package edu.uw.harmony.UI.Chat.message;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import edu.uw.harmony.R;
import edu.uw.harmony.UI.model.NewMessageCountViewModel;
import edu.uw.harmony.UI.settings.SettingsViewModel;
import edu.uw.harmony.databinding.FragmentChatRoomBinding;
import edu.uw.harmony.UI.model.UserInfoViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatRoomFragment extends Fragment {

    private ChatViewModel mChatModel;
    private UserInfoViewModel mUserModel;

    private ChatSendViewModel mSendModel;
    private int mChatId= 0;
    private SettingsViewModel settingsViewModel;
    private NewMessageCountViewModel mChatCountViewModel;

    public ChatRoomFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingsViewModel = new ViewModelProvider(getActivity()).get(SettingsViewModel.class);
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        ChatRoomFragmentArgs args = ChatRoomFragmentArgs.fromBundle(getArguments());
        mChatId = args.getChatId();
        mUserModel = provider.get(UserInfoViewModel.class);
        mChatModel = provider.get(ChatViewModel.class);
        mChatModel.getFirstMessages(mChatId, mUserModel.getJwt(), settingsViewModel.getCurrentThemeID() == R.style.Theme_1_Harmony);
        mSendModel = provider.get(ChatSendViewModel.class);
        mChatCountViewModel = provider.get(NewMessageCountViewModel.class);
        mChatCountViewModel.setCurrentChatRoom(args.getChatId());
        mChatCountViewModel.reset();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (settingsViewModel.getCurrentThemeID() == R.style.Theme_1_Harmony){

        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_room, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentChatRoomBinding binding = FragmentChatRoomBinding.bind(getView());
        if ( settingsViewModel.getCurrentThemeID() != R.style.Theme_1_Harmony){
            binding.editMessage.setTextColor(this.getContext().getResources().getColor(R.color.white, null));
        }

        //SetRefreshing shows the internal Swiper view progress bar. Show this until messages load
        binding.swipeContainer.setRefreshing(true);

        final RecyclerView rv = binding.recyclerMessages;
        //Set the Adapter to hold a reference to the list FOR THIS chat ID that the ViewModel
        //holds.
        rv.setAdapter(new ChatMessageRecyclerViewAdapter(
                mChatModel.getMessageListByChatId(mChatId),
                mUserModel.getEmail(),
                settingsViewModel.getCurrentThemeID() == R.style.Theme_1_Harmony));


        //When the user scrolls to the top of the RV, the swiper list will "refresh"
        //The user is out of messages, go out to the service and get more
        binding.swipeContainer.setOnRefreshListener(() -> {
            mChatModel.getNextMessages(mChatId, mUserModel.getJwt());
        });

        mChatModel.addMessageObserver(mChatId, getViewLifecycleOwner(),
                list -> {
                    /*
                     * This solution needs work on the scroll position. As a group,
                     * you will need to come up with some solution to manage the
                     * recyclerview scroll position. You also should consider a
                     * solution for when the keyboard is on the screen.
                     */
                    //inform the RV that the underlying list has (possibly) changed
                    rv.getAdapter().notifyDataSetChanged();
                    rv.scrollToPosition(rv.getAdapter().getItemCount() - 1);
                    binding.swipeContainer.setRefreshing(false);
                });
        binding.buttonSend.setOnClickListener(button -> {
            mSendModel.sendMessage(mChatId,
                    mUserModel.getJwt(),
                    binding.editMessage.getText().toString());
        });
        mSendModel.addResponseObserver(getViewLifecycleOwner(), response ->
                binding.editMessage.setText(""));

        binding.imageButton.setOnClickListener(button -> {
            Navigation.findNavController(getView()).navigate(
                    ChatRoomFragmentDirections.actionNavigationChatPostToUpdateChatFragment(mChatId));
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mChatCountViewModel.setCurrentChatRoom(-1);
    }
}
