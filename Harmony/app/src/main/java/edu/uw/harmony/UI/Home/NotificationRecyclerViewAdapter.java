package edu.uw.harmony.UI.Home;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.uw.harmony.R;
import edu.uw.harmony.UI.Chat.page.ChatListFragmentDirections;
import edu.uw.harmony.UI.settings.SettingsViewModel;
import edu.uw.harmony.databinding.FragmentNotificationCardBinding;

/**
 * This is the Recycler View adapter for the notifications.
 * It sets the notifications sent over from the generator.
 * @author Austin Scott
 * @version 1.1
 */
public class NotificationRecyclerViewAdapter extends
        RecyclerView.Adapter<NotificationRecyclerViewAdapter.NotificationViewHolder>{
    /** Cretes a list of Notifications*/
    private final List<NotificationItem> mNotifications;

    SettingsViewModel sModel;

    /**
     * Instantiates the list of Notifications
     * @param items
     */
    public NotificationRecyclerViewAdapter(List<NotificationItem> items, SettingsViewModel model){
        if(items.isEmpty()){
            Log.d("Test", "Notifications is empty");
        }
        this.mNotifications = items;
        this.sModel = model;
    }

    /**
     *
     * @return the size of the list
     */
    @Override
    public int getItemCount() {
        return mNotifications.size();
    }

    /**
     *
     * @param parent
     * @param viewType
     * @return a new ViewHolder
     */
    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotificationViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_notification_card, parent, false), sModel);
    }

    /**
     * Sets the notifications. (setText for every attribute)
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull edu.uw.harmony.UI.Home.NotificationRecyclerViewAdapter.NotificationViewHolder holder, int position) {
        holder.setNotifications(mNotifications.get(position));
    }

    /**
     * Objects from this class represent an Individual row View from the List
     * of rows in the Blog Recycler View.
     */
    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentNotificationCardBinding binding;
        private NotificationItem mNotifications;
        SettingsViewModel settingsViewModel;

        /**
         * Constructor that instantiates the view and the binding.
         * @param view
         */
        public NotificationViewHolder(View view, SettingsViewModel sModel) {
            super(view);
            mView = view;
            binding = FragmentNotificationCardBinding.bind(view);
            settingsViewModel = sModel;
        }

        /**
         * Sets the sender text and message text in the notification card.
         * @param notifications
         */
        void setNotifications(final NotificationItem notifications) {
            mNotifications = notifications;
            Log.d("Message in Set Notifications", mNotifications.getMessage());
            binding.textToPersonHome.setText(mNotifications.getSender());
            binding.textMessageHome.setText(mNotifications.getMessage());
            binding.fragmentTimeNotification.setText(mNotifications.getTime());
            if(settingsViewModel.getCurrentThemeID() == R.style.Theme_1_Harmony){
                binding.cardRoot.setCardBackgroundColor(binding.getRoot().getResources().getColor(R.color.offwhite));
                binding.textTOHome.setTextColor(binding.getRoot().getResources().getColor(R.color.black));
                binding.textToPersonHome.setTextColor(binding.getRoot().getResources().getColor(R.color.black));
                binding.textMessageHome.setTextColor(binding.getRoot().getResources().getColor(R.color.black));
                binding.imageMessageNotificationHome.setColorFilter(binding.getRoot().getResources().getColor(R.color.tan));
                binding.fragmentTimeNotification.setTextColor(Color.BLACK);
            } else {
                binding.cardRoot.setCardBackgroundColor(binding.getRoot().getResources().getColor(R.color.black));
                binding.textTOHome.setTextColor(binding.getRoot().getResources().getColor(R.color.teal_200));
                binding.textToPersonHome.setTextColor(binding.getRoot().getResources().getColor(R.color.teal_200));
                binding.textMessageHome.setTextColor(binding.getRoot().getResources().getColor(R.color.teal_200));
                binding.imageMessageNotificationHome.setColorFilter(Color.WHITE);
                binding.fragmentTimeNotification.setTextColor(binding.getRoot().getResources().getColor(R.color.teal_200));
            }
            binding.cardRoot.setOnClickListener(view -> {
                Navigation.findNavController(mView).navigate(
                        HomeFragmentDirections.actionNavigationHomeToNavigationChatList());

            });

        }
    }

}

