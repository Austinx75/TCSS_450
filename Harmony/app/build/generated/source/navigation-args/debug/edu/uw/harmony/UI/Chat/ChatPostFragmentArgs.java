package edu.uw.harmony.UI.Chat;

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

public class ChatPostFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private ChatPostFragmentArgs() {
  }

  private ChatPostFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static ChatPostFragmentArgs fromBundle(@NonNull Bundle bundle) {
    ChatPostFragmentArgs __result = new ChatPostFragmentArgs();
    bundle.setClassLoader(ChatPostFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("chat")) {
      ChatPost chat;
      if (Parcelable.class.isAssignableFrom(ChatPost.class) || Serializable.class.isAssignableFrom(ChatPost.class)) {
        chat = (ChatPost) bundle.get("chat");
      } else {
        throw new UnsupportedOperationException(ChatPost.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
      }
      if (chat == null) {
        throw new IllegalArgumentException("Argument \"chat\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("chat", chat);
    } else {
      throw new IllegalArgumentException("Required argument \"chat\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public ChatPost getChat() {
    return (ChatPost) arguments.get("chat");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("chat")) {
      ChatPost chat = (ChatPost) arguments.get("chat");
      if (Parcelable.class.isAssignableFrom(ChatPost.class) || chat == null) {
        __result.putParcelable("chat", Parcelable.class.cast(chat));
      } else if (Serializable.class.isAssignableFrom(ChatPost.class)) {
        __result.putSerializable("chat", Serializable.class.cast(chat));
      } else {
        throw new UnsupportedOperationException(ChatPost.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
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
    ChatPostFragmentArgs that = (ChatPostFragmentArgs) object;
    if (arguments.containsKey("chat") != that.arguments.containsKey("chat")) {
      return false;
    }
    if (getChat() != null ? !getChat().equals(that.getChat()) : that.getChat() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (getChat() != null ? getChat().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "ChatPostFragmentArgs{"
        + "chat=" + getChat()
        + "}";
  }

  public static class Builder {
    private final HashMap arguments = new HashMap();

    public Builder(ChatPostFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    public Builder(@NonNull ChatPost chat) {
      if (chat == null) {
        throw new IllegalArgumentException("Argument \"chat\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("chat", chat);
    }

    @NonNull
    public ChatPostFragmentArgs build() {
      ChatPostFragmentArgs result = new ChatPostFragmentArgs(arguments);
      return result;
    }

    @NonNull
    public Builder setChat(@NonNull ChatPost chat) {
      if (chat == null) {
        throw new IllegalArgumentException("Argument \"chat\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("chat", chat);
      return this;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public ChatPost getChat() {
      return (ChatPost) arguments.get("chat");
    }
  }
}
