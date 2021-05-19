package edu.uw.harmony.UI.settings.Password;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import edu.uw.harmony.R;

public class PasswordChangeSuccessFragmentDirections {
  private PasswordChangeSuccessFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionPasswordChangeSuccessFragmentToNavigationHome() {
    return new ActionOnlyNavDirections(R.id.action_passwordChangeSuccessFragment_to_navigation_home);
  }
}
