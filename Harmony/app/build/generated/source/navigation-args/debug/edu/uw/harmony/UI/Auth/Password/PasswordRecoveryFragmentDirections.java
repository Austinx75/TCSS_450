package edu.uw.harmony.UI.Auth.Password;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import edu.uw.harmony.R;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class PasswordRecoveryFragmentDirections {
  private PasswordRecoveryFragmentDirections() {
  }

  @NonNull
  public static ActionPasswordRecoveryFragmentToRecoveryCompleteFragment actionPasswordRecoveryFragmentToRecoveryCompleteFragment(
      ) {
    return new ActionPasswordRecoveryFragmentToRecoveryCompleteFragment();
  }

  public static class ActionPasswordRecoveryFragmentToRecoveryCompleteFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    private ActionPasswordRecoveryFragmentToRecoveryCompleteFragment() {
    }

    @NonNull
    public ActionPasswordRecoveryFragmentToRecoveryCompleteFragment setEmail(
        @NonNull String email) {
      if (email == null) {
        throw new IllegalArgumentException("Argument \"email\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("email", email);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("email")) {
        String email = (String) arguments.get("email");
        __result.putString("email", email);
      } else {
        __result.putString("email", "default");
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_passwordRecoveryFragment_to_recoveryCompleteFragment;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getEmail() {
      return (String) arguments.get("email");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionPasswordRecoveryFragmentToRecoveryCompleteFragment that = (ActionPasswordRecoveryFragmentToRecoveryCompleteFragment) object;
      if (arguments.containsKey("email") != that.arguments.containsKey("email")) {
        return false;
      }
      if (getEmail() != null ? !getEmail().equals(that.getEmail()) : that.getEmail() != null) {
        return false;
      }
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionPasswordRecoveryFragmentToRecoveryCompleteFragment(actionId=" + getActionId() + "){"
          + "email=" + getEmail()
          + "}";
    }
  }
}
