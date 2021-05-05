package edu.uw.harmony.UI.Chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.harmony.R;
import edu.uw.harmony.databinding.FragmentChatBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("testing", "Im testing");
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Local access to the ViewBinding object. No need to create as Instance Var as it is only
        //used here.
        FragmentChatBinding binding = FragmentChatBinding.bind(getView());
        binding.newChat.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(
                        ChatsFragmentDirections.actionNavigationChatsToNewChatFragment()));
    }
}