package edu.uw.harmony.UI.Chat.update;

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

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import edu.uw.harmony.R;
import edu.uw.harmony.UI.Chat.NewChat.NewChatFragmentArgs;
import edu.uw.harmony.UI.Chat.NewChat.NewChatFragmentDirections;
import edu.uw.harmony.UI.Chat.NewChat.NewChatViewModel;
import edu.uw.harmony.UI.Contacts.ContactListViewModel;
import edu.uw.harmony.UI.Contacts.ContactRecyclerViewAdapter;
import edu.uw.harmony.UI.model.UserInfoViewModel;
import edu.uw.harmony.UI.settings.SettingsViewModel;
import edu.uw.harmony.databinding.FragmentNewChatBinding;
import edu.uw.harmony.databinding.FragmentUpdateChatBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateChatFragment extends Fragment {

    FragmentUpdateChatBinding binding;
    private ContactListViewModel mContactModel;
    private UserInfoViewModel mUserModel;
    private UpdateChatViewModel mModel;
    private List<String> selected;
    /** ViewModel for settings */
    private SettingsViewModel settingsViewModel;
    private int chatId;
    private StringBuilder updatedMembers;
    private List<String> updated;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.selected = new ArrayList<>();
        mModel = new ViewModelProvider(getActivity()).get(UpdateChatViewModel.class);
        settingsViewModel = new ViewModelProvider(getActivity()).get(SettingsViewModel.class);
        mUserModel = new ViewModelProvider(getActivity()).get(UserInfoViewModel.class);
        mContactModel = new ViewModelProvider(getActivity()).get(ContactListViewModel.class);
        UpdateChatFragmentArgs args = UpdateChatFragmentArgs.fromBundle(getArguments());
        Log.e("CHAT ROOm", args.getChatid() + "");
        mModel.connectGet(mUserModel.getJwt(), mUserModel.getEmail(), args.getChatid());
        chatId = args.getChatid();
        mModel.connectDelete(mUserModel.getJwt(), mUserModel.getEmail(), chatId);
        updatedMembers = new StringBuilder();
        updated = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUpdateChatBinding.inflate(inflater);
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
        binding.layoutWait.setVisibility(View.GONE);
        mContactModel.addContactListObserver(getViewLifecycleOwner(), contactList -> {
            binding.listRoot.setAdapter(
                    new ContactRecyclerViewAdapter(contactList,mContactModel,mUserModel, settingsViewModel, true, updated, this.selected));
            Log.e("Updated members",updated.size() + "");
            binding.layoutWait.setVisibility(View.GONE);
        });
        binding.button.setOnClickListener(button -> {
            Navigation.findNavController(getView()).navigate(UpdateChatFragmentDirections.actionUpdateChatFragmentToNavigationChatPost(chatId));
            Log.e("UPDATED", updated.toString());
        });
        mModel.addResponseObserver(getViewLifecycleOwner(), response ->{
            if (response.has("code")) {
//                this error cannot be fixed by the user changing credentials
                binding.editTextChatname.setError("Something went wrong. Please restart");
            } else {
                mContactModel.connectGet(mUserModel.getJwt());
                try {
                    binding.editTextChatname.setText(response.get("name").toString());
                    JSONArray emails = response.getJSONArray("members");
                    for (int i = 0; i < emails.length(); i++ ){
                        this.selected.add(emails.get(i).toString());
                        updated.add(emails.get(i).toString());
                    }
                    Log.e("EMAILS", this.selected.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        updatedMembers.append(updated.get(0) + " ");
        for (int i = 1; i < updated.size(); i++ ){
            updatedMembers.append(" " +updated.get(i));
        }
        mModel.connectPost(mUserModel.getJwt(),mUserModel.getEmail(), chatId, updatedMembers.toString());
        Log.e("SELECTED ON DESTROY", this.selected.toString());
        this.selected.clear();
        mModel.done();
    }
}