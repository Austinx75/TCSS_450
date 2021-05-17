package edu.uw.harmony;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;

import com.auth0.android.jwt.JWT;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import edu.uw.harmony.UI.model.UserInfoViewModel;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivityArgs args = MainActivityArgs.fromBundle(getIntent().getExtras());
        //Import com.auth0.android.jwt.JWT
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

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_contact, R.id.navigation_chats, R.id.navigation_weather
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
}