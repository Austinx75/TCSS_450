package edu.uw.harmony.UI.Contacts;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

import edu.uw.harmony.R;
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

    public ContactRecyclerViewAdapter(List<ContactCard> items, ContactListViewModel mModel, UserInfoViewModel uModel) {
        this.mContact= items;
        this.mModel = mModel;
        this.uModel = uModel;
        mExpandedFlags = mContact.stream().collect(Collectors.toMap(Function.identity(), contacts -> false));
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContactViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_contact_card, parent, false), mModel, uModel);
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

        public int[] images = {R.drawable.contact_boy_512, R.drawable.contact_hacker_512,R.drawable.contact_barista_512,
                R.drawable.contact_kitty_512,R.drawable.contact_man_512,R.drawable.contact_man_1_512,
                R.drawable.contact_man_2_512,R.drawable.contact_user_512,R.drawable.contact_woman_512,
                R.drawable.contact_woman_1_512};
        Random rand = new Random();

        public ContactViewHolder(View view, ContactListViewModel mModel, UserInfoViewModel uModel) {
            super(view);
            mView = view;
            contactListViewModel = mModel;
            userInfoViewModel = uModel;

            binding = FragmentContactCardBinding.bind(view);
            binding.contactCard.setOnClickListener(this::handleMoreOrLess);
            binding.contactDelete.setOnClickListener(button -> {mModel.contactDelete(uModel.getJwt(), Integer.parseInt(mContact.getId()));});
            binding.contactMessage.setOnClickListener(button -> {
                Log.d("ID", (mContact.getId()));});
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
            displayPreview();

        }

    }

}
