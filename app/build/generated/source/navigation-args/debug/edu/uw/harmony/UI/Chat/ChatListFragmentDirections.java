package edu.uw.harmony.UI.Chat;

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

public class ChatListFragmentDirections {
  private ChatListFragmentDirections() {
  }

  @NonNull
  public static ActionChatListFragmentToChatPostFragment actionChatListFragmentToChatPostFragment(
      @NonNull ChatPost chat) {
    return new ActionChatListFragmentToChatPostFragment(chat);
  }

  public static class ActionChatListFragmentToChatPostFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    private ActionChatListFragmentToChatPostFragment(@NonNull ChatPost chat) {
      if (chat == null) {
        throw new IllegalArgumentException("Argument \"chat\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("chat", chat);
    }

    @NonNull
    public ActionChatListFragmentToChatPostFragment setChat(@NonNull ChatPost chat) {
      if (chat == null) {
        throw new IllegalArgumentException("Argument \"chat\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("chat", chat);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
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
    public int getActionId() {
      return R.id.action_chatListFragment_to_chatPostFragment;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public ChatPost getChat() {
      return (ChatPost) arguments.get("chat");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionChatListFragmentToChatPostFragment that = (ActionChatListFragmentToChatPostFragment) object;
      if (arguments.containsKey("chat") != that.arguments.containsKey("chat")) {
        return false;
      }
      if (getChat() != null ? !getChat().equals(that.getChat()) : that.getChat() != null) {
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
      result = 31 * result + (getChat() != null ? getChat().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionChatListFragmentToChatPostFragment(actionId=" + getActionId() + "){"
          + "chat=" + getChat()
          + "}";
    }
  }
}
