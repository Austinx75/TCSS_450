package edu.uw.harmony.UI.Chat.page;

import java.io.Serializable;
import java.util.List;

/**
 * Class to encapsulate a Phish.net Blog Post. Building an Object requires a publish date and title.
 *
 * Optional fields include URL, teaser, and Author.
 *
 *
 * @author Charles Bryan
 * @version 14 September 2018
 */
public class ChatPost implements Serializable {

    private final int mChatId;
    private List<Integer> mMemberIds;
    private final String mRecentMessage;
    private  String mTitle;


    /**
     * Helper class for building Credentials.
     *
     * @author Charles Bryan
     */
    public static class Builder {
        private final int mChatId;
        private List<Integer> mMemberIds;
        private  String mTitle;
        private  String mRecentMessage = "";


        /**
         * Constructs a new Builder.
         *
         * @param Id the chat id
         * @param members all member ids for members in the chatroom
         */
        public Builder(int Id, List<Integer> members, String title) {
            this.mChatId = Id;
            this.mMemberIds = members;
            this.mTitle = title;
        }

        /**
         * Add the most recent chat message to preview
         * @param val the most recent chat message
         * @return the Builder of this chat
         */
        public Builder addRecentMessage(final String val) {
            mRecentMessage = val;
            return this;
        }

        public ChatPost build() {
            return new ChatPost(this);
        }

    }

    private ChatPost(final Builder builder) {
        this.mChatId = builder.mChatId;
        this.mMemberIds = builder.mMemberIds;
        this.mRecentMessage = builder.mRecentMessage;
        this.mTitle=builder.mTitle;
    }

    public int getChatId() {
        return mChatId;
    }

    public List<Integer> getMembers() {
        return mMemberIds;
    }

    public String getRecentMessage() {
        return mRecentMessage;
    }

    public String getTitle() {return mTitle;}


}
