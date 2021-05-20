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

public class RecoveryCompleteFragmentDirections {
  private RecoveryCompleteFragmentDirections() {
  }

  @NonNull
  public static ActionRecoveryCompleteFragmentToLogInFragment actionRecoveryCompleteFragmentToLogInFragment(
      ) {
    return new ActionRecoveryCompleteFragmentToLogInFragment();
  }

  public static class ActionRecoveryCompleteFragmentToLogInFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    private ActionRecoveryCompleteFragmentToLogInFragment() {
    }

    @NonNull
    public ActionRecoveryCompleteFragmentToLogInFragment setEmail(@NonNull String email) {
      if (email == null) {
        throw new IllegalArgumentException("Argument \"email\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("email", email);
      return this;
    }

    @NonNull
    public ActionRecoveryCompleteFragmentToLogInFragment setPassword(@NonNull String password) {
      if (password == null) {
        throw new IllegalArgumentException("Argument \"password\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("password", password);
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
      if (arguments.containsKey("password")) {
        String password = (String) arguments.get("password");
        __result.putString("password", password);
      } else {
        __result.putString("password", "default");
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_recoveryCompleteFragment_to_logInFragment;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getEmail() {
      return (String) arguments.get("email");
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getPassword() {
      return (String) arguments.get("password");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionRecoveryCompleteFragmentToLogInFragment that = (ActionRecoveryCompleteFragmentToLogInFragment) object;
      if (arguments.containsKey("email") != that.arguments.containsKey("email")) {
        return false;
      }
      if (getEmail() != null ? !getEmail().equals(that.getEmail()) : that.getEmail() != null) {
        return false;
      }
      if (arguments.containsKey("password") != that.arguments.containsKey("password")) {
        return false;
      }
      if (getPassword() != null ? !getPassword().equals(that.getPassword()) : that.getPassword() != null) {
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
      result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionRecoveryCompleteFragmentToLogInFragment(actionId=" + getActionId() + "){"
          + "email=" + getEmail()
          + ", password=" + getPassword()
          + "}";
    }
  }
}
