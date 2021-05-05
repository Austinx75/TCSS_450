package edu.uw.harmony.UI.Contacts;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
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
}
