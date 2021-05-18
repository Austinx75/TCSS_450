package edu.uw.harmony.UI.Chat.message;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.NavArgs;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class ChatRoomFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private ChatRoomFragmentArgs() {
  }

  private ChatRoomFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static ChatRoomFragmentArgs fromBundle(@NonNull Bundle bundle) {
    ChatRoomFragmentArgs __result = new ChatRoomFragmentArgs();
    bundle.setClassLoader(ChatRoomFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("chatId")) {
      int chatId;
      chatId = bundle.getInt("chatId");
      __result.arguments.put("chatId", chatId);
    } else {
      throw new IllegalArgumentException("Required argument \"chatId\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  public int getChatId() {
    return (int) arguments.get("chatId");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("chatId")) {
      int chatId = (int) arguments.get("chatId");
      __result.putInt("chatId", chatId);
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
    ChatRoomFragmentArgs that = (ChatRoomFragmentArgs) object;
    if (arguments.containsKey("chatId") != that.arguments.containsKey("chatId")) {
      return false;
    }
    if (getChatId() != that.getChatId()) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + getChatId();
    return result;
  }

  @Override
  public String toString() {
    return "ChatRoomFragmentArgs{"
        + "chatId=" + getChatId()
        + "}";
  }

  public static class Builder {
    private final HashMap arguments = new HashMap();

    public Builder(ChatRoomFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    public Builder(int chatId) {
      this.arguments.put("chatId", chatId);
    }

    @NonNull
    public ChatRoomFragmentArgs build() {
      ChatRoomFragmentArgs result = new ChatRoomFragmentArgs(arguments);
      return result;
    }

    @NonNull
    public Builder setChatId(int chatId) {
      this.arguments.put("chatId", chatId);
      return this;
    }

    @SuppressWarnings("unchecked")
    public int getChatId() {
      return (int) arguments.get("chatId");
    }
  }
}
