package edu.uw.harmony.UI.Chat.page;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import edu.uw.harmony.R;

public class ChatsFragmentDirections {
  private ChatsFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionNavigationChatsToNewChatFragment() {
    return new ActionOnlyNavDirections(R.id.action_navigation_chats_to_newChatFragment);
  }
}
