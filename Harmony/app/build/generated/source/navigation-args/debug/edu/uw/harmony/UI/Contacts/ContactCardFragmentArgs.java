package edu.uw.harmony.UI.Contacts;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.navigation.NavArgs;
import java.io.Serializable;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class ContactCardFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private ContactCardFragmentArgs() {
  }

  private ContactCardFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static ContactCardFragmentArgs fromBundle(@NonNull Bundle bundle) {
    ContactCardFragmentArgs __result = new ContactCardFragmentArgs();
    bundle.setClassLoader(ContactCardFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("contact")) {
      ContactCard contact;
      if (Parcelable.class.isAssignableFrom(ContactCard.class) || Serializable.class.isAssignableFrom(ContactCard.class)) {
        contact = (ContactCard) bundle.get("contact");
      } else {
        throw new UnsupportedOperationException(ContactCard.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
      }
      if (contact == null) {
        throw new IllegalArgumentException("Argument \"contact\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("contact", contact);
    } else {
      throw new IllegalArgumentException("Required argument \"contact\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public ContactCard getContact() {
    return (ContactCard) arguments.get("contact");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
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
  public boolean equals(Object object) {
    if (this == object) {
        return true;
    }
    if (object == null || getClass() != object.getClass()) {
        return false;
    }
    ContactCardFragmentArgs that = (ContactCardFragmentArgs) object;
    if (arguments.containsKey("contact") != that.arguments.containsKey("contact")) {
      return false;
    }
    if (getContact() != null ? !getContact().equals(that.getContact()) : that.getContact() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (getContact() != null ? getContact().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "ContactCardFragmentArgs{"
        + "contact=" + getContact()
        + "}";
  }

  public static class Builder {
    private final HashMap arguments = new HashMap();

    public Builder(ContactCardFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    public Builder(@NonNull ContactCard contact) {
      if (contact == null) {
        throw new IllegalArgumentException("Argument \"contact\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("contact", contact);
    }

    @NonNull
    public ContactCardFragmentArgs build() {
      ContactCardFragmentArgs result = new ContactCardFragmentArgs(arguments);
      return result;
    }

    @NonNull
    public Builder setContact(@NonNull ContactCard contact) {
      if (contact == null) {
        throw new IllegalArgumentException("Argument \"contact\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("contact", contact);
      return this;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public ContactCard getContact() {
      return (ContactCard) arguments.get("contact");
    }
  }
}
