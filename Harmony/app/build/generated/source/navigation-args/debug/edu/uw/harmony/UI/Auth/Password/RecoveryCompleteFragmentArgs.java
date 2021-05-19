package edu.uw.harmony.UI.Auth.Password;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.NavArgs;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class RecoveryCompleteFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private RecoveryCompleteFragmentArgs() {
  }

  private RecoveryCompleteFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static RecoveryCompleteFragmentArgs fromBundle(@NonNull Bundle bundle) {
    RecoveryCompleteFragmentArgs __result = new RecoveryCompleteFragmentArgs();
    bundle.setClassLoader(RecoveryCompleteFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("email")) {
      String email;
      email = bundle.getString("email");
      if (email == null) {
        throw new IllegalArgumentException("Argument \"email\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("email", email);
    } else {
      __result.arguments.put("email", "default");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getEmail() {
    return (String) arguments.get("email");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
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
  public boolean equals(Object object) {
    if (this == object) {
        return true;
    }
    if (object == null || getClass() != object.getClass()) {
        return false;
    }
    RecoveryCompleteFragmentArgs that = (RecoveryCompleteFragmentArgs) object;
    if (arguments.containsKey("email") != that.arguments.containsKey("email")) {
      return false;
    }
    if (getEmail() != null ? !getEmail().equals(that.getEmail()) : that.getEmail() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "RecoveryCompleteFragmentArgs{"
        + "email=" + getEmail()
        + "}";
  }

  public static class Builder {
    private final HashMap arguments = new HashMap();

    public Builder(RecoveryCompleteFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    public Builder() {
    }

    @NonNull
    public RecoveryCompleteFragmentArgs build() {
      RecoveryCompleteFragmentArgs result = new RecoveryCompleteFragmentArgs(arguments);
      return result;
    }

    @NonNull
    public Builder setEmail(@NonNull String email) {
      if (email == null) {
        throw new IllegalArgumentException("Argument \"email\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("email", email);
      return this;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getEmail() {
      return (String) arguments.get("email");
    }
  }
}
