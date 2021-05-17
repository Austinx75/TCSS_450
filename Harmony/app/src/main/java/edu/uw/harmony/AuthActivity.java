package edu.uw.harmony;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import edu.uw.harmony.UI.settings.SettingsFragment;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTheme();
        setContentView(R.layout.activity_auth);
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