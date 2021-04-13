package edu.uw.tcss450.als48.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.als48.R;
import edu.uw.tcss450.als48.databinding.FragmentColorBinding;
import edu.uw.tcss450.als48.databinding.FragmentFirstBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColorFragment extends Fragment {

    FragmentColorBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentColorBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Get a reference to the SafeArgs object
        ColorFragmentArgs args = ColorFragmentArgs.fromBundle(getArguments());

        //Set the text color of the label. NOTE no need to cast.
        binding.textLabel.setTextColor(args.getColor());
    }

    private void updateContent(int color){
        binding.textLabel.setTextColor(color);
    }

}