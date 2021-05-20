package edu.uw.harmony.UI.settings.Password;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import edu.uw.harmony.R;

public class PasswordChangeFragmentDirections {
  private PasswordChangeFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionPasswordChangeFragmentToPasswordChangeSuccessFragment() {
    return new ActionOnlyNavDirections(R.id.action_passwordChangeFragment_to_passwordChangeSuccessFragment);
  }
}
