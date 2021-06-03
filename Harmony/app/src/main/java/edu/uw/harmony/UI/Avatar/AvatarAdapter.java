package edu.uw.harmony.UI.Avatar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.uw.harmony.R;
import edu.uw.harmony.UI.Contacts.ContactRecyclerViewAdapter;
import edu.uw.harmony.databinding.FragmentAvatarBinding;
import edu.uw.harmony.databinding.FragmentAvatarListBinding;

public class AvatarAdapter extends RecyclerView.Adapter<AvatarAdapter.AvatarViewHolder> {
    private List<Avatar> mAvatarList;
    AvatarViewModel avatarViewModel;

    public static class AvatarViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public final View mView;
        public FragmentAvatarBinding binding;
        AvatarViewModel avatarViewModel;

        public AvatarViewHolder(@NonNull View view, AvatarViewModel aModel) {
            super(view);
            mView = view;
            avatarViewModel = aModel;
            mImageView = view.findViewById(R.id.imageView);

            binding = FragmentAvatarBinding.bind(view);
        }
    }

    public AvatarAdapter(List<Avatar> avatarList, AvatarViewModel aModel){
        mAvatarList = avatarList;
        avatarViewModel = aModel;
    }

    @NonNull
    @Override
    public AvatarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AvatarAdapter.AvatarViewHolder(LayoutInflater
                .from(parent.getContext()).inflate(R.layout.fragment_avatar, parent, false), avatarViewModel);
    }

    @Override
    public void onBindViewHolder(@NonNull AvatarViewHolder holder, int position) {
        Avatar avatar = mAvatarList.get(position);
        holder.mImageView.setImageResource(avatar.getImageSource());
    }

    @Override
    public int getItemCount() {
        return mAvatarList.size();
    }
}
