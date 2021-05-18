package edu.uw.harmony.UI.Chat.page;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import edu.uw.harmony.R;
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
      int chatId) {
    return new ActionChatListFragmentToChatPostFragment(chatId);
  }

  @NonNull
  public static NavDirections actionNavigationChatListToNavigationNewChat() {
    return new ActionOnlyNavDirections(R.id.action_navigation_chat_list_to_navigation_new_chat);
  }

  public static class ActionChatListFragmentToChatPostFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    private ActionChatListFragmentToChatPostFragment(int chatId) {
      this.arguments.put("chatId", chatId);
    }

    @NonNull
    public ActionChatListFragmentToChatPostFragment setChatId(int chatId) {
      this.arguments.put("chatId", chatId);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("chatId")) {
        int chatId = (int) arguments.get("chatId");
        __result.putInt("chatId", chatId);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_chatListFragment_to_chatPostFragment;
    }

    @SuppressWarnings("unchecked")
    public int getChatId() {
      return (int) arguments.get("chatId");
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
      if (arguments.containsKey("chatId") != that.arguments.containsKey("chatId")) {
        return false;
      }
      if (getChatId() != that.getChatId()) {
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
      result = 31 * result + getChatId();
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionChatListFragmentToChatPostFragment(actionId=" + getActionId() + "){"
          + "chatId=" + getChatId()
          + "}";
    }
  }
}
