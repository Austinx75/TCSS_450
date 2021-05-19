package edu.uw.harmony.UI.Home;

import android.util.Log;

import java.io.Serializable;

public class NotificationItem implements Serializable{

    private final String mSender;
    private final String mMessage;


    public static class Builder {
        private final String mSender;
        private final String mMessage;
        /**
         * Constructs a new Builder.
         *
         * @param theSender The person that sent the message
         * @param theMessage the message from that person
         */
        public Builder(String theSender, String theMessage) {
            this.mSender = theSender;
            this.mMessage = theMessage;
        }

        public NotificationItem build() {
            return new NotificationItem(this);
        }
    }

    /**
     * Sets the variables
     * @param builder
     */
    private NotificationItem(final Builder builder) {
        this.mSender = builder.mSender;
        this.mMessage = builder.mMessage;
    }

    /**
     *
     * @return the sender
     */
    public String getSender(){
        return mSender;
    }

    /**
     *
     * @return the message
     */
    public String getMessage(){
        return mMessage;
    }

}


