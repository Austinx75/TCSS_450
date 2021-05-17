package edu.uw.harmony;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.auth0.android.jwt.JWT;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import edu.uw.harmony.UI.model.UserInfoViewModel;
import edu.uw.harmony.UI.settings.SettingsFragment;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTheme();
        setContentView(R.layout.activity_main);

        MainActivityArgs args = MainActivityArgs.fromBundle(getIntent().getExtras());
        String email = args.getEmail();

        JWT jwt = new JWT(args.getJwt());

        // Check to see if the web token is still valid or not. To make a JWT expire after a
        // longer or shorter time period, change the expiration time when the JWT is
        // created on the web service.
//        if(!jwt.isExpired(0)) {
            //take note that we are not using the constructor explicitly, the no-arg
            //constructor is called implicitly
            //UserInfoViewModel model = new ViewModelProvider(this).get(UserInfoViewModel.class);

            //Take note of the need to use the setter, since we have to use a no-arg constructor
            //model.setJWT(jwt);
            new ViewModelProvider(this,
                    new UserInfoViewModel.UserInfoViewModelFactory(args.getEmail(), args.getJwt())).get(UserInfoViewModel.class);

//        } else {
//            //In production code, add in your own error handling/flow for when the JWT is expired
//            throw new IllegalStateException("JWT is expired!");
//
//        }

        BottomNavigationView navView = findViewById(R.id.nav_view);

        /** Changing the color for the bottom nav bar icons. */
        if(getCurrentTheme() == R.style.Theme_1_Harmony){
            navView.setItemIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.orange)));
            navView.setItemBackgroundResource(R.color.accent_tan);
        } else {
            navView.setItemIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.teal_200)));
            navView.setItemBackgroundResource(R.color.black);
        }

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_contact, R.id.navigation_chat_list, R.id.navigation_weather
        ).build();


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController,mAppBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

    }


    @Override
        public boolean onSupportNavigateUp() {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                    || super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    //To handle clicks
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);
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