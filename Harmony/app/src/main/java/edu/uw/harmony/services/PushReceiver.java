package edu.uw.harmony.services;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import org.json.JSONException;

import java.util.Random;

import edu.uw.harmony.AuthActivity;
import edu.uw.harmony.R;
import edu.uw.harmony.UI.Chat.message.ChatMessage;
import edu.uw.harmony.UI.Home.NotificationItem;
import me.pushy.sdk.Pushy;

import static android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
import static android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE;

public class PushReceiver extends BroadcastReceiver {

    public static final String RECEIVED_NEW_MESSAGE = "new message from pushy";

    private static final String CHANNEL_ID = "1";

    public static final String RECEIVED_NEW_CONTACT = "contacts page update from pushy";

    public static final String RECEIVED_NEW_CHAT = "new chat has been created";


    @Override
    /**
     * Handles notification types.
     * @version 1.3
     * @author Austin Scott
     * @param context the context of the message.
     * @param intent the intent of the message.
     */
    public void onReceive(Context context, Intent intent) {
        String typeOfMessage = intent.getStringExtra("type");
        if (typeOfMessage.equals("contacts")) {
            handleContactsNotification(context, intent);
        }
        if (typeOfMessage.equals("msg")) {
            handleChatNotification(context, intent);
        }
        if(typeOfMessage.equals("chat")){
            handleNewChatNotifications(context, intent);
        }
    }

    /**
     * Handles new messages.
     * @version 1.3
     * @param context the context of the message.
     * @param intent the intent of the message.
     */
    public void handleChatNotification(Context context, Intent intent) {
        //the following variables are used to store the information sent from Pushy
        //In the WS, you define what gets sent. You can change it there to suit your needs
        //Then here on the Android side, decide what to do with the message you got

        //for the lab, the WS is only sending chat messages so the type will always be msg
        //for your project, the WS needs to send different types of push messages.
        //So perform logic/routing based on the "type"
        //feel free to change the key or type of values.
        String typeOfMessage = intent.getStringExtra("type");
        ChatMessage message = null;
        int chatId = -1;
        try{
            message = ChatMessage.createFromJsonString(intent.getStringExtra("message"));
            chatId = intent.getIntExtra("chatid", -1);
        } catch (JSONException e) {
            //Web service sent us something unexpected...I can't deal with this.
            throw new IllegalStateException("Error from Web Service. Contact Dev Support");
        }

        ActivityManager.RunningAppProcessInfo appProcessInfo = new ActivityManager.RunningAppProcessInfo();
        ActivityManager.getMyMemoryState(appProcessInfo);

        if (appProcessInfo.importance == IMPORTANCE_FOREGROUND || appProcessInfo.importance == IMPORTANCE_VISIBLE) {
            //app is in the foreground so send the message to the active Activities
            Log.d("PUSHY", "Message received in foreground: " + message);

            //create an Intent to broadcast a message to other parts of the app.
            Intent i = new Intent(RECEIVED_NEW_MESSAGE);
            i.putExtra("chatMessage", message);
            i.putExtra("chatid", chatId);
            i.putExtras(intent.getExtras());

            context.sendBroadcast(i);
        } else {
            //app is in the background so create and post a notification
            Log.d("PUSHY", "Message received in background: " + message.getMessage());

            Intent i = new Intent(context, AuthActivity.class);
            i.putExtras(intent.getExtras());

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                    i, PendingIntent.FLAG_UPDATE_CURRENT);

            //research more on notifications the how to display them
            //https://developer.android.com/guide/topics/ui/notifiers/notifications

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.chat_notification_24dp)
                    .setContentTitle("Message from: " + message.getSender())
                    .setContentText(message.getMessage())
                    .setSubText(Integer.toString(message.getChatId()))
                    .setContentInfo(intent.getStringExtra("type"))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent);


            // Automatically configure a ChatMessageNotification Channel for devices running Android O+
            Pushy.setNotificationChannel(builder, context);

            // Get an instance of the NotificationManager service
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

            Random random = new Random();
            int n = random.nextInt(300);
            // Build the notification and display it
            notificationManager.notify(n, builder.build());
            Log.d("Pushy", notificationManager.getActiveNotifications().toString());
