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
    /** The chat id*/
    private final int mChatId;
    /** The ids for the members */
    private List<Integer> mMemberIds;
    /** The most recent message in the chat room*/
    private final String mRecentMessage;
    /** Title for chat*/
    private  String mTitle;


    /**
     * Helper class for building Credentials.
     *
     * @author Charles Bryan
     */
    public static class Builder {
        /** Chat id*/
        private final int mChatId;
        /** List of members in chat*/
        private List<Integer> mMemberIds;
        /** The chat title*/
        private  String mTitle;
        /** The most recent message within the chat*/
        private  String mRecentMessage = "";


        /**
         * Constructs a new Builder.
         *
         * @param Id the chat id
         * @param members all member ids for members in the chatroom
         */
        public Builder(int Id, List<Integer> members, String title, String recentMessage) {
            this.mChatId = Id;
            this.mMemberIds = members;
            this.mTitle = title;
            mRecentMessage = recentMessage;
        }


        public ChatPost build() {
            return new ChatPost(this);
        }

    }

    /**
     * Constructor for the ChatPost
     * @param builder the builder
     */
    private ChatPost(final Builder builder) {
        this.mChatId = builder.mChatId;
        this.mMemberIds = builder.mMemberIds;
        this.mRecentMessage = builder.mRecentMessage;
        this.mTitle=builder.mTitle;
    }

    /**
     * Gets the chat id of the post
     * @return the chat id
     */
    public int getChatId() {
        return mChatId;
    }

    /**
     * Gets the title of the chat
     * @return the title of the chat
     */
    public String getTitle() {return mTitle;}

    /**
     * Gets the most recent message within the chat
     * @return the most recent message
     */
    public String getRecentMessage() {return mRecentMessage;}
}
