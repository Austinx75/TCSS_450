package edu.uw.harmony.UI.Home;

import java.util.Arrays;
import java.util.List;

public class NotificationGenerator {
    private static final NotificationItem[] NOTIFICATION_ITEMS;
    public static final int COUNT = 5;
    public static String[] names = {"Larry", "Austin", "Hermie", "Gary", "Jack"};
    public static String[] messages = {"I am here", "We need to finish sprint 2!",
            "How are you?", "Where are you?", "Just finished Sprint 2"};

    static {
        NOTIFICATION_ITEMS = new NotificationItem[COUNT];
        for(int i = 0; i < NOTIFICATION_ITEMS.length; i++){
            NOTIFICATION_ITEMS[i] = new NotificationItem.Builder(names[i],messages[i]).build();
        }
    }

    public static List<NotificationItem> getNotificationList(){
        return Arrays.asList(NOTIFICATION_ITEMS);
    }

    public static NotificationItem[] getNotification(){
        return Arrays.copyOf(NOTIFICATION_ITEMS, NOTIFICATION_ITEMS.length);
    }

    private NotificationGenerator(){ }
}
