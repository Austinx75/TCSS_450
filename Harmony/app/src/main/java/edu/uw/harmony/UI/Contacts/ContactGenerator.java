package edu.uw.harmony.UI.Contacts;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import edu.uw.harmony.R;

/**
 * This class is used to create Sample Contacts and is currently under development.
 * This will be changed to grabbing data from database later on.
 *
 * @author Jack Lin
 * @version 1.0
 */

public class ContactGenerator {

    /** array of contacts */
    private static final ContactCard[] CONTACTS;

    /** the number of contacts */
    public static final int COUNT = 20;

    /** the array of names to assign the contacts for now */
    public static String[] names = {"Tilly Britt", "Damien Church", "Mia Ali", "Ayesha Garcia", "Iga Montes",
                                    "Maddie Donaldson", "Humera Mccartney", "Marnie Bouvet", "Zane Shannon",
                                    "Marlon Cantu", "Nayan Zuniga", "Kerry Weiss", "Tyson Pittman", "Carolyn Lake",
                                    "Deacon Wilder", "Anis Stuart", "Rida Winter", "Aaliyah Doherty", "Charlie Neale",
                                    "Rida Winter"};
    public static String[] usernames = {"Gothse", "Gibsonfirebreatha", "Greenpayne", "Jenkinininki",
                                        "Dobanderson", "Drogcole", "Hahugia", "Zimbradley", "Harrificent",
                                        "Keblex", "Goliread", "Flower Yoda", "TheLionKing", "BlackHoleSnail",
                                        "RhythmStar", "Fox", "IHeartCatnip", "AbleCheese", "RatSauce", "ThePrestige",
                                        "BatmanBegins"};

    /** the different types of status a contact can have */
    public static String[] status = {"Online", "Away", "Idle"};

    /** array of images used for contact avatars */
    public static int[] images = {R.drawable.contact_boy_512, R.drawable.contact_hacker_512,R.drawable.contact_barista_512,
            R.drawable.contact_kitty_512,R.drawable.contact_man_512,R.drawable.contact_man_1_512,
            R.drawable.contact_man_2_512,R.drawable.contact_user_512,R.drawable.contact_woman_512,
            R.drawable.contact_woman_1_512};

    static Random rand = new Random();

    static {
        CONTACTS = new ContactCard[COUNT];
        for (int i = 0; i < CONTACTS.length; i++) {
            CONTACTS[i] = new ContactCard
                    .Builder(names[i], String.valueOf(0x000+i)).addUsername(usernames[i]).addStatus(status[rand.nextInt(status.length)]).addAvatar(images[rand.nextInt(images.length)])
                    .build();
        }
    }

    /**
     * Getter method for contact list
     * @return contact list
     */
    public static List<ContactCard> getContactList() {
        return Arrays.asList(CONTACTS);
    }

    /**
     * @return  a copy of the contact list
     */
    public static ContactCard[] getCONTACTS() {
        return Arrays.copyOf(CONTACTS, CONTACTS.length);
    }

    /**
     * private constructor
     */
    private ContactGenerator() { }
}
