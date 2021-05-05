package edu.uw.harmony.UI.Chat;

import android.graphics.drawable.Icon;
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
            //binding.buittonMore.setOnClickListener(this::handleMoreOrLess);
        }
        /**
         * When the button is clicked in the more state, expand the card to display
         * the blog preview and switch the icon to the less state. When the button
         * is clicked in the less state, shrink the card and switch the icon to the
         * more state.
         * @param button the button that was clicked
         */
        private void handleMoreOrLess(final View button) {
            mExpandedFlags.put(mChat, !mExpandedFlags.get(mChat));
            displayPreview();
        }
        /**
         * Helper used to determine if the preview should be displayed or not.
         */
        private void displayPreview() {
            if (mExpandedFlags.get(mChat)) {
//                binding.textPreview.setVisibility(View.VISIBLE);
//                binding.buittonMore.setImageIcon(
//                        Icon.createWithResource(
//                                mView.getContext(),
//                                R.drawable.ic_less_grey_24dp));
            } else {
//                binding.textPreview.setVisibility(View.GONE);
//                binding.buittonMore.setImageIcon(
//                        Icon.createWithResource(
//                                mView.getContext(),
//                                R.drawable.ic_more_grey_24dp));
            }
        }
        void setChat(final ChatPost chat) {
            mChat = chat;
//            binding.buttonFullPost.setOnClickListener(view -> {
//                Navigation.findNavController(mView).navigate(
//                        ChatListFragmentDirections
//                                .actionNavigationChatsToChatPostFragment(chat));

            binding.textTitle.setOnClickListener(view -> {
                Navigation.findNavController(mView).navigate(
                        ChatListFragmentDirections.actionChatListFragmentToChatPostFragment(chat)
                );
            });

            binding.textPubdate.setOnClickListener(view -> {
                Navigation.findNavController(mView).navigate(
                        ChatListFragmentDirections.actionChatListFragmentToChatPostFragment(chat)
                );
            });

            binding.imageFace.setOnClickListener(view -> {
                Navigation.findNavController(mView).navigate(
                        ChatListFragmentDirections.actionChatListFragmentToChatPostFragment(chat)
                );
            });

            binding.textTitle.setText(chat.getTitle());
            binding.textPubdate.setText(chat.getPubDate());
            //Use methods in the HTML class to format the HTML found in the text
            final String preview = Html.fromHtml(
                    chat.getTeaser(),
                    Html.FROM_HTML_MODE_COMPACT)
                    .toString().substring(0,100) //just a preview of the teaser
                    + "...";
//            binding.textPreview.setText(preview);
            displayPreview();
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

////////////////////////////////////////////////////////////////////////////////////

//package edu.uw.harmony.UI.Chat;
//
//import android.graphics.drawable.Icon;
//import android.text.Html;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.navigation.Navigation;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.List;
//import java.util.Map;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
//import edu.uw.harmony.R;
//import edu.uw.harmony.databinding.FragmentChatCardBinding;
//
//public class ChatRecyclerViewAdapter extends RecyclerView.Adapter<ChatRecyclerViewAdapter.ChatViewHolder>{
//    //Store all of the blogs to present
//    private final List<ChatPost> mChats;
//    //Store the expanded state for each List item, true -> expanded, false -> not
//    private final Map<ChatPost, Boolean> mExpandedFlags;
//
//    public ChatRecyclerViewAdapter(List<ChatPost> items) {
//        this.mChats = items;
//        mExpandedFlags = mChats.stream()
//                .collect(Collectors.toMap(Function.identity(), chat -> false));
//    }
//
//    /**
//     * Objects from this class represent an Individual row View from the List
//     * of rows in the Blog Recycler View.
//     */
//    public class ChatViewHolder extends RecyclerView.ViewHolder {
//        public final View mView;
//        public FragmentChatCardBinding binding;
//        private ChatPost mChat;
//        public ChatViewHolder(View view) {
//            super(view);
//            mView = view;
//            binding = FragmentChatCardBinding.bind(view);
//            binding.buittonMore.setOnClickListener(this::handleMoreOrLess);
//        }
//        /**
//         * When the button is clicked in the more state, expand the card to display
//         * the blog preview and switch the icon to the less state. When the button
//         * is clicked in the less state, shrink the card and switch the icon to the
//         * more state.
//         * @param button the button that was clicked
//         */
//        private void handleMoreOrLess(final View button) {
//            mExpandedFlags.put(mChat, !mExpandedFlags.get(mChat));
//            displayPreview();
//        }
//        /**
//         * Helper used to determine if the preview should be displayed or not.
//         */
//        private void displayPreview() {
//            if (mExpandedFlags.get(mChat)) {
//                binding.textPreview.setVisibility(View.VISIBLE);
//                binding.buittonMore.setImageIcon(
//                        Icon.createWithResource(
//                                mView.getContext(),
//                                R.drawable.ic_less_grey_24dp));
//            } else {
//                binding.textPreview.setVisibility(View.GONE);
//                binding.buittonMore.setImageIcon(
//                        Icon.createWithResource(
//                                mView.getContext(),
//                                R.drawable.ic_more_grey_24dp));
//            }
//        }
//        void setChat(final ChatPost chat) {
//            mChat = chat;
//            binding.buttonFullPost.setOnClickListener(view -> {
////                Navigation.findNavController(mView).navigate(
////                        ChatListFragmentDirections
////                                .actionNavigationChatsToChatPostFragment(chat));
//                //TODO add navigation later step
//            });
//            binding.textTitle.setText(chat.getTitle());
//            binding.textPubdate.setText(chat.getPubDate());
//            //Use methods in the HTML class to format the HTML found in the text
//            final String preview = Html.fromHtml(
//                    chat.getTeaser(),
//                    Html.FROM_HTML_MODE_COMPACT)
//                    .toString().substring(0,100) //just a preview of the teaser
//                    + "...";
//            binding.textPreview.setText(preview);
//            displayPreview();
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return mChats.size();
//    }
//
//    @NonNull
//    @Override
//    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return new ChatViewHolder(LayoutInflater
//                .from(parent.getContext())
//                .inflate(R.layout.fragment_chat_card, parent, false));
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
//        holder.setChat(mChats.get(position));
//    }
//}
