package edu.uw.harmony.UI.Contacts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.uw.harmony.databinding.FragmentContactRequestCardBinding;

public class ContactRequestCardFragment extends Fragment {
    /** binding variable that allows interaction with views */
    private FragmentContactRequestCardBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContactRequestCardBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentContactRequestCardBinding.bind(getView());
    }
}
