package edu.uw.harmony.UI.Chat.NewChat;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import edu.uw.harmony.R;
import edu.uw.harmony.UI.Chat.message.ChatRoomFragmentArgs;
import edu.uw.harmony.UI.Chat.page.ChatListFragmentDirections;
import edu.uw.harmony.UI.Contacts.ContactListFragmentDirections;
import edu.uw.harmony.UI.Contacts.ContactListViewModel;
import edu.uw.harmony.UI.Contacts.ContactRecyclerViewAdapter;
import edu.uw.harmony.UI.model.UserInfoViewModel;
import edu.uw.harmony.UI.settings.SettingsViewModel;
import edu.uw.harmony.databinding.FragmentContactListBinding;
import edu.uw.harmony.databinding.FragmentNewChatBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewChatFragment extends Fragment {

    FragmentNewChatBinding binding;
    private ContactListViewModel mContactModel;
    private UserInfoViewModel mUserModel;
    private NewChatViewModel mModel;

    /** ViewModel for settings */
    private SettingsViewModel settingsViewModel;

    private List<String> emails = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = new ViewModelProvider(getActivity()).get(NewChatViewModel.class);
        settingsViewModel = new ViewModelProvider(getActivity()).get(SettingsViewModel.class);
        mUserModel = new ViewModelProvider(getActivity()).get(UserInfoViewModel.class);
        mContactModel = new ViewModelProvider(getActivity()).get(ContactListViewModel.class);
        mContactModel.connectGet(mUserModel.getJwt());
        NewChatFragmentArgs args = NewChatFragmentArgs.fromBundle(getArguments());
        if (!args.getEmail().equals("default")) {
            this.emails.add(args.getEmail());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewChatBinding.inflate(inflater);
        /** Dependent on the theme, this will set all text / image fields to a certain color. */
        if(settingsViewModel.getCurrentThemeID() == R.style.Theme_1_Harmony){
//            binding.toWho.setTextColor(Color.BLACK);
//            binding.enterName.setTextColor(Color.BLACK);
//            binding.enterMessage.setTextColor(Color.BLACK);
//            binding.sendMessage.setBackgroundColor(Color.WHITE);
//            binding.sendMessage.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.orange)));
        } else {
            binding.editTextChatname.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.offwhite, null)));
            binding.editTextChatname.setTextColor(Color.WHITE);
        }

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    /**
     * Override onViewCreated, add a observer to contact list and update it based on changes
     * @param view View
     * @param savedInstanceState Bundle
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<String> selected = new ArrayList<String>();
        binding.button.setOnClickListener(butt -> {
            Log.e("Test", selected.toString());
            this.attemptNewChat(selected);
        });
        binding.layoutWait.setVisibility(View.GONE);
        mContactModel.addContactListObserver(getViewLifecycleOwner(), contactList -> {
            binding.listRoot.setAdapter(
                    new ContactRecyclerViewAdapter(contactList,mContactModel,mUserModel, settingsViewModel, true, selected, this.emails));
            binding.layoutWait.setVisibility(View.GONE);
            Log.e("EMAILS FROM NEW CHAT", this.emails.toString());
        });

        mModel.addResponseObserver(getViewLifecycleOwner(), response ->{
            try {
                Navigation.findNavController(getView()).navigate(
                        NewChatFragmentDirections.actionNavigationNewChatToNavigationChatPost((int) response.get("chatid"))
                );
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

    }

    private void attemptNewChat(List<String> selected) {
        if (selected.size() > 1 && binding.editTextChatname.getText().toString().length() == 0){
            binding.editTextChatname.setError("Please Enter a chat name for group chats");
        } else if (selected.size() == 0) {
            binding.editTextChatname.setError("Please add someone to your chat room");
        }
        if (selected.size() == 1 && binding.editTextChatname.getText().toString().length() > 0){
            attemptConnect(selected, binding.editTextChatname.getText().toString());
        }
        if (selected.size() == 1 && binding.editTextChatname.getText().toString().length() == 0) {
            attemptConnect(selected, selected.get(0));
        }
        if (selected.size() > 1 && binding.editTextChatname.getText().toString().length() > 0){
            attemptConnect(selected, binding.editTextChatname.getText().toString());
        }
    }

    private void attemptConnect(List<String> selected, String chatName) {
        StringBuilder members = new StringBuilder();
        members.append(selected.get(0));
        for (int i = 1; i < selected.size(); i ++ ){
            members.append(" " + selected.get(i));
        }
        mModel.connectPost(mUserModel.getJwt(),mUserModel.getEmail(),members.toString(), chatName);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mModel.done();
    }
}