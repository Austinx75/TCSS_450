package edu.uw.harmony.UI.Avatar;

import android.widget.ImageView;

import java.util.Arrays;
import java.util.List;

import edu.uw.harmony.R;

public class AvatarGenerator {
    private static final Avatar[] AVATARS;
    public static final int COUNT = 50;
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

    public static List<Avatar> getAvatarList() {
        return Arrays.asList(AVATARS);
    }

    public static Avatar[] getAvatars() {
        return Arrays.copyOf(AVATARS, AVATARS.length);
    }
}
