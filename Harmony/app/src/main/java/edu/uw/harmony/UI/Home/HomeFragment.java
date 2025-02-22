package edu.uw.harmony.UI.Home;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.service.notification.StatusBarNotification;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import edu.uw.harmony.R;
import edu.uw.harmony.UI.Contacts.ContactGenerator;
import edu.uw.harmony.UI.Contacts.ContactRecyclerViewAdapter;
import edu.uw.harmony.UI.Weather.HourlyForecastRecyclerViewAdapter;
import edu.uw.harmony.UI.Weather.WeatherViewModel;
import edu.uw.harmony.UI.Weather.WeeklyForecastRecyclerViewAdapter;
import edu.uw.harmony.UI.model.LocationViewModel;
import edu.uw.harmony.UI.model.NewMessageCountViewModel;
import edu.uw.harmony.UI.model.UserInfoViewModel;
import edu.uw.harmony.UI.settings.SettingsViewModel;
import edu.uw.harmony.databinding.FragmentHomeBinding;
import edu.uw.harmony.databinding.FragmentWeatherBinding;

/**
 * This fragment shows a few components that summarize info throughout the app. This is the first
 * fragment navigated to when the user signs in.
 *
 * @author  Austin Scott
 * @version 1.1
 */
public class HomeFragment extends Fragment {

    /** This is the binder for home fragment. allows us to accessa all its attributes*/
    private FragmentHomeBinding binding;
    /** ViewModel for settings */
    private SettingsViewModel settingsViewModel;
    /** This is the view model for the user. It allows us to access the email of user.*/
    private UserInfoViewModel model;
    /** This is the weather view model*/
    private WeatherViewModel mWeatherModel;
    /** This is the location view model*/
    private LocationViewModel mLocationModel;

    private boolean mUpdatedByWeatherFragment = false;

    /** This accesses the notifcations*/
    private NotificationViewModel nModel;
    /** This is the notification manager that accesses the list of active notifications*/
    NotificationManager notificationManager;

    @Override
    public void onStart() {
        super.onStart();
        View notificationView = binding.listRoot;
        ((RecyclerView) notificationView).setAdapter(new NotificationRecyclerViewAdapter(nModel.getNotifications(), settingsViewModel));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        settingsViewModel = new ViewModelProvider(getActivity()).get(SettingsViewModel.class);
        model = new ViewModelProvider(getActivity()).get(UserInfoViewModel.class);
        nModel = new ViewModelProvider(getActivity()).get(NotificationViewModel.class);
        mLocationModel = new ViewModelProvider(getActivity()).get(LocationViewModel.class);
        mWeatherModel = new ViewModelProvider(getActivity()).get(WeatherViewModel.class);
        notificationManager = (NotificationManager) getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);


        //Set up action listener for weather updates
        mWeatherModel.addCurrentWeatherObserver(getViewLifecycleOwner(), currentWeather -> {
            Log.d("Debug", "Home Fragment weather: " + currentWeather.temp);
            binding.imageViewMainConditionsPlaceholder.setImageResource(currentWeather.image);
            if(!currentWeather.temp.equals("Loading...")){
                binding.textDegHome.setText(
                        Math.round(
                                Double.parseDouble(currentWeather.temp)) + "°");
            }
        });

        /** I instantiate the recycler view here.*/
        View notificationView = binding.listRoot;
        if (notificationView instanceof RecyclerView){
            ((RecyclerView) notificationView).setAdapter(new NotificationRecyclerViewAdapter(nModel.getNotifications(), settingsViewModel));

        }

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences prefs =
                getActivity().getSharedPreferences(
                        getString(R.string.keys_shared_prefs),
                        Context.MODE_PRIVATE);//Store the credentials in SharedPrefs
        if (prefs.contains(getString(R.string.keys_prefs_verified)) ) {
            String token = prefs.getString(getString(R.string.keys_prefs_verified), "");
            if (token.equals("0"))
            Navigation.findNavController(view).navigate(HomeFragmentDirections.actionNavigationHomeToEmailVerificationFragment());
        }

        if (prefs.contains(getString(R.string.keys_prefs_recovering))) {
            String token = prefs.getString(getString(R.string.keys_prefs_recovering), "");
            Log.e("TOKEN for recovering", token);
            if (token.equals("1")) {
                Navigation.findNavController(view).navigate(HomeFragmentDirections.actionNavigationHomeToPasswordChangeFragment());
            }
        }

        BottomNavigationView navBar = getActivity().findViewById(R.id.nav_view);
        navBar.setVisibility(View.VISIBLE);

        /** Clears the notifications from home screen */
        binding.buttonClearHome.setOnClickListener(button -> {
            notificationManager.cancelAll();
            nModel.clearNotifications();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(this).attach(this).commit();
                });


        nModel.addNotificationObserver(getViewLifecycleOwner(), notification -> {
            if (binding.listRoot instanceof RecyclerView) {
                (binding.listRoot).setAdapter(
                        new NotificationRecyclerViewAdapter(notification, settingsViewModel));
            }
        });

        /** Dependent on the theme, this will set all text / image fields to a certain color. */
        if(settingsViewModel.getCurrentThemeID() == R.style.Theme_1_Harmony){
            binding.textEmailHome.setTextColor(Color.BLACK);
            binding.textDegHome.setTextColor(Color.BLACK);
            binding.textNotificationsHome.setTextColor(Color.BLACK);
            binding.imageLogoHome.setColorFilter(Color.BLACK);
            binding.buttonClearHome.setBackgroundColor(getResources().getColor(R.color.tan));
            binding.buttonClearHome.setTextColor(getResources().getColor(R.color.white));
        } else {
            binding.textEmailHome.setTextColor(Color.WHITE);
            binding.textDegHome.setTextColor(Color.WHITE);
            binding.textNotificationsHome.setTextColor(Color.WHITE);
            binding.imageLogoHome.setColorFilter(Color.WHITE);
            binding.buttonClearHome.setBackgroundColor(getResources().getColor(R.color.lighter_blue));
            binding.buttonClearHome.setTextColor(Color.WHITE);

        }
        /** Sets the email and gets rid of the progress bar.*/
        Log.d("STATUS", "Got to success");
        binding.textEmailHome.setText(model.getEmail());
        binding.layoutWait.setVisibility(view.GONE);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void setUpdatedByWeatherFragment(boolean b) {
        mUpdatedByWeatherFragment = b;
    }
}