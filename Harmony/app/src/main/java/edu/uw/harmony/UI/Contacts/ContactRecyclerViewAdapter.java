package edu.uw.harmony.UI.Contacts;

import android.graphics.drawable.Icon;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

import edu.uw.harmony.R;
import edu.uw.harmony.UI.Chat.ChatRecyclerViewAdapter;
import edu.uw.harmony.databinding.FragmentContactCardBinding;


public class ContactRecyclerViewAdapter extends RecyclerView.Adapter<ContactRecyclerViewAdapter.ContactViewHolder>{
    private final List<ContactCard> mContact;

    //Store the expanded state for each List item, true -> expanded, false -> not
    private final Map<ContactCard, Boolean> mExpandedFlags;

    public ContactRecyclerViewAdapter(List<ContactCard> items) {
        this.mContact= items;
        mExpandedFlags = mContact.stream().collect(Collectors.toMap(Function.identity(), contacts -> false));
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContactViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_contact_card, parent, false));
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
        public int[] images = {R.drawable.contact_boy_512, R.drawable.contact_hacker_512,R.drawable.contact_barista_512,
                R.drawable.contact_kitty_512,R.drawable.contact_man_512,R.drawable.contact_man_1_512,
                R.drawable.contact_man_2_512,R.drawable.contact_user_512,R.drawable.contact_woman_512,
                R.drawable.contact_woman_1_512};
        Random rand = new Random();

        public ContactViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentContactCardBinding.bind(view);
            //binding.contactMessage.setOnClickListener(button ->  Navigation.findNavController(getView())
            //       .navigate(ContactCardFragmentDirections.actionContactCardFragmentToNavigationNewChat()));
            //binding.buttonMore.setOnClickListener(this::handleMoreOrLess);
        }

        /**
         * When the button is clicked in the more state, expand the card to display
         * the blog preview and switch the icon to the less state.  When the button
         * is clicked in the less state, shrink the card and switch the icon to the
         * more state.
         * @param button the button that was clicked*/

        private void displayCard(final View button) {
            //mExpandedFlags.put(mContact, !mExpandedFlags.get(mContact));
            displayPreview();
        }

        /**
         * Helper used to determine if the preview should be displayed or not. */

        private void displayPreview() {
            /*if (mExpandedFlags.get(mContact)) {
                binding.textPreview.setVisibility(View.VISIBLE);
            } else {
                binding.textPreview.setVisibility(View.GONE);
            }*/
        }


        void setContact(final ContactCard contact) {
            mContact = contact;

            binding.contactUsername.setText(contact.getUsername());
            binding.contactStatus.setText(contact.getStatus());
            binding.contactAvatar.setImageResource(images[rand.nextInt(images.length)]);

            binding.contactCard.setOnClickListener(button ->
                    Navigation.findNavController(mView).navigate(
                            ContactListFragmentDirections.actionNavigationContactToContactFragment(binding.contactUsername.getText().toString(),  binding.contactStatus.getText().toString())));

            binding.contactMessage.setOnClickListener(button ->
                    Navigation.findNavController(mView).navigate(
                            ContactListFragmentDirections.actionNavigationContactToNavigationNewChat()));

        }

    }

}
