package edu.uw.harmony.UI.Chat;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import edu.uw.harmony.R;
import edu.uw.harmony.UI.Weather.HourlyForecastItemGenerator;
import edu.uw.harmony.UI.Weather.HourlyForecastRecyclerViewAdapter;
import edu.uw.harmony.UI.settings.SettingsViewModel;
import edu.uw.harmony.databinding.FragmentChatBinding;
import edu.uw.harmony.databinding.FragmentChatListBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatListFragment extends Fragment {
    private ChatListViewModel mModel;

    /** ViewModel for settings */
    private SettingsViewModel settingsViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = new ViewModelProvider(getActivity()).get(ChatListViewModel.class);
        settingsViewModel = new ViewModelProvider(getActivity()).get(SettingsViewModel.class);
        mModel.connectGet();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_list, container, false);
        if (view instanceof RecyclerView) {
            ((RecyclerView) view).setAdapter(
                    new ChatRecyclerViewAdapter(ChatGenerator.getBlogList()));
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentChatListBinding binding = FragmentChatListBinding.bind(getView());

        if(settingsViewModel.getCurrentThemeID() == R.style.Theme_1_Harmony){
            binding.buttonNewChat.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.orange)));
            binding.buttonNewChat.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.tan)));
        } else {
            binding.buttonNewChat.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.teal_200)));
            binding.buttonNewChat.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
        }

        mModel.addBlogListObserver(getViewLifecycleOwner(), blogList -> {
            Log.d("string", ""+blogList.size());
            if (!blogList.isEmpty()) {
                Log.d("string", "not empty");
                binding.listRoot.setAdapter( new ChatRecyclerViewAdapter(blogList)
                );
            }
        });

        binding.buttonNewChat.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(
                        ChatListFragmentDirections.actionNavigationChatListToNavigationNewChat()));
    }
}