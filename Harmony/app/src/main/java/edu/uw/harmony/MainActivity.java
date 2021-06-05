package edu.uw.harmony;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.location.Location;
import android.os.Bundle;
import android.provider.Telephony;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import com.auth0.android.jwt.JWT;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import edu.uw.harmony.UI.Weather.WeatherViewModel;
import edu.uw.harmony.UI.model.LocationViewModel;
import edu.uw.harmony.UI.model.NewContactCountViewModel;
import edu.uw.harmony.UI.model.UserInfoViewModel;

import android.view.Menu;
import android.view.MenuItem;


import com.auth0.android.jwt.JWT;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import edu.uw.harmony.UI.model.UserInfoViewModel;
import edu.uw.harmony.UI.settings.SettingsFragment;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import edu.uw.harmony.UI.Chat.message.ChatMessage;
import edu.uw.harmony.UI.Chat.message.ChatViewModel;
import edu.uw.harmony.UI.Home.NotificationRecyclerViewAdapter;
import edu.uw.harmony.UI.Home.NotificationViewModel;
import edu.uw.harmony.UI.model.LocationViewModel;
import edu.uw.harmony.UI.model.NewMessageCountViewModel;
import edu.uw.harmony.UI.model.UserInfoViewModel;
import edu.uw.harmony.UI.settings.SettingsFragment;
import edu.uw.harmony.UI.settings.SettingsViewModel;
import edu.uw.harmony.databinding.ActivityMainBinding;
import edu.uw.harmony.databinding.FragmentHomeBinding;
import edu.uw.harmony.services.PushReceiver;


public class  MainActivity extends AppCompatActivity {

    private MainPushMessageReceiver mPushMessageReceiver;
    private NewContactPushReceiver mContactPushReceiver;
    private NewChatPushReceiver mNewChatPushReceiver;

    private NewMessageCountViewModel mNewMessageModel;
    private NewContactCountViewModel mNewContactModel;
    private NotificationViewModel nModel;

    private ActivityMainBinding binding;

    private AppBarConfiguration mAppBarConfiguration;

    private FragmentHomeBinding fBinding;

    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    /**
     * The fastest rate for active location updates. Exact. Updates will never be more frequent
     * than this value.
     */
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    // A constant int for the permissions request code. Must be a 16 bit number
    private static final int MY_PERMISSIONS_LOCATIONS = 8414;

    private LocationRequest mLocationRequest;

    //Use a FusedLocationProviderClient to request the location
    private FusedLocationProviderClient mFusedLocationClient;

    // Will use this call back to decide what to do when a location change is detected
    private LocationCallback mLocationCallback;

    //The ViewModel that will store the current location
    private LocationViewModel mLocationModel;
    /** Notification manager that accesses system services*/
    NotificationManager notificationManager;
    /** Stores the status bar notifications*/
    ArrayList<StatusBarNotification> notifications;
    /** Accesses the settings**/
    private SettingsViewModel settingsViewModel;


