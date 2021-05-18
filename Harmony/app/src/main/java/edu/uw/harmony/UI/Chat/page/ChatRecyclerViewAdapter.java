package edu.uw.harmony.UI.Chat.page;

import android.text.Html;
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
import edu.uw.harmony.databinding.FragmentChatCardBinding;

public class ChatRecyclerViewAdapter extends RecyclerView.Adapter<ChatRecyclerViewAdapter.ChatViewHolder>{
    //Store all of the blogs to present
    private final List<ChatPost> mChats;
    //Store the expanded state for each List item, true -> expanded, false -> not
    private final Map<ChatPost, Boolean> mExpandedFlags;

    public ChatRecyclerViewAdapter(List<ChatPost> items) {
        this.mChats = items;
        mExpandedFlags = mChats.stream()
                .collect(Collectors.toMap(Function.identity(), chat -> false));
    }

    /**
     * Objects from this class represent an Individual row View from the List
     * of rows in the Blog Recycler View.
     */
    public class ChatViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentChatCardBinding binding;
        private ChatPost mChat;
        public ChatViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentChatCardBinding.bind(view);
        }

        void setChat(final ChatPost chat) {
            mChat = chat;

            binding.textTitle.setOnClickListener(view -> {
                Navigation.findNavController(mView).navigate(
                        ChatListFragmentDirections.actionChatListFragmentToChatPostFragment(chat.getChatId())
                );
            });

            binding.textPubdate.setOnClickListener(view -> {
                Navigation.findNavController(mView).navigate(
                        ChatListFragmentDirections.actionChatListFragmentToChatPostFragment(chat.getChatId())
                );
            });

            binding.imageFace.setOnClickListener(view -> {
                Navigation.findNavController(mView).navigate(
                        ChatListFragmentDirections.actionChatListFragmentToChatPostFragment(chat.getChatId())
                );
            });
            binding.textTitle.setText(chat.getTitle());
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
                .inflate(R.layout.fragment_chat_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        holder.setChat(mChats.get(position));
    }
}