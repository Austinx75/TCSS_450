package edu.uw.harmony.UI.Home;

import java.util.Arrays;
import java.util.List;

public class NotificationGenerator {
    /** Creats an array for the Notifications*/
    private static final NotificationItem[] NOTIFICATION_ITEMS;
    /** Creates a final count size to instantiate the array.*/
    public static final int COUNT = 5;
    /** Creates an array of strings to generate the To: <names>*/
    public static String[] names = {"Larry", "Austin", "Hermie", "Gary", "Jack"};
    /** Creates an array of mock messages*/
    public static String[] messages = {"I am here", "We need to finish sprint 2!",
            "How are you?", "Where are you?", "Just finished Sprint 2"};

    /** This sends the mock names and messages over to the builder in NotificationItem*/
    static {
        NOTIFICATION_ITEMS = new NotificationItem[COUNT];
        for(int i = 0; i < NOTIFICATION_ITEMS.length; i++){
            NOTIFICATION_ITEMS[i] = new NotificationItem.Builder(names[i],messages[i]).build();
        }
    }

    /**
     * Returns the list of Notifications
     * @return
     */
    public static List<NotificationItem> getNotificationList(){
        return Arrays.asList(NOTIFICATION_ITEMS);
    }

    /**
     *
     * @return a copy of the notifications
     */
    public static NotificationItem[] getNotification(){
        return Arrays.copyOf(NOTIFICATION_ITEMS, NOTIFICATION_ITEMS.length);
    }

    /**
     * Parameterless constructor
     */
    private NotificationGenerator(){ }
}
