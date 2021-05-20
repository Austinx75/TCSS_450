package edu.uw.harmony.UI.settings;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import edu.uw.harmony.R;

public class SettingsFragmentDirections {
  private SettingsFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionSettingsFragmentToPasswordChangeFragment() {
    return new ActionOnlyNavDirections(R.id.action_settingsFragment_to_passwordChangeFragment);
  }
}
