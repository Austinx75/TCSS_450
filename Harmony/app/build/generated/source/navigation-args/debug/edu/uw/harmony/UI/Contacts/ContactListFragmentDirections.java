package edu.uw.harmony.UI.Contacts;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import edu.uw.harmony.R;
import java.io.Serializable;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class ContactListFragmentDirections {
  private ContactListFragmentDirections() {
  }

  @NonNull
  public static ActionNavigationContactToContactCardFragment actionNavigationContactToContactCardFragment(
      @NonNull ContactCard contact) {
    return new ActionNavigationContactToContactCardFragment(contact);
  }

  @NonNull
  public static NavDirections actionNavigationContactToNavigationNewChat() {
    return new ActionOnlyNavDirections(R.id.action_navigation_contact_to_navigation_new_chat);
  }

  @NonNull
  public static ActionNavigationContactToContactFragment actionNavigationContactToContactFragment(
      @NonNull String username, @NonNull String status) {
    return new ActionNavigationContactToContactFragment(username, status);
  }

  @NonNull
  public static NavDirections actionNavigationContactToAddNewContactFragment() {
    return new ActionOnlyNavDirections(R.id.action_navigation_contact_to_addNewContactFragment);
  }

  public static class ActionNavigationContactToContactCardFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    private ActionNavigationContactToContactCardFragment(@NonNull ContactCard contact) {
      if (contact == null) {
        throw new IllegalArgumentException("Argument \"contact\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("contact", contact);
    }

    @NonNull
    public ActionNavigationContactToContactCardFragment setContact(@NonNull ContactCard contact) {
      if (contact == null) {
        throw new IllegalArgumentException("Argument \"contact\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("contact", contact);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("contact")) {
        ContactCard contact = (ContactCard) arguments.get("contact");
        if (Parcelable.class.isAssignableFrom(ContactCard.class) || contact == null) {
          __result.putParcelable("contact", Parcelable.class.cast(contact));
        } else if (Serializable.class.isAssignableFrom(ContactCard.class)) {
          __result.putSerializable("contact", Serializable.class.cast(contact));
        } else {
          throw new UnsupportedOperationException(ContactCard.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
        }
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_navigation_contact_to_contactCardFragment;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public ContactCard getContact() {
      return (ContactCard) arguments.get("contact");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionNavigationContactToContactCardFragment that = (ActionNavigationContactToContactCardFragment) object;
      if (arguments.containsKey("contact") != that.arguments.containsKey("contact")) {
        return false;
      }
      if (getContact() != null ? !getContact().equals(that.getContact()) : that.getContact() != null) {
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
      result = 31 * result + (getContact() != null ? getContact().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionNavigationContactToContactCardFragment(actionId=" + getActionId() + "){"
          + "contact=" + getContact()
          + "}";
    }
  }

  public static class ActionNavigationContactToContactFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    private ActionNavigationContactToContactFragment(@NonNull String username,
        @NonNull String status) {
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
    public ActionNavigationContactToContactFragment setUsername(@NonNull String username) {
      if (username == null) {
        throw new IllegalArgumentException("Argument \"username\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("username", username);
      return this;
    }

    @NonNull
    public ActionNavigationContactToContactFragment setStatus(@NonNull String status) {
      if (status == null) {
        throw new IllegalArgumentException("Argument \"status\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("status", status);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
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
    public int getActionId() {
      return R.id.action_navigation_contact_to_contactFragment;
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

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionNavigationContactToContactFragment that = (ActionNavigationContactToContactFragment) object;
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
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + (getUsername() != null ? getUsername().hashCode() : 0);
      result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionNavigationContactToContactFragment(actionId=" + getActionId() + "){"
          + "username=" + getUsername()
          + ", status=" + getStatus()
          + "}";
    }
  }
}
