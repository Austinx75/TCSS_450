package edu.uw.harmony.UI.Avatar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.harmony.R;
import edu.uw.harmony.databinding.FragmentAvatarBinding;
import edu.uw.harmony.databinding.FragmentAvatarListBinding;

/**
 * This is a Recycler Adapter that creates the AvatarListFragment.
 *
 * @author Jack Lin
 * @version 1.0
 */
public class AvatarAdapter extends RecyclerView.Adapter<AvatarAdapter.AvatarViewHolder> {
    private final List<Avatar> mAvatarList;
    private FragmentAvatarListBinding listBinding;
    private View listView;
    public static int currentAvatar;
    AvatarViewModel avatarViewModel;


    /**
     * Constructor that initializes all relevant fields for avatar list fragment
     */
    public AvatarAdapter(List<Avatar> avatarList, AvatarViewModel aModel, View listView){
        this.mAvatarList = avatarList;
        this.avatarViewModel = aModel;
        this.listView = listView;
    }

    @NonNull
    @Override
    public AvatarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AvatarAdapter.AvatarViewHolder(LayoutInflater
                .from(parent.getContext()).inflate(R.layout.fragment_avatar, parent, false), listView, avatarViewModel);
    }

    @Override
    public void onBindViewHolder(@NonNull AvatarViewHolder holder, int position) {
        Avatar avatar = mAvatarList.get(position);
        holder.setAvatar(avatar);
    }

    @Override
    public int getItemCount() {
        return mAvatarList.size();
    }

    public int getCurrentAvatar() {
        return currentAvatar;
    }

    /**
     * Objects from this class represent an Individual row View from the List* of rows in the Avatar Recycler View.
     */
    public static class AvatarViewHolder extends RecyclerView.ViewHolder{
        public FragmentAvatarBinding binding;
        public FragmentAvatarListBinding listBinding;
        AvatarViewModel avatarViewModel;
        private Avatar avatar;


        /**
         * Constructor for the Avatar view holder that initializes all needed fields
         */
        public AvatarViewHolder(@NonNull View view, @NonNull View listView, AvatarViewModel aModel) {
            super(view);
            avatarViewModel = aModel;

            binding = FragmentAvatarBinding.bind(view);
            listBinding = FragmentAvatarListBinding.bind(listView);

        }

        public void setAvatar(final Avatar av) {
            this.avatar = av;
            binding.imageView.setImageResource(avatar.getImageSource());
            binding.avatarID.setText(avatar.getImageSource());

            binding.imageView.setOnClickListener(button -> {
                listBinding.currentAvatar.setImageResource(avatar.getImageSource());
                listBinding.avatarID.setText(String.valueOf(avatar.getImageSource()));
                AvatarAdapter.currentAvatar = avatar.getImageSource();
            });

        }

    }
}
