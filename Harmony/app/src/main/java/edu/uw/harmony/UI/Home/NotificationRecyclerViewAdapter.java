package edu.uw.harmony.UI.Home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.harmony.R;
import edu.uw.harmony.databinding.FragmentNotificationCardBinding;


public class NotificationRecyclerViewAdapter extends
        RecyclerView.Adapter<NotificationRecyclerViewAdapter.NotificationViewHolder>{

    private final List<NotificationItem> mNotifications;

    public NotificationRecyclerViewAdapter(List<NotificationItem> items){
        this.mNotifications = items;
    }

    @Override
    public int getItemCount() {
        return mNotifications.size();
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotificationViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_notification_card, parent, false));
    }

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

        public NotificationViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentNotificationCardBinding.bind(view);
        }

        void setNotifications(final NotificationItem notifications) {
            mNotifications = notifications;
            binding.textToPersonHome.setText(mNotifications.getSender());
            binding.textMessageHome.setText(mNotifications.getMessage());

        }
    }

}