    /**
     * Recieves messages from system service and adds them to notification view model.
     */
    public void onStart() {
        super.onStart();

        nModel = new ViewModelProvider(this).get(NotificationViewModel.class);
        notificationManager = (NotificationManager) this.getSystemService(this.NOTIFICATION_SERVICE);
        notifications = new ArrayList<>(Arrays.asList(notificationManager.getActiveNotifications()));
        NavController nc = Navigation.findNavController(
                MainActivity.this, R.id.nav_host_fragment);
        NavDestination nd = nc.getCurrentDestination();

        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a");
        for(int i = 0; i < notifications.size(); i++){
            if(notifications.get(i).getNotification().extras.getCharSequence(Notification.EXTRA_TITLE) == null || notifications.get(i).getNotification().extras.getCharSequence(Notification.EXTRA_INFO_TEXT) == null){

            } else {
                if(notifications.get(i).getNotification().extras.getCharSequence(Notification.EXTRA_INFO_TEXT).equals("chat")){
                    Log.d("Back", "Enters right if");
                    Timestamp ts = new Timestamp(System.currentTimeMillis());
                    Date date = new Date(ts.getTime());
                    SimpleDateFormat formatter1 = new SimpleDateFormat("hh:mm a");
                    String dateString = formatter1.format(date);
                    nModel.addNotification(notifications
                                    .get(i)
                                    .getNotification()
                                    .extras
                                    .getCharSequence(Notification.EXTRA_TITLE).toString(),
                            notifications.get(i).getNotification()
                                    .extras
                                    .getCharSequence(Notification.EXTRA_TEXT)
                                    .toString(),
                            dateString);

                    if(nd.getId() != R.id.navigation_chat_list){
                        mNewMessageModel.increment(Integer.parseInt(notifications.get(i).getNotification().extras.getCharSequence(Notification.EXTRA_SUB_TEXT).toString()));
                    }
                } else if(notifications.get(i).getNotification().extras.getCharSequence(Notification.EXTRA_INFO_TEXT).equals("contacts")) {

                    Log.d("Back", "Enters right if");
                    Timestamp ts = new Timestamp(System.currentTimeMillis());
                    Date date = new Date(ts.getTime());
                    SimpleDateFormat formatter1 = new SimpleDateFormat("hh:mm a");
                    String dateString = formatter1.format(date);
                    nModel.addNotification(notifications
                            .get(i)
                            .getNotification()
                            .extras
                            .getCharSequence(Notification.EXTRA_TITLE)
                            .toString(),
                            notifications
                                    .get(i)
                                    .getNotification()
                                    .extras.getCharSequence(Notification.EXTRA_TEXT)
                                    .toString(),
                            dateString);

                    if(nd.getId() != R.id.navigation_contact_container){
                        mNewContactModel.increment();
                    }
                } else {
                    String dateString = formatter.format(new Date(notifications.get(i).getPostTime()));
                    nModel.addNotification(notifications
                                    .get(i)
                                    .getNotification()
                                    .extras
                                    .getCharSequence(Notification.EXTRA_TITLE)
                                    .toString()
                                    .substring(13,notifications.
                                            get(i)
                                            .getNotification()
                                            .extras
                                            .getCharSequence(Notification.EXTRA_TITLE)
                                            .length()),
                            notifications.get(i).getNotification().extras.getCharSequence(Notification.EXTRA_TEXT).toString(), dateString);
                    if(nd.getId() != R.id.navigation_chat_list){
                        mNewMessageModel.increment(Integer.parseInt(notifications.get(i).getNotification().extras.getCharSequence(Notification.EXTRA_SUB_TEXT).toString()));
                    }
                }
            }
        }
        notifications.clear();
        notificationManager.cancelAll();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTheme();
        //setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fBinding = FragmentHomeBinding.inflate(getLayoutInflater());
        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);



        MainActivityArgs args = MainActivityArgs.fromBundle(getIntent().getExtras());
        JWT jwt = new JWT(args.getJwt());
        String email = args.getEmail();
        new ViewModelProvider(
                this,
                new UserInfoViewModel.UserInfoViewModelFactory(email, jwt.toString()))
                .get(UserInfoViewModel.class);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);

        /** Changing the color for the bottom nav bar icons. */
        if(getCurrentTheme() == R.style.Theme_1_Harmony){
            navView.setItemIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.orange, null)));
            navView.setItemBackgroundResource(R.color.accent_tan);
            navView.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.black, null)));
        } else {
            navView.setItemIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.teal_200, null)));
            navView.setItemBackgroundResource(R.color.black);
            navView.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.white, null)));
        }


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_contact_container, R.id.navigation_chat_list, R.id.navigation_weather_container
        ).build();


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController,mAppBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.navigation_contact_container) {
                mNewContactModel.reset();
                BadgeDrawable badge = binding.navView.getOrCreateBadge(R.id.navigation_contact_container);
                badge.clearNumber();
                badge.setVisible(false);
            }
            if(destination.getId() == R.id.navigation_chat_list){
                BadgeDrawable badge = binding.navView.getOrCreateBadge(R.id.navigation_chat_list);
                badge.clearNumber();
                badge.setVisible(false);
            }
        });

        mNewMessageModel = new ViewModelProvider(this).get(NewMessageCountViewModel.class);
        mNewContactModel = new ViewModelProvider(this).get(NewContactCountViewModel.class);

        mNewContactModel.addContactCountObserver(this, count ->{
            Log.e("total ", "" +count);
            BadgeDrawable badge = binding.navView.getOrCreateBadge(R.id.navigation_contact_container);
            badge.setMaxCharacterCount(3);
            if ( count > 0) {
                //new messages! update and show the notification badge.
                badge.setNumber(count);
                badge.setVisible(true);
            } else {
                //user did some action to clear the new messages, remove the badge
                badge.clearNumber();
                badge.setVisible(false);
            }
        });

