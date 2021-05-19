package edu.uw.harmony.UI.settings;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import edu.uw.harmony.R;


public class SettingsViewModel extends AndroidViewModel {


    /** The ID of the current theme */
    private int currentThemeID;

    /** The key to access shared preferences */
    public static final String sharedPreferenceKey = "MY_SHARED_PREF";

    /** Key to the switch button index*/
    public static final String switchButtonKey = "SWITCH_BUTTON_KEY";

    /** Key of saved theme*/
    public static final String savedThemeKey = "SAVED_THEME_KEY";

    /** Boolean value to tell us if button is checked */
    private boolean switchState;

    /** Key to determine state of switch */
    public static final String savedSwitchState = "SAVED_SWITCH_STATE";

    /**
     * Creates a new instance of the settings view model
     * @param application
     */
    public SettingsViewModel(@NonNull Application application) {
        super(application);
        SharedPreferences preferences = getApplication().getSharedPreferences(sharedPreferenceKey, Context.MODE_PRIVATE);
        currentThemeID = preferences.getInt(savedThemeKey, R.style.Theme_1_Harmony);
        switchState = preferences.getBoolean(savedSwitchState, false);

    }

    /**
     * @return the currentThemeIndex
     */
    public int getCurrentThemeID(){
        return currentThemeID;
    }

    /**
     * Returns switch state
     * @return true or false
     */
    public boolean getSwitchState(){
        return switchState;
    }

    /**
     * This sets what position the switch was in
     * @param check
     */
    public void setSavedSwitchState(boolean check){
        SharedPreferences.Editor preferences =
                getApplication().getSharedPreferences(sharedPreferenceKey, Context.MODE_PRIVATE).edit();
        preferences.putBoolean(savedSwitchState, check);
        if (preferences.commit()) {
            Log.i("Saved theme information", "Successfully saved theme information: Check State=" + check);
        } else {
            Log.e("ERROR", "Could not save theme information.");
        }
        switchState = check;
    }

    /**
     * This sets the selected theme in shared preference
     * @param themeID
     */
    public void setSelectedTheme(int themeID){
        SharedPreferences.Editor preferences =
                getApplication().getSharedPreferences(sharedPreferenceKey, Context.MODE_PRIVATE).edit();
        preferences.putInt(savedThemeKey, themeID);
        if (preferences.commit()) {
            Log.i("Saved theme information", "Successfully saved theme information: ThemeID=" + themeID);
        } else {
            Log.e("ERROR", "Could not save theme information.");
        }
        currentThemeID = themeID;
    }



}
