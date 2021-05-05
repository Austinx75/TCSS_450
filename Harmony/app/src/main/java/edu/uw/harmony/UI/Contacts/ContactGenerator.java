package edu.uw.harmony.UI.Contacts;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import edu.uw.harmony.R;

public class ContactGenerator {
    private static final ContactCard[] CONTACTS;
    public static final int COUNT = 20;
    public static int[] images = {R.drawable.contact_boy_512, R.drawable.contact_hacker_512,R.drawable.contact_barista_512,
            R.drawable.contact_kitty_512,R.drawable.contact_man_512,R.drawable.contact_man_1_512,
            R.drawable.contact_man_2_512,R.drawable.contact_user_512,R.drawable.contact_woman_512,
            R.drawable.contact_woman_1_512};
    static Random rand = new Random();

    static {
        CONTACTS = new ContactCard[COUNT];
        for (int i = 0; i < CONTACTS.length; i++) {
            CONTACTS[i] = new ContactCard
                    .Builder("Joe Smith", "0001").addUsername("Ajoe").addNumber("2156062185").addStatus("Idle").addAvatar(images[rand.nextInt(images.length)])
                    .build();
        }
    }

    public static List<ContactCard> getContactList() {
        return Arrays.asList(CONTACTS);
    }

    public static ContactCard[] getCONTACTS() {
        return Arrays.copyOf(CONTACTS, CONTACTS.length);
    }

    private ContactGenerator() { }
}
