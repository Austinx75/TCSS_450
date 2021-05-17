package edu.uw.harmony.UI.Contacts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import edu.uw.harmony.R;
import edu.uw.harmony.databinding.FragmentContactListBinding;

/**
 * This is a Fragment of a list of contact card instances.
 *
 * @author Jack Lin
 * @version 1.0
 */

public class ContactListFragment extends Fragment {

    private ContactListViewModel mModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = new ViewModelProvider(getActivity()).get(ContactListViewModel.class);
        mModel.connectGet();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_list, container, false);
        View contactView = view.findViewById(R.id.list_root);
        if (contactView instanceof RecyclerView){
            ((RecyclerView) contactView).setAdapter(new ContactRecyclerViewAdapter(ContactGenerator.getContactList()));
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentContactListBinding binding = FragmentContactListBinding.bind(getView());
        binding.layoutWait.setVisibility(View.GONE);
    }
}