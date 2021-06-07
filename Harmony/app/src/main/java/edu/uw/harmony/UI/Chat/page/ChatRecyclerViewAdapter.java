package edu.uw.harmony.UI.Chat.page;

import android.graphics.Color;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import edu.uw.harmony.R;
import edu.uw.harmony.UI.Chat.page.ChatListFragmentDirections;
import edu.uw.harmony.UI.Chat.page.ChatPost;
import edu.uw.harmony.UI.model.NewMessageCountViewModel;
import edu.uw.harmony.UI.settings.SettingsViewModel;
import edu.uw.harmony.databinding.FragmentChatCardBinding;

import static android.view.View.VISIBLE;

/**
 * Creates a Recycler View Adapter for the Chat rooms
 */
public class ChatRecyclerViewAdapter extends RecyclerView.Adapter<ChatRecyclerViewAdapter.ChatViewHolder>{
    /** Stores all of the rooms*/
    private final List<ChatPost> mChats;

    private SettingsViewModel sModel;

    private NewMessageCountViewModel mCountModel;

    /**
     * Constructor for the Recycler View Adapter
     * @param items the items that will be created
     */
    public ChatRecyclerViewAdapter(List<ChatPost> items, SettingsViewModel model, NewMessageCountViewModel countModel) {
        this.mChats = items;
        this.sModel = model;
        this.mCountModel = countModel;
    }

    /**
     * Objects from this class represent an Individual row View from the List
     * of rows in the Blog Recycler View.
     */
    public class ChatViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentChatCardBinding binding;
        private ChatPost mChat;
        private NewMessageCountViewModel mModel;
        private List<Integer> newMessages;

        public ChatViewHolder(View view, NewMessageCountViewModel model) {
            super(view);
            mView = view;
            binding = FragmentChatCardBinding.bind(view);
            mModel = model;
            List<Integer> newMessages = mModel.getRoomsWithMessages();
        }

        /**
         * Sets the chat on the page
         * @param chat the chat post
         */
        void setChat(final ChatPost chat) {
            if(mModel.getRoomsWithMessages().contains(chat.getChatId())){
                binding.contactNewChatAdded2.setVisibility(VISIBLE);
            } else {
                binding.contactNewChatAdded2.setVisibility(View.GONE);
            }
            mChat = chat;
            Log.e("AVATAR", mChat.getAvatar()+"");
            binding.imageFace.setImageResource((mChat.getAvatar()));
            binding.cardRoot.setOnClickListener(view ->  navToChat());
            binding.textRecentMessage.setOnClickListener(view -> navToChat());
            binding.contactNewChatAdded2.setOnClickListener(view -> navToChat());
            binding.textTitle.setText(chat.getTitle());
            binding.textRecentMessage.setText(chat.getRecentMessage());

            if(sModel.getCurrentThemeID() == R.style.Theme_1_Harmony){
                binding.cardRoot.setCardBackgroundColor(binding.getRoot().getResources().getColor(R.color.offwhite));
//                binding.imageFace.setColorFilter(binding.getRoot().getResources().getColor(R.color.tan));
                binding.textTitle.setBackgroundColor(binding.getRoot().getResources().getColor(R.color.offwhite));
                binding.textTitle.setTextColor(binding.getRoot().getResources().getColor(R.color.black));
                binding.textRecentMessage.setBackgroundColor(binding.getRoot().getResources().getColor(R.color.offwhite));
                binding.textRecentMessage.setTextColor(Color.BLACK);
            } else {
                binding.cardRoot.setCardBackgroundColor(Color.BLACK);
//                binding.imageFace.setColorFilter(Color.WHITE);
                binding.textRecentMessage.setBackgroundColor(Color.BLACK);
                binding.textRecentMessage.setTextColor(binding.getRoot().getResources().getColor(R.color.teal_200));
                binding.textTitle.setBackgroundColor(Color.BLACK);
                binding.textTitle.setTextColor(binding.getRoot().getResources().getColor(R.color.teal_200));
            }
        }
        private void navToChat() {
            Navigation.findNavController(mView).navigate(
                    ChatListFragmentDirections.actionChatListFragmentToChatPostFragment(mChat.getChatId())
            );
        }
    }

    @Override
    public int getItemCount() {
        return mChats.size();
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_chat_card, parent, false), this.mCountModel);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        holder.setChat(mChats.get(position));
    }
}