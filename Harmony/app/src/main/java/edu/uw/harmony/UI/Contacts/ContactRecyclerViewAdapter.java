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
    private final List<ContactCard> mContacts;
    //Store the expanded state for each List item, true -> expanded, false -> not
    private final Map<ContactCard, Boolean> mExpandedFlags;
    private final Map<ContactCard, Boolean> mInChat;
    ContactListViewModel mModel;
    UserInfoViewModel uModel;
    SettingsViewModel sModel;
    boolean newChat;
    List<String> selected;
    List<String> autofill;

    /**
     * Constructor that initializes all relevant fields for contact list fragment
     */
    public ContactRecyclerViewAdapter(List<ContactCard> items, ContactListViewModel mModel, UserInfoViewModel uModel, SettingsViewModel model, boolean newChat, List<String> selected, List<String> autoFill) {
        this.mContacts= items;
        this.mModel = mModel;
        this.uModel = uModel;
        this.sModel = model;
        this.newChat = newChat;
        this.selected = selected;
        this.autofill = autoFill;

        mExpandedFlags = mContacts.stream().collect(Collectors.toMap(Function.identity(), contacts -> false));
        mInChat = mContacts.stream().collect(Collectors.toMap(Function.identity(), contacts -> false));
    }

    /** Constructor for the Contact recycler view adapter that initializes all necessary fields */
    public ContactRecyclerViewAdapter(List<ContactCard> items, ContactListViewModel mModel, UserInfoViewModel uModel, SettingsViewModel model) {
        this.mContacts= items;
        this.mModel = mModel;
        this.uModel = uModel;
        this.sModel = model;
        this.newChat = false;
        this.selected = new ArrayList<String>();
        this.autofill= new ArrayList<String>();
        mExpandedFlags = mContacts.stream().collect(Collectors.toMap(Function.identity(), contacts -> false));
        mInChat = mContacts.stream().collect(Collectors.toMap(Function.identity(), contacts -> false));
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
        holder.setContact(mContacts.get(position));
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
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

        /**
         * Constructor for the Contact view holder that initializes all needed fields
         */
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
                binding.contactDelete.setOnClickListener(button -> {
                    mContacts.remove(mContact);
                    notifyItemRemoved(mContacts.indexOf(mContact));
                    mModel.contactDelete(uModel.getJwt(), Integer.parseInt(mContact.getId()));
                });
                binding.contactMessage.setOnClickListener(button -> {
                    ContactContainerFragmentDirections.ActionNavigationContactContainerToNavigationNewChat2 directions
                            = ContactContainerFragmentDirections.actionNavigationContactContainerToNavigationNewChat2();
                    directions.setEmail(binding.contactUsername.getText().toString());
                    Navigation.findNavController(mView).navigate(directions);
                });
                binding.contactNewChatAdded.setVisibility(View.GONE);
            } else{
                binding.contactCard.setOnClickListener(button -> {
                    if (binding.contactNewChatAdded.getVisibility() == View.VISIBLE) {
                        this.selected.remove(binding.contactUsername.getText().toString());
                    }else{
                        this.selected.add(binding.contactUsername.getText().toString());
                    }
                    handleSelected(button);
                    displaySelected();
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
         * When the card is clicked, it will show a check mark indicating it is selected. We then
         * reflect this in the mInChat map.
         * @param button the button pressed
         */
        private void handleSelected(final View button) {
            mInChat.put(mContact, !mInChat.get(mContact));
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
         * Reflects to the card that the card is currently selected
         */
        private void displaySelected() {
            if (mInChat.get(mContact)) {
                binding.contactNewChatAdded.setVisibility(View.VISIBLE);
            } else {
                binding.contactNewChatAdded.setVisibility(View.GONE);
            }
        }

        /**
         * Method to fill each contact card with information
         * @param contact ContactCard Object
         */
        void setContact(final ContactCard contact) {
            if (newChat && this.autofill.contains(contact.getUsername())) {
                Log.e("AUTOFILL", autofill.toString());
                mInChat.put(contact, true);
                binding.contactNewChatAdded.setVisibility(View.VISIBLE);
            }
            mContact = contact;

            binding.contactUsername.setText(contact.getUsername());
            binding.contactStatus.setText(contact.getStatus());
            binding.contactAvatar.setImageResource(contact.getAvatar());
            Log.d("AvatarID:", String.valueOf(contact.getAvatar()));

            String name = contact.getName();
            String user = contact.getUsername();
            String id = contact.getId();

            String preview = "\r\n" + "Full Name: "+name + "\r\n" +
                    "Username: "+user + "\r\n";
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
            displaySelected();
        }
    }
}
