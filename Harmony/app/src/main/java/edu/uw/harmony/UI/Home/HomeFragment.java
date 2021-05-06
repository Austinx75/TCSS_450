package edu.uw.harmony.UI.Home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.harmony.R;
import edu.uw.harmony.UI.model.UserInfoViewModel;
import edu.uw.harmony.databinding.FragmentHomeBinding;

/**
 * This fragment shows a few components that summarize info throughout the app. This is the first
 * fragment navigated to when the user signs in.
 *
 * @author  Gary Kono
 * @version 1.0
 */
public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d("STATUS", "Got to success");

        //Currently throws a weird error. Will have to debug in the future.
        //Note argument sent to the ViewModelProvider constructor. It is the Activity that
        // holds this fragment.
//        UserInfoViewModel model = new ViewModelProvider(getActivity())
//                .get(UserInfoViewModel.class);
//
//        //Set the text color of the label. NOTE no need to cast
//        binding.textViewGreeting.setText("Hello, " + model.getEmail());
        binding.textViewGreeting.setText("Hello, welcome to the home page.");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}