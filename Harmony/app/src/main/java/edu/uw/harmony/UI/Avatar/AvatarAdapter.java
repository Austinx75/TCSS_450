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

public class AvatarAdapter extends RecyclerView.Adapter<AvatarAdapter.AvatarViewHolder> {
    private final List<Avatar> mAvatarList;
    private FragmentAvatarListBinding listBinding;
    private View listView;
    AvatarViewModel avatarViewModel;

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

    public static class AvatarViewHolder extends RecyclerView.ViewHolder{
        public FragmentAvatarBinding binding;
        public FragmentAvatarListBinding listBinding;
        AvatarViewModel avatarViewModel;
        private Avatar avatar;

        public AvatarViewHolder(@NonNull View view, @NonNull View listView, AvatarViewModel aModel) {
            super(view);
            avatarViewModel = aModel;

            binding = FragmentAvatarBinding.bind(view);
            listBinding = FragmentAvatarListBinding.bind(listView);

            binding.imageView.setOnClickListener(button -> {
                Log.d("imageId", String.valueOf(binding.imageView.getId()));
            });
        }

        public void setAvatar(final Avatar av) {
            this.avatar = av;
            binding.imageView.setImageResource(avatar.getImageSource());
            binding.avatarID.setText(avatar.getImageSource());
            binding.imageView.setOnClickListener(button -> {
                listBinding.currentAvatar.setImageResource(avatar.getImageSource());
                listBinding.avatarID.setText(String.valueOf(avatar.getImageSource()));
                System.out.println(listBinding.avatarID.getText().toString());
            });

        }

    }
}
