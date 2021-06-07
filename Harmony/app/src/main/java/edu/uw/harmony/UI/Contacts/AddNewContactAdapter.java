package edu.uw.harmony.UI.Contacts;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import edu.uw.harmony.R;
import edu.uw.harmony.UI.model.UserInfoViewModel;
import edu.uw.harmony.UI.settings.SettingsViewModel;
import edu.uw.harmony.databinding.FragmentAddNewContactCardBinding;

/**
 * This is a Recycler Adapter that creates the AddNewContactFragment.
 *
 * @author Jack Lin
 * @version 1.0
 */

public class AddNewContactAdapter extends RecyclerView.Adapter<AddNewContactAdapter.AddNewContactViewHolder> implements Filterable {
    private List<ContactCard> mContacts;
    private List<ContactCard>  mContactsAll;
    //Store the expanded state for each List item, true -> expanded, false -> not
    private final Map<ContactCard, Boolean> mExpandedFlags;
    AddNewContactViewModel rModel;
    UserInfoViewModel uModel;
    SettingsViewModel sModel;

    /**
     * Constructor for contact request recycler view adapter. Initializes necessary fields
     */
    public AddNewContactAdapter(List<ContactCard> items, AddNewContactViewModel rModel, UserInfoViewModel uModel, SettingsViewModel model) {
        this.mContacts= items;
        this.mContactsAll = new ArrayList<>(mContacts);
        this.rModel = rModel;
        this.uModel = uModel;
        this.sModel = model;
        mExpandedFlags = mContacts.stream().collect(Collectors.toMap(Function.identity(), contacts -> false));
    }

    @NonNull
    @Override
    public AddNewContactAdapter.AddNewContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AddNewContactAdapter.AddNewContactViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_add_new_contact_card, parent, false), rModel, uModel);
    }

    @Override
    public void onBindViewHolder(@NonNull AddNewContactAdapter.AddNewContactViewHolder holder, int position) {
        holder.setContact(mContacts.get(position));
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter(){

        // run on background thread
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ContactCard> filterList = new ArrayList<>();
            if (constraint.toString().isEmpty()){
                filterList.addAll(mContactsAll);
            } else {
                for (ContactCard contactCard: mContactsAll){
                    if (contactCard.getUsername().toLowerCase().contains(constraint.toString().toLowerCase())){
                        filterList.add(contactCard);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filterList;
            return filterResults;
        }

        //runs on ui thread
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mContacts.clear();
            mContacts.addAll((Collection<? extends ContactCard>) results.values);
            notifyDataSetChanged();
        }
    };
    /**
     * Objects from this class represent an Individual row View from the List* of rows in the Blog Recycler View.
     */
    public class AddNewContactViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentAddNewContactCardBinding binding;
        private ContactCard mContact;
        AddNewContactAdapter mAdapter;
        AddNewContactViewModel addNewContactViewModel;
        UserInfoViewModel userInfoViewModel;

        /**
         * Public constructor for the contact view holder. Initializes all necessary fields
         */
        public AddNewContactViewHolder(View view, AddNewContactViewModel mModel, UserInfoViewModel uModel) {
            super(view);
            mView = view;
            addNewContactViewModel = mModel;
            userInfoViewModel = uModel;
            binding = FragmentAddNewContactCardBinding.bind(view);
            binding.contactCard.setOnClickListener(this::handleMoreOrLess);

            binding.contactAdd.setOnClickListener(button -> {
                mModel.contactAdd(uModel.getJwt(), Integer.parseInt(mContact.getId()));
                notifyItemRemoved(mContacts.indexOf(mContact));
                mContacts.remove(mContact);
            });
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
                binding.contactPreview.setVisibility(View.VISIBLE);
            } else {
                binding.contactPreview.setVisibility(View.GONE);
            }
        }

        /**
         * Method to fill each contact card with information
         * @param contact ContactCard Object
         */
        void setContact(final ContactCard contact) {
            mContact = contact;

            binding.contactUsername.setText(contact.getUsername());
            binding.contactAvatar.setImageResource(contact.getAvatar());

            String name = contact.getName();
            String user = contact.getUsername();

            String preview = "\r\n" + "Full Name: "+name + "\r\n" +
                    "Username: "+user + "\r\n";
            binding.contactPreview.setText(preview);

            if(sModel.getCurrentThemeID() == R.style.Theme_1_Harmony){
                binding.cardRoot.setCardBackgroundColor(binding.getRoot().getResources().getColor(R.color.offwhite));
                binding.contactUsername.setTextColor(Color.BLACK);
                binding.contactAdd.setColorFilter(binding.getRoot().getResources().getColor(R.color.tan));
                binding.contactPreview.setTextColor(Color.BLACK);
            } else {
                binding.cardRoot.setCardBackgroundColor(binding.getRoot().getResources().getColor(R.color.black));
                binding.contactUsername.setTextColor(binding.getRoot().getResources().getColor(R.color.teal_200));
                binding.contactAdd.setColorFilter(binding.getRoot().getResources().getColor(R.color.white));
                binding.contactPreview.setTextColor(binding.getRoot().getResources().getColor(R.color.teal_200));
            }
            displayPreview();
        }
    }
}
