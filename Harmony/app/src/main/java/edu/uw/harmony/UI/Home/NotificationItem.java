package edu.uw.harmony.UI.Home;

import android.util.Log;

import java.io.Serializable;

/**
 * This builds the strings that are sent over from the Generator class.
 * @author Austin Scott
 * @version 1.1
 */
public class NotificationItem implements Serializable{

    /** This is the sender attribute of the notification*/
    private final String mSender;
    /** This is the message attribute of the notification*/
    private final String mMessage;
    /** String for the time stamp*/
    private final String time;


    public static class Builder {
        /** String for the sender information*/
        private final String mSender;
        /** String for the message information*/
        private final String mMessage;
        /** String for the time stamp*/
        private final String time;

        /**
         * Constructs a new Builder.
         *
         * @param theSender The person that sent the message
         * @param theMessage the message from that person
         */
        public Builder(String theSender, String theMessage, String theTime) {
            this.mSender = theSender;
            this.mMessage = theMessage;
            this.time = theTime;
        }

        /**
         *
         * @return method call of Notificaion Item and sending in this instance
         */
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
        this.time = builder.time;
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

    /**
     *
     * @return
     */
    public String getTime(){
        return time;
    }

}


