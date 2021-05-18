package edu.uw.harmony;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import android.os.Bundle;
import android.util.Log;

import com.auth0.android.jwt.JWT;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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

import edu.uw.harmony.UI.Chat.message.ChatMessage;
import edu.uw.harmony.UI.Chat.message.ChatViewModel;
import edu.uw.harmony.UI.model.NewMessageCountViewModel;
import edu.uw.harmony.UI.model.UserInfoViewModel;
import edu.uw.harmony.databinding.ActivityMainBinding;
import edu.uw.harmony.services.PushReceiver;


public class MainActivity extends AppCompatActivity {

    private MainPushMessageReceiver mPushMessageReceiver;
    private NewMessageCountViewModel mNewMessageModel;

    private ActivityMainBinding binding;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTheme();
        //setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MainActivityArgs args = MainActivityArgs.fromBundle(getIntent().getExtras());
        JWT jwt = new JWT(args.getJwt());
        String email = args.getEmail();

        // Check to see if the web token is still valid or not. To make a JWT expire after a
        // longer or shorter time period, change the expiration time when the JWT is
        // created on the web service.
        //TEMPORARILY DISABLED UNTIL WE GET JWT FROM OUR OWN SERVER
//        if(!jwt.isExpired(0)) {
//            new ViewModelProvider(
//                    this,
//                    new UserInfoViewModel.UserInfoViewModelFactory(email, jwt.toString()))
//                    .get(UserInfoViewModel.class);
//        } else {
//            //In production code, add in your own error handling/flow for when the JWT is expired
//            throw new IllegalStateException("JWT is expired!");
//        }
        new ViewModelProvider(
                this,
                new UserInfoViewModel.UserInfoViewModelFactory(email, jwt.toString()))
                .get(UserInfoViewModel.class);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);

        new ViewModelProvider(this,
                new UserInfoViewModel.UserInfoViewModelFactory(args.getEmail(), args.getJwt())
        ).get(UserInfoViewModel.class);

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
                R.id.navigation_home, R.id.navigation_contact, R.id.navigation_chat_list, R.id.navigation_weather
        ).build();


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController,mAppBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        mNewMessageModel = new ViewModelProvider(this).get(NewMessageCountViewModel.class);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.navigation_chat_post) {
                //When the user navigates to the chats page, reset the new message count.
                // This will need some extra logic for your project as it should have
                // multiple chat rooms.
                mNewMessageModel.reset();
            }
        });
        mNewMessageModel.addMessageCountObserver(this, count -> {
            BadgeDrawable badge = binding.navView.getOrCreateBadge(R.id.navigation_chat_post);
            badge.setMaxCharacterCount(2);
            if (count > 0) {
                //new messages! update and show the notification badge.
                badge.setNumber(count);
                badge.setVisible(true);
            } else {
                //user did some action to clear the new messages, remove the badge
                badge.clearNumber();
                badge.setVisible(false);
            }
        });

    }


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
        IntentFilter iFilter = new IntentFilter(PushReceiver.RECEIVED_NEW_MESSAGE);

        registerReceiver(mPushMessageReceiver, iFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPushMessageReceiver != null) {
            unregisterReceiver(mPushMessageReceiver);
        }
    }

    /**
     * A BroadcastReceiver that
     */
    private class MainPushMessageReceiver extends BroadcastReceiver {

        private ChatViewModel mModel = new ViewModelProvider(MainActivity.this)
                .get(ChatViewModel.class);

        @Override
        public void onReceive(Context context, Intent intent) {
            NavController nc = Navigation.findNavController(
                    MainActivity.this, R.id.nav_host_fragment);

            NavDestination nd = nc.getCurrentDestination();

            if (intent.hasExtra("chatMessage")) {
                ChatMessage cm = (ChatMessage) intent.getSerializableExtra("chatMessage");
                if (nd.getId() != R.id.navigation_chat_post) {
                    mNewMessageModel.increment();
                }

                mModel.addMessage(intent.getIntExtra("chatid", -1), cm);
            }
        }
    }


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    //To handle clicks
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

    private void signOut() {
        SharedPreferences prefs =
                getSharedPreferences(
                        getString(R.string.keys_shared_prefs),
                        Context.MODE_PRIVATE);
        prefs.edit().remove(getString(R.string.keys_prefs_jwt)).apply();
        //End the app completely
        finishAndRemoveTask();
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