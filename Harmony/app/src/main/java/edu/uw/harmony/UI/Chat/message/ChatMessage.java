package edu.uw.harmony.UI.Chat.message;

import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Encapsulate chat message details.
 */
public final class ChatMessage implements Serializable {

    /** The message id*/
    private final int mMessageId;
    /** The message*/
    private final String mMessage;
    /** The sender*/
    private final String mSender;
    /** The timestamp that the message was sent*/
    private final String mTimeStamp;
    /** The chat id the message belongs to*/
    private final int mChatId;

    /**
     * Chat Message Constructor
     * @param messageId message id
     * @param message the message
     * @param sender the sender's email
     * @param timeStamp the time that it was sent
     * @param chatId the chat it belongs to base on id
     */
    public ChatMessage(int messageId, String message, String sender, String timeStamp, int chatId) {
        mMessageId = messageId;
        mMessage = message;
        mSender = sender;
        mTimeStamp = timeStamp;
        mChatId = chatId;
    }

    /**
     * Static factory method to turn a properly formatted JSON String into a
     * ChatMessage object.
     * @param cmAsJson the String to be parsed into a ChatMessage Object.
     * @return a ChatMessage Object with the details contained in the JSON String.
     * @throws JSONException when cmAsString cannot be parsed into a ChatMessage.
     */
    public static ChatMessage createFromJsonString(final String cmAsJson) throws JSONException {
        final JSONObject msg = new JSONObject(cmAsJson);
        return new ChatMessage(msg.getInt("messageid"),
                msg.getString("message"),
                msg.getString("email"),
                msg.getString("timestamp"),
                msg.getInt("chatid"));
    }

    /**
     * Gets the message
     * @return the message
     */
    public String getMessage() {
        return mMessage;
    }

    /**
     * Gets the senders email
     * @return the email of the sender
     */
    public String getSender() {
        return mSender;
    }

    /**
     * Gets the timestamp it was sent
     * @return the timestamp
     */
    public String getTimeStamp() {
        return mTimeStamp;
    }

    /**
     * Gets the message id
     * @return the message id
     */
    public int getMessageId() {
        return mMessageId;
    }

    /**
     * Gets the chat the message belongs to
     * @return the id of the chat the message is in
     */
    public int getChatId() {return mChatId;}

    /**
     * Provides equality solely based on MessageId.
     * @param other the other object to check for equality
     * @return true if other message ID matches this message ID, false otherwise
     */
    @Override
    public boolean equals(@Nullable Object other) {
        boolean result = false;
        if (other instanceof ChatMessage) {
            result = mMessageId == ((ChatMessage) other).mMessageId;
        }
        return result;
    }
}
