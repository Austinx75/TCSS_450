package edu.uw.harmony;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.NavArgs;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class MainActivityArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private MainActivityArgs() {
  }

  private MainActivityArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static MainActivityArgs fromBundle(@NonNull Bundle bundle) {
    MainActivityArgs __result = new MainActivityArgs();
    bundle.setClassLoader(MainActivityArgs.class.getClassLoader());
    if (bundle.containsKey("jwt")) {
      String jwt;
      jwt = bundle.getString("jwt");
      if (jwt == null) {
        throw new IllegalArgumentException("Argument \"jwt\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("jwt", jwt);
    } else {
      throw new IllegalArgumentException("Required argument \"jwt\" is missing and does not have an android:defaultValue");
    }
    if (bundle.containsKey("email")) {
      String email;
      email = bundle.getString("email");
      if (email == null) {
        throw new IllegalArgumentException("Argument \"email\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("email", email);
    } else {
      throw new IllegalArgumentException("Required argument \"email\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getJwt() {
    return (String) arguments.get("jwt");
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
    if (arguments.containsKey("jwt")) {
      String jwt = (String) arguments.get("jwt");
      __result.putString("jwt", jwt);
    }
    if (arguments.containsKey("email")) {
      String email = (String) arguments.get("email");
      __result.putString("email", email);
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
    MainActivityArgs that = (MainActivityArgs) object;
    if (arguments.containsKey("jwt") != that.arguments.containsKey("jwt")) {
      return false;
    }
    if (getJwt() != null ? !getJwt().equals(that.getJwt()) : that.getJwt() != null) {
      return false;
    }
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
    result = 31 * result + (getJwt() != null ? getJwt().hashCode() : 0);
    result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "MainActivityArgs{"
        + "jwt=" + getJwt()
        + ", email=" + getEmail()
        + "}";
  }

  public static class Builder {
    private final HashMap arguments = new HashMap();

    public Builder(MainActivityArgs original) {
      this.arguments.putAll(original.arguments);
    }

    public Builder(@NonNull String jwt, @NonNull String email) {
      if (jwt == null) {
        throw new IllegalArgumentException("Argument \"jwt\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("jwt", jwt);
      if (email == null) {
        throw new IllegalArgumentException("Argument \"email\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("email", email);
    }

    @NonNull
    public MainActivityArgs build() {
      MainActivityArgs result = new MainActivityArgs(arguments);
      return result;
    }

    @NonNull
    public Builder setJwt(@NonNull String jwt) {
      if (jwt == null) {
        throw new IllegalArgumentException("Argument \"jwt\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("jwt", jwt);
      return this;
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
    public String getJwt() {
      return (String) arguments.get("jwt");
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getEmail() {
      return (String) arguments.get("email");
    }
  }
}