//            notificationManager.getActiveNotifications().;

        }

    }

    /**
     * Handles new contact notifications.
     * @version 1.3
     * @author Austin Scott
     * @param context the context of the message.
     * @param intent the intent of the message.
     */
    private void handleContactsNotification(Context context, Intent intent) {
        Log.d("Contact", "This end point works");
        String contact="", message="";
        Log.d("Contacts", "made it to push reciever");

        try{
             contact = intent.getStringExtra("contactId");
             message = intent.getStringExtra("message");

        } catch (Exception e){
            Log.e("Contact Update Error", "Failed to retrieve contact information");
        }

        ActivityManager.RunningAppProcessInfo appProcessInfo = new ActivityManager.RunningAppProcessInfo();
        ActivityManager.getMyMemoryState(appProcessInfo);

        if (appProcessInfo.importance == IMPORTANCE_FOREGROUND || appProcessInfo.importance == IMPORTANCE_VISIBLE) {
            //app is in the foreground so send the message to the active Activities
            Log.d("PUSHY", "Contacts notification recieved in foreground: ");

            //create an Intent to broadcast a message to other parts of the app.
            Intent i = new Intent(RECEIVED_NEW_CONTACT);
            i.putExtra("contactId", contact);
            i.putExtra("message",message);
            i.putExtras(intent.getExtras());
            Log.d("PUSHY", "Contact Request Sender " + i.getStringExtra("contactId").toString());
            context.sendBroadcast(i);
        } else {
            //app is in the background so create and post a notification
            Log.d("PUSHY", "Contacts info updated in background");

            Intent i = new Intent(context, AuthActivity.class);
            i.putExtra("contactId", intent.getStringExtra("contactId"));
            i.putExtras(intent.getExtras());

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                    i, PendingIntent.FLAG_UPDATE_CURRENT);

            //research more on notifications the how to display them
            //https://developer.android.com/guide/topics/ui/notifiers/notifications
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.contact_black_24dp)
                    .setContentTitle(i.getStringExtra("contactId"))
                    .setContentText(i.getStringExtra("message"))
                    .setContentInfo(intent.getStringExtra("type"))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setGroup("Harmony Notifications");

            // Automatically configure a ChatMessageNotification Channel for devices running Android O+
            Pushy.setNotificationChannel(builder, context);

            // Get an instance of the NotificationManager service
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
            // Build the notification and display it
            Random random = new Random();
            int n = random.nextInt(300);
            notificationManager.notify(n, builder.build());

        }
    }

    /**
     * Handles new chats.
     * @version 1.3
     * @author Austin Scott
     * @param context the context of the message.
     * @param intent the intent of the message.
     */
    private void handleNewChatNotifications(Context context, Intent intent) {

        ActivityManager.RunningAppProcessInfo appProcessInfo = new ActivityManager.RunningAppProcessInfo();
        ActivityManager.getMyMemoryState(appProcessInfo);
        String message;
        String member;
        int chatid;
        try{
            message = intent.getStringExtra("message");
            member = intent.getStringExtra("member");
            chatid = intent.getIntExtra("chatid", -1);
            Log.d("Message", intent.getStringExtra("message"));
        } catch(Exception e){
            throw new IllegalStateException("Error from Web Service. Contact Dev Support");
        }

        if (appProcessInfo.importance == IMPORTANCE_FOREGROUND || appProcessInfo.importance == IMPORTANCE_VISIBLE) {
            //app is in the foreground so send the message to the active Activities
            Log.d("PUSHY_NEW_CHAT", "New Chat notification recieved in foreground: ");

            //create an Intent to broadcast a message to other parts of the app.
            Intent i = new Intent(RECEIVED_NEW_CHAT);
            i.putExtra("newChat", message);
            i.putExtra("member", member);
            i.putExtra("chatid", chatid);
            i.putExtras(intent.getExtras());
            Log.d("intent", i.getStringExtra("newChat"));
            context.sendBroadcast(i);

        } else {
            Log.d("PUSHY", "Message received in background: " + message);

            Intent i = new Intent(context, AuthActivity.class);
            i.putExtras(intent.getExtras());

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                    i, PendingIntent.FLAG_UPDATE_CURRENT);
            //Log.d("PUSHY", intent.getIntExtra("chatid", -1));

            //research more on notifications the how to display them
            //https://developer.android.com/guide/topics/ui/notifiers/notifications
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.chat_notification_24dp)
                    .setContentTitle(intent.getStringExtra("member"))
                    .setContentInfo(intent.getStringExtra("type"))
                    .setContentText(message)
                    .setSubText(Integer.toString(intent.getIntExtra("chatid", -1)))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent);


            // Automatically configure a ChatMessageNotification Channel for devices running Android O+
            Pushy.setNotificationChannel(builder, context);

            // Get an instance of the NotificationManager service
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

            Random random = new Random();
            int n = random.nextInt(300);
            // Build the notification and display it
            notificationManager.notify(n, builder.build());
            Log.d("Pushy", notificationManager.getActiveNotifications().toString());
        }
    }


}
