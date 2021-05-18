package edu.uw.harmony.UI.Contacts;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.NavArgs;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class ContactFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private ContactFragmentArgs() {
  }

  private ContactFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static ContactFragmentArgs fromBundle(@NonNull Bundle bundle) {
    ContactFragmentArgs __result = new ContactFragmentArgs();
    bundle.setClassLoader(ContactFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("username")) {
      String username;
      username = bundle.getString("username");
      if (username == null) {
        throw new IllegalArgumentException("Argument \"username\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("username", username);
    } else {
      throw new IllegalArgumentException("Required argument \"username\" is missing and does not have an android:defaultValue");
    }
    if (bundle.containsKey("status")) {
      String status;
      status = bundle.getString("status");
      if (status == null) {
        throw new IllegalArgumentException("Argument \"status\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("status", status);
    } else {
      throw new IllegalArgumentException("Required argument \"status\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getUsername() {
    return (String) arguments.get("username");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getStatus() {
    return (String) arguments.get("status");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("username")) {
      String username = (String) arguments.get("username");
      __result.putString("username", username);
    }
    if (arguments.containsKey("status")) {
      String status = (String) arguments.get("status");
      __result.putString("status", status);
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
    ContactFragmentArgs that = (ContactFragmentArgs) object;
    if (arguments.containsKey("username") != that.arguments.containsKey("username")) {
      return false;
    }
    if (getUsername() != null ? !getUsername().equals(that.getUsername()) : that.getUsername() != null) {
      return false;
    }
    if (arguments.containsKey("status") != that.arguments.containsKey("status")) {
      return false;
    }
    if (getStatus() != null ? !getStatus().equals(that.getStatus()) : that.getStatus() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (getUsername() != null ? getUsername().hashCode() : 0);
    result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "ContactFragmentArgs{"
        + "username=" + getUsername()
        + ", status=" + getStatus()
        + "}";
  }

  public static class Builder {
    private final HashMap arguments = new HashMap();

    public Builder(ContactFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    public Builder(@NonNull String username, @NonNull String status) {
      if (username == null) {
        throw new IllegalArgumentException("Argument \"username\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("username", username);
      if (status == null) {
        throw new IllegalArgumentException("Argument \"status\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("status", status);
    }

    @NonNull
    public ContactFragmentArgs build() {
      ContactFragmentArgs result = new ContactFragmentArgs(arguments);
      return result;
    }

    @NonNull
    public Builder setUsername(@NonNull String username) {
      if (username == null) {
        throw new IllegalArgumentException("Argument \"username\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("username", username);
      return this;
    }

    @NonNull
    public Builder setStatus(@NonNull String status) {
      if (status == null) {
        throw new IllegalArgumentException("Argument \"status\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("status", status);
      return this;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getUsername() {
      return (String) arguments.get("username");
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getStatus() {
      return (String) arguments.get("status");
    }
  }
}
