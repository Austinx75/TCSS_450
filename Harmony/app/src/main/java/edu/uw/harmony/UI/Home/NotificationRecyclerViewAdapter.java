package edu.uw.harmony.UI.Home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.harmony.R;
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

    /**
     * Instantiates the list of Notifications
     * @param items
     */
    public NotificationRecyclerViewAdapter(List<NotificationItem> items){
        this.mNotifications = items;
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
        return new NotificationViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_notification_card, parent, false));
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

        /**
         * Constructor that instantiates the view and the binding.
         * @param view
         */
        public NotificationViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentNotificationCardBinding.bind(view);
        }

        /**
         * Sets the sender text and message text in the notification card.
         * @param notifications
         */
        void setNotifications(final NotificationItem notifications) {
            mNotifications = notifications;
            binding.textToPersonHome.setText(mNotifications.getSender());
            binding.textMessageHome.setText(mNotifications.getMessage());

        }
    }

}

