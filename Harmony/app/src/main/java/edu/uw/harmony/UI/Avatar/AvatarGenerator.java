package edu.uw.harmony.UI.Avatar;

import android.widget.ImageView;

import java.util.Arrays;
import java.util.List;

import edu.uw.harmony.R;

/**
 * This class is used to create Avatar List
 *
 * @author Jack Lin
 * @version 1.0
 */

public class AvatarGenerator {
    /** array of avatars */
    private static final Avatar[] AVATARS;
    /** the number of contacts */
    public static final int COUNT = 50;
    /** the images for avatars*/
    public static int[] images = {R.drawable.contact_boy_512, R.drawable.contact_hacker_512,R.drawable.contact_barista_512,
            R.drawable.contact_kitty_512,R.drawable.contact_man_512,R.drawable.contact_man_1_512,
            R.drawable.contact_man_2_512,R.drawable.contact_user_512,R.drawable.contact_woman_512,
            R.drawable.contact_woman_1_512};

    static {
        AVATARS = new Avatar[images.length];
        for (int i = 0; i < AVATARS.length; i++) {
            AVATARS[i] = new Avatar
                    .Builder(images[i])
                    .build();
        }
    }
    /**
     * Getter method for avatar list
     * @return avatar list
     */
    public static List<Avatar> getAvatarList() {
        return Arrays.asList(AVATARS);
    }

    public static Avatar[] getAvatars() {
        return Arrays.copyOf(AVATARS, AVATARS.length);
    }
}
