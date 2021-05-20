package edu.uw.harmony;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import edu.uw.harmony.UI.model.PushyTokenViewModel;
import me.pushy.sdk.Pushy;

import edu.uw.harmony.UI.settings.SettingsFragment;

/**
 * This is the activity that handles the log in and registration
 * @author Austin Scott
 * @version 1.0
 */
public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTheme();
        setContentView(R.layout.activity_auth);
        //If it is not already running, start the Pushy listeningservice
        Pushy.listen(this);
        initiatePushyTokenRequest();
    }
    private void initiatePushyTokenRequest() {
        new ViewModelProvider(this).get(PushyTokenViewModel.class).retrieveToken();
    }

    /**
     * Fetches the last used theme the user used with the app.
     */
    private void initTheme() {
        SharedPreferences sharedPreferences = getSharedPreferences(SettingsFragment.sharedPreferenceKey, Context.MODE_PRIVATE);
        int lastTheme = sharedPreferences.getInt(SettingsFragment.savedThemeKey, R.style.Theme_1_Harmony);
        this.setTheme(lastTheme);
    }
}