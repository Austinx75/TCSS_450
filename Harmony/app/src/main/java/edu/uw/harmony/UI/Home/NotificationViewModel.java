package edu.uw.harmony.UI.Home;

import android.app.Application;
import android.text.format.DateFormat;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.uw.harmony.UI.Chat.message.ChatMessage;

/**
 * @author Austin Scott
 * @version 1.3
 * This stores the notifications. It makes use of method overloading, and adds
 * built notifications to a list that is then sent over to the recycler view adapter.
 */
public class NotificationViewModel extends AndroidViewModel{

    /** This wil store the list of notifications*/
    public MutableLiveData<List<NotificationItem>> mNotifications;

    /**
     * instantiates the mNotifications
     * @param application
     */
    public NotificationViewModel(@NonNull Application application){
        super(application);
        mNotifications = new MutableLiveData<>();
        mNotifications.setValue(new ArrayList<>());
    }

    /**
     * adds the observer
     * @param owner
     * @param observer
     */
    public void addNotificationObserver(@NonNull LifecycleOwner owner,
                                                  @NonNull Observer<? super List<NotificationItem>> observer) {
        mNotifications.observe(owner, observer);
    }

    /**
     * This adds a notificatino to the list. It is for messages sent in foreground
     * @param theMessage
     * @param theDate
     */
    public void addNotification(ChatMessage theMessage, String theDate){
        mNotifications.getValue().add(new NotificationItem.Builder(theMessage.getSender(), theMessage.getMessage(), theDate).build());
        mNotifications.setValue(mNotifications.getValue());
    }

    /**
     * This adds notifications to list. This is for background notifications, or new chat
     * @param theSender
     * @param theMessage
     * @param theTime
     */
    public void addNotification(String theSender, String theMessage, String theTime){
        mNotifications.getValue().add(new NotificationItem.Builder(theSender, theMessage, theTime).build());
        mNotifications.setValue(mNotifications.getValue());
    }


    /**
     *
     * @return list
     */
    public List<NotificationItem> getNotifications(){
        return mNotifications.getValue();
    }

    /**
     * Clears the list
     */
    public void clearNotifications(){
        mNotifications.getValue().clear();
    }

}
