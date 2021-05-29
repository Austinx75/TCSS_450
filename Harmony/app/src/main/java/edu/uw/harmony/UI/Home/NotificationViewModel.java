package edu.uw.harmony.UI.Home;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;

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

    public void addNotification(ChatMessage theMessage){
        mNotifications.getValue().add(new NotificationItem.Builder(theMessage.getSender(), theMessage.getMessage()).build());
        mNotifications.setValue(mNotifications.getValue());
        //Log.d("Notifications", mNotifications.getValue().get(0).getMessage());
    }

    public void addNotification(String theSender, String theMessage){
        mNotifications.getValue().add(new NotificationItem.Builder(theSender, theMessage).build());
        mNotifications.setValue(mNotifications.getValue());
        //Log.d("Notifications", mNotifications.getValue().get(0).getMessage());
    }

    public List<NotificationItem> getNotifications(){
        if(mNotifications.getValue().size() == 0){

        } else {
            Log.d("Notifications", mNotifications.getValue().get(0).getMessage());
        }
        return mNotifications.getValue();
    }

    public void clearNotifications(){
        mNotifications.getValue().clear();
    }

}