;
        mNewMessageModel.addMessageCountObserver(this, mapping -> {
            int total = sum(mapping) ;
            Log.e("total ", "" +total);
            BadgeDrawable badge = binding.navView.getOrCreateBadge(R.id.navigation_chat_list);
            badge.setMaxCharacterCount(3);
            if ( total > 0) {
                //new messages! update and show the notification badge.
                badge.setNumber(total);
                badge.setVisible(true);
            } else {
                //user did some action to clear the new messages, remove the badge
                badge.clearNumber();
                badge.setVisible(false);
            }

        });

        //Location Setup
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION
                            , Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_LOCATIONS);
        } else {
            //The user has already allowed the use of Locations. Get the current location.
            requestLocation();
        }

        mLocationCallback = new LocationCallback() {
            @Override public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    // Update UI with location data
                    // ...
                    Log.d("LOCATION UPDATE!", location.toString());
                    if (mLocationModel == null) {
                        mLocationModel = new ViewModelProvider(MainActivity.this)
                                .get(LocationViewModel.class);
                    }
                    mLocationModel.setLocation(location);
                }
            }
        };
        createLocationRequest();
    }

    /**
     * Helper function that sums the chat room new messages
     * @param map the map representing the notification for each chat room
     * @return the total number of unseen messages
     */
    private int sum(Map<Integer, Integer> map) {
        int total = 0;
        for (Map.Entry<Integer, Integer> entry: map.entrySet()) {
            if (entry.getKey() != mNewMessageModel.getCurrentChatRoom()) {
            total += entry.getValue();}
        }
        return total;
    }



    /**
     * Allows us to navigate between fragments.
     * @return
     */
    @Override
        public boolean onSupportNavigateUp() {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                    || super.onSupportNavigateUp();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mPushMessageReceiver == null) {
            mPushMessageReceiver = new MainPushMessageReceiver();
        }
        if(mNewChatPushReceiver == null){
            mNewChatPushReceiver = new NewChatPushReceiver();
        }
        if(mContactPushReceiver == null){
            mContactPushReceiver = new NewContactPushReceiver();
        }
        IntentFilter iFilter = new IntentFilter(PushReceiver.RECEIVED_NEW_MESSAGE);
        IntentFilter nFilter = new IntentFilter(PushReceiver.RECEIVED_NEW_CHAT);
        IntentFilter cFilter = new IntentFilter(PushReceiver.RECEIVED_NEW_CONTACT);

        registerReceiver(mPushMessageReceiver, iFilter);
        registerReceiver(mContactPushReceiver, cFilter);
        registerReceiver(mNewChatPushReceiver, nFilter);

        //startLocationUpdates();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPushMessageReceiver != null) {
            unregisterReceiver(mPushMessageReceiver);
        }

        //stopLocationUpdates();
    }

    /**
     * A BroadcastReceiver that
     */
    private class MainPushMessageReceiver extends BroadcastReceiver {

        private ChatViewModel mModel = new ViewModelProvider(MainActivity.this)
                .get(ChatViewModel.class);
        private NotificationViewModel nModel = new ViewModelProvider(MainActivity.this).get(NotificationViewModel.class);

        private UserInfoViewModel model = new ViewModelProvider(MainActivity.this).get(UserInfoViewModel.class);


        @Override
        public void onReceive(Context context, Intent intent) {

            NavController nc = Navigation.findNavController(
                    MainActivity.this, R.id.nav_host_fragment);
            Log.d("ReceivePushy", "Notification Received");

            NavDestination nd = nc.getCurrentDestination();
            if (intent.hasExtra("chatMessage")) {

                ChatMessage cm = (ChatMessage) intent.getSerializableExtra("chatMessage");
                Log.e("CM ID", "CM" +  cm.getChatId() + " CURRENT ROOm" +mNewMessageModel.getCurrentChatRoom());
                Log.d("MAin Activity", cm.getMessage());
                if (cm.getChatId() == mNewMessageModel.getCurrentChatRoom()){
                    mNewMessageModel.reset();
                }
                else {
                    mNewMessageModel.increment(cm.getChatId());
                }
                Timestamp ts = new Timestamp(System.currentTimeMillis());
                Date date = new Date(ts.getTime());
                SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a");
                String dateString = formatter.format(date);
                if(!cm.getSender().equals(model.getEmail())){
                    nModel.addNotification(cm, dateString);
                }
                mModel.addMessage(intent.getIntExtra("chatid", -1), cm);
            }

        }
    }

    private class NewChatPushReceiver extends BroadcastReceiver {
        private NotificationViewModel nModel = new ViewModelProvider(MainActivity.this).get(NotificationViewModel.class);

        private UserInfoViewModel model = new ViewModelProvider(MainActivity.this).get(UserInfoViewModel.class);

        NavController nc = Navigation.findNavController(
                MainActivity.this, R.id.nav_host_fragment);
        NavDestination nd = nc.getCurrentDestination();

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.hasExtra("newChat")){
                Log.d("Messages", "made it to main activity");
                Log.d("PUSHY", "Received in main in if statement");
                Timestamp ts = new Timestamp(System.currentTimeMillis());
                Date date = new Date(ts.getTime());
                SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a");
                String dateString = formatter.format(date);
                nModel.addNotification(intent.getStringExtra("member"), intent.getStringExtra("newChat"), dateString);
                if(nd.getId() != R.id.navigation_chat_list){
                    mNewMessageModel.increment(intent.getIntExtra("chatid", -1));
                }
            }
        }

    }

    private class NewContactPushReceiver extends BroadcastReceiver {
        private NotificationViewModel nModel = new ViewModelProvider(MainActivity.this).get(NotificationViewModel.class);

        private UserInfoViewModel model = new ViewModelProvider(MainActivity.this).get(UserInfoViewModel.class);

        NavController nc = Navigation.findNavController(
                MainActivity.this, R.id.nav_host_fragment);
        NavDestination nd = nc.getCurrentDestination();

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.hasExtra("contactId")){
                Log.d("PUSHY", "Received in main in if statement");
                Timestamp ts = new Timestamp(System.currentTimeMillis());
                Date date = new Date(ts.getTime());
                SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a");
                String dateString = formatter.format(date);
                nModel.addNotification(intent.getStringExtra("contactId"), intent.getStringExtra("message"), dateString);

                if(nd.getId() != R.id.navigation_contact_container){
                    mNewContactModel.increment();
                }

            }

        }
    }



    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    /**
     * It controls what happens if the user selects settings or sign out
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.action_sign_out){
            signOut();
            return true;
        }
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);


    }

    /**
     * Requests location updates from the FusedLocationApi.
     */
    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                    mLocationCallback,
                    null /* Looper */);
        }
    }

    /**
     * Removes location updates from the FusedLocationApi.
     */ private void stopLocationUpdates() {
        // It is a good practice to remove location requests when the activity is in a paused or
        // stopped state. Doing so helps battery performance and is especially
        // recommended in applications that request frequent location updates.
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

    private void requestLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            Log.d("REQUEST LOCATION", "User did NOT allow permission to request location!");
        } else {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                Log.d("LOCATION", location.toString());
                                if (mLocationModel == null) {
                                    mLocationModel = new ViewModelProvider(MainActivity.this)
                                            .get(LocationViewModel.class);
                                }
                                mLocationModel.setLocation(location);

                                //When first getting a location, setup the weather view model so that
                                //it can connect to the current location from here
                                setupWeatherModel();
                            }
                        }
                    });
        }
    }

    @Override public void onRequestPermissionsResult(int requestCode,
                                                     String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_LOCATIONS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // locations-related task you need to do.
                    requestLocation();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Log.d("PERMISSION DENIED", "Nothing to see or do here.");
                    //Shut down the app. In production release, you would let the user
                    // know why the app is shutting down...maybe ask for permission again?
                    finishAndRemoveTask();
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    /**
     * Create and configure a Location Request used when retrieving location updates
     */
    private void createLocationRequest() {
        mLocationRequest = LocationRequest.create();

        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than
        // requested if other applications are requesting location at a faster interval.
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    /**
     * Initializes the weather model with a connection to this activity (to also connect to the location
     * view model) so that it has access to the user's current location before visiting the weather fragment
     */
    private void setupWeatherModel() {
        WeatherViewModel weatherModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        weatherModel.setCurrentActivity(this);
        weatherModel.setupLocationModel();
        weatherModel.useCurrentLocation();
    }

    /**
     * This signs the user out, and deletes the preference key for the jwt.
     */
    private void signOut() {
        SharedPreferences prefs =
                getSharedPreferences(
                        getString(R.string.keys_shared_prefs),
                        Context.MODE_PRIVATE);
        prefs.edit().remove(getString(R.string.keys_prefs_jwt)).apply();
        //End the app completely
        finishAndRemoveTask();
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
    }



    /**
     * Fetches the last used theme the user used with the app.
     */
    private void initTheme() {
        SharedPreferences sharedPreferences = getSharedPreferences(SettingsFragment.sharedPreferenceKey, Context.MODE_PRIVATE);
        int lastTheme = sharedPreferences.getInt(SettingsFragment.savedThemeKey, R.style.Theme_1_Harmony);
        this.setTheme(lastTheme);
    }

    /**
     *
     * @return the current theme of the activity.
     */
    private int getCurrentTheme(){
        SharedPreferences sharedPreferences = getSharedPreferences(SettingsFragment.sharedPreferenceKey, Context.MODE_PRIVATE);
        int lastTheme = sharedPreferences.getInt(SettingsFragment.savedThemeKey, R.style.Theme_1_Harmony);
        return lastTheme;
    }
}