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

/**
 * This class is used to create intents that can be broadcasted in the foreground and background of a application.
 * It broadcasts notifications for new contacts, new chats, and new messages.
 */
public class PushReceiver extends BroadcastReceiver {
    /** This is an intent filter string used in main activity for new message*/
    public static final String RECEIVED_NEW_MESSAGE = "new message from pushy";
    /** This is an intent filter string used in main activity*/
    private static final String CHANNEL_ID = "1";
    /** This is an intent filter string used in main activity for new contact*/
    public static final String RECEIVED_NEW_CONTACT = "contacts page update from pushy";
    /** This is an intent filter string used in main activity for new chat*/
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
     * Handles new messages, either foreground or background
     * @version 1.3
     * @param context the context of the message.
     * @param intent the intent of the message.
     */
    public void handleChatNotification(Context context, Intent intent) {
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
            Intent i = new Intent(RECEIVED_NEW_MESSAGE);
            i.putExtra("chatMessage", message);
            i.putExtra("chatid", chatId);
            i.putExtras(intent.getExtras());
            context.sendBroadcast(i);
        } else {
            Intent i = new Intent(context, AuthActivity.class);
            i.putExtras(intent.getExtras());
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                    i, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.chat_notification_24dp)
                    .setContentTitle("Message from: " + message.getSender())
                    .setContentText(message.getMessage())
                    .setSubText(Integer.toString(message.getChatId()))
                    .setContentInfo(intent.getStringExtra("type"))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent);
            Pushy.setNotificationChannel(builder, context);
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
            Random random = new Random();
            int n = random.nextInt(300);
            notificationManager.notify(n, builder.build());

        }

    }

    /**
     * Handles new contact notifications, either foreground or background
     * @version 1.3
     * @author Austin Scott
     * @param context the context of the message.
     * @param intent the intent of the message.
     */
    private void handleContactsNotification(Context context, Intent intent) {
        String contact="", message="";
        try{
             contact = intent.getStringExtra("contactId");
             message = intent.getStringExtra("message");

        } catch (Exception e){
            Log.e("Contact Update Error", "Failed to retrieve contact information");
        }

        ActivityManager.RunningAppProcessInfo appProcessInfo = new ActivityManager.RunningAppProcessInfo();
        ActivityManager.getMyMemoryState(appProcessInfo);

        if (appProcessInfo.importance == IMPORTANCE_FOREGROUND || appProcessInfo.importance == IMPORTANCE_VISIBLE) {
            Intent i = new Intent(RECEIVED_NEW_CONTACT);
            i.putExtra("contactId", contact);
            i.putExtra("message",message);
            i.putExtras(intent.getExtras());
            context.sendBroadcast(i);
        } else {
            Intent i = new Intent(context, AuthActivity.class);
            i.putExtra("contactId", intent.getStringExtra("contactId"));
            i.putExtras(intent.getExtras());
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                    i, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.contact_black_24dp)
                    .setContentTitle(i.getStringExtra("contactId"))
                    .setContentText(i.getStringExtra("message"))
                    .setContentInfo(intent.getStringExtra("type"))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setGroup("Harmony Notifications");
            Pushy.setNotificationChannel(builder, context);
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
            Random random = new Random();
            int n = random.nextInt(300);
            notificationManager.notify(n, builder.build());

        }
    }

    /**
     * Handles new chats, either foreground or background
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
        } catch(Exception e){
            throw new IllegalStateException("Error from Web Service. Contact Dev Support");
        }

        if (appProcessInfo.importance == IMPORTANCE_FOREGROUND || appProcessInfo.importance == IMPORTANCE_VISIBLE) {
            Intent i = new Intent(RECEIVED_NEW_CHAT);
            i.putExtra("newChat", message);
            i.putExtra("member", member);
            i.putExtra("chatid", chatid);
            i.putExtras(intent.getExtras());
            context.sendBroadcast(i);
        } else {
            Intent i = new Intent(context, AuthActivity.class);
            i.putExtras(intent.getExtras());

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                    i, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.chat_notification_24dp)
                    .setContentTitle(intent.getStringExtra("member"))
                    .setContentInfo(intent.getStringExtra("type"))
                    .setContentText(message)
                    .setSubText(Integer.toString(intent.getIntExtra("chatid", -1)))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent);
            Pushy.setNotificationChannel(builder, context);
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
            Random random = new Random();
            int n = random.nextInt(300);
            notificationManager.notify(n, builder.build());
            Log.d("Pushy", notificationManager.getActiveNotifications().toString());
        }
    }


}
