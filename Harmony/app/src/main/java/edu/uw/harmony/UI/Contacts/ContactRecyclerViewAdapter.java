package edu.uw.harmony.UI.Contacts;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

import edu.uw.harmony.R;
import edu.uw.harmony.UI.Auth.Register.RegisterFragmentDirections;
import edu.uw.harmony.UI.Chat.NewChat.NewChatFragmentDirections;
import edu.uw.harmony.UI.model.UserInfoViewModel;
import edu.uw.harmony.UI.settings.SettingsViewModel;
import edu.uw.harmony.databinding.FragmentContactCardBinding;

/**
 * This is a Recycler Adapter that creates the ContactListFragment.
 *
 * @author Jack Lin
 * @version 1.0
 */

public class ContactRecyclerViewAdapter extends RecyclerView.Adapter<ContactRecyclerViewAdapter.ContactViewHolder>{
    private final List<ContactCard> mContact;
    //Store the expanded state for each List item, true -> expanded, false -> not
    private final Map<ContactCard, Boolean> mExpandedFlags;
    ContactListViewModel mModel;
    UserInfoViewModel uModel;
    SettingsViewModel sModel;
    boolean newChat;
    List<String> selected;
    List<String> autofill;

    public ContactRecyclerViewAdapter(List<ContactCard> items, ContactListViewModel mModel, UserInfoViewModel uModel, SettingsViewModel model, boolean newChat, List<String> selected, List<String> autoFill) {
        this.mContact= items;
        this.mModel = mModel;
        this.uModel = uModel;
        this.sModel = model;
        this.newChat = newChat;
        this.selected = selected;
        this.autofill = autoFill;
        mExpandedFlags = mContact.stream().collect(Collectors.toMap(Function.identity(), contacts -> false));
    }
    public ContactRecyclerViewAdapter(List<ContactCard> items, ContactListViewModel mModel, UserInfoViewModel uModel, SettingsViewModel model) {
        this.mContact= items;
        this.mModel = mModel;
        this.uModel = uModel;
        this.sModel = model;
        this.newChat = false;
        this.selected = new ArrayList<String>();
        this.autofill= new ArrayList<String>();
        mExpandedFlags = mContact.stream().collect(Collectors.toMap(Function.identity(), contacts -> false));
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContactViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_contact_card, parent, false), mModel, uModel, this.selected, this.autofill);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.setContact(mContact.get(position));
    }

    @Override
    public int getItemCount() {
        return mContact.size();
    }

    /**
     * Objects from this class represent an Individual row View from the List* of rows in the Blog Recycler View.
     */
    public class ContactViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentContactCardBinding binding;
        private ContactCard mContact;
        ContactListViewModel contactListViewModel;
        UserInfoViewModel userInfoViewModel;
        List<String> selected;
        List<String> autofill;

        public int[] images = {R.drawable.contact_boy_512, R.drawable.contact_hacker_512,R.drawable.contact_barista_512,
                R.drawable.contact_kitty_512,R.drawable.contact_man_512,R.drawable.contact_man_1_512,
                R.drawable.contact_man_2_512,R.drawable.contact_user_512,R.drawable.contact_woman_512,
                R.drawable.contact_woman_1_512};
        Random rand = new Random();

        public ContactViewHolder(View view, ContactListViewModel mModel, UserInfoViewModel uModel, List<String> selected, List<String> autofill) {
            super(view);
            mView = view;
            contactListViewModel = mModel;
            userInfoViewModel = uModel;
            this.selected = selected;
            this.autofill=autofill;

            binding = FragmentContactCardBinding.bind(view);
            binding.contactNewChatAdded.setVisibility(View.GONE);
            if (!newChat) {
                binding.contactCard.setOnClickListener(this::handleMoreOrLess);
                binding.contactDelete.setOnClickListener(button -> {mModel.contactDelete(uModel.getJwt(), Integer.parseInt(mContact.getId()));});
                binding.contactMessage.setOnClickListener(button -> {
                    Log.d("ID", (mContact.getId()));
                    ContactContainerFragmentDirections.ActionNavigationContactContainerToNavigationNewChat2 directions
                            = ContactContainerFragmentDirections.actionNavigationContactContainerToNavigationNewChat2();
                    directions.setEmail(binding.contactUsername.getText().toString());

                    Navigation.findNavController(mView).navigate(directions);
                });
                binding.contactNewChatAdded.setVisibility(View.GONE);
            } else{
                binding.contactCard.setOnClickListener(button -> {
                    if (binding.contactNewChatAdded.getVisibility() == View.VISIBLE) {
                        binding.contactNewChatAdded.setVisibility(View.GONE);
                        this.selected.remove(binding.contactUsername.getText().toString());
                    }else{
                        binding.contactNewChatAdded.setVisibility(View.VISIBLE);
                        this.selected.add(binding.contactUsername.getText().toString());
                    }
                });
                binding.contactNewChatAdded.setOnClickListener(button -> {
                    if (binding.contactNewChatAdded.getVisibility() == View.VISIBLE) {
                        binding.contactNewChatAdded.setVisibility(View.GONE);
                        this.selected.remove(binding.contactUsername.getText().toString());
                    }else{
                        binding.contactNewChatAdded.setVisibility(View.VISIBLE);
                        this.selected.add(binding.contactUsername.getText().toString());
                    }
                });
                binding.contactDelete.setVisibility(View.GONE);
                binding.contactMessage.setVisibility(View.GONE);
            }
        }

        /**
         * When the button is clicked in the more state, expand the card to display
         * the blog preview and switch the icon to the less state.  When the button
         * is clicked in the less state, shrink the card and switch the icon to the
         * more state.
         * @param button the button that was clicked*/

        private void handleMoreOrLess(final View button) {
            mExpandedFlags.put(mContact, !mExpandedFlags.get(mContact));
            displayPreview();
        }

        /**
         * Helper used to determine if the preview should be displayed or not. */

        private void displayPreview() {
            if (mExpandedFlags.get(mContact)) {
                binding.textPreview.setVisibility(View.VISIBLE);
            } else {
                binding.textPreview.setVisibility(View.GONE);
            }
        }

        /**
         * Method to fill each contact card with information
         * @param contact ContactCard Object
         */
        void setContact(final ContactCard contact) {
            if (newChat && this.autofill.contains(contact.getUsername())) {
                binding.contactNewChatAdded.setVisibility(View.VISIBLE);
            }
            mContact = contact;

            binding.contactUsername.setText(contact.getUsername());
            binding.contactStatus.setText(contact.getStatus());
            binding.contactAvatar.setImageResource(images[rand.nextInt(images.length)]);

            String name = contact.getName();
            String user = contact.getUsername();
            String id = contact.getId();

            String preview = "\r\n" + "Full Name: "+name + "\r\n" +
                    "Username: "+user + "\r\n" +
                    "ID: "+id + "\r\n";
            binding.textPreview.setText(preview);
            if(sModel.getCurrentThemeID() == R.style.Theme_1_Harmony){
                binding.cardRoot.setCardBackgroundColor(binding.getRoot().getResources().getColor(R.color.offwhite));
                binding.contactUsername.setTextColor(Color.BLACK);
                binding.contactStatus.setTextColor(Color.BLACK);
                binding.contactMessage.setColorFilter(binding.getRoot().getResources().getColor(R.color.tan));
                binding.contactDelete.setColorFilter(binding.getRoot().getResources().getColor(R.color.tan));
                binding.contactNewChatAdded.setColorFilter(binding.getRoot().getResources().getColor(R.color.tan));
                binding.contactStatus.setTextColor(Color.BLACK);
                binding.textPreview.setTextColor(Color.BLACK);
            } else {
                binding.cardRoot.setCardBackgroundColor(binding.getRoot().getResources().getColor(R.color.black));
                binding.contactUsername.setTextColor(binding.getRoot().getResources().getColor(R.color.teal_200));
                binding.contactStatus.setTextColor(binding.getRoot().getResources().getColor(R.color.teal_200));
                binding.contactMessage.setColorFilter(binding.getRoot().getResources().getColor(R.color.white));
                binding.contactDelete.setColorFilter(binding.getRoot().getResources().getColor(R.color.white));
                binding.contactNewChatAdded.setColorFilter(binding.getRoot().getResources().getColor(R.color.white));
                binding.contactStatus.setTextColor(binding.getRoot().getResources().getColor(R.color.teal_200));
                binding.textPreview.setTextColor(binding.getRoot().getResources().getColor(R.color.teal_200));
            }
            displayPreview();

        }

    }

}
