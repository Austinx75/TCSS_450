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


public class NotificationViewModel extends AndroidViewModel{

    public MutableLiveData<List<NotificationItem>> mNotifications;

    public NotificationViewModel(@NonNull Application application){
        super(application);
        mNotifications = new MutableLiveData<>();
        mNotifications.setValue(new ArrayList<>());
    }

    public void addNotificationObserver(@NonNull LifecycleOwner owner,
                                                  @NonNull Observer<? super List<NotificationItem>> observer) {
        mNotifications.observe(owner, observer);
    }

    public void addNotification(ChatMessage theMessage, String theDate){
        mNotifications.getValue().add(new NotificationItem.Builder(theMessage.getSender(), theMessage.getMessage(), theDate).build());
        mNotifications.setValue(mNotifications.getValue());
    }

    public void addNotification(String theSender, String theMessage, String theTime){
        mNotifications.getValue().add(new NotificationItem.Builder(theSender, theMessage, theTime).build());
        mNotifications.setValue(mNotifications.getValue());
    }



    public List<NotificationItem> getNotifications(){
        return mNotifications.getValue();
    }

    public void clearNotifications(){
        mNotifications.getValue().clear();
    }

}
