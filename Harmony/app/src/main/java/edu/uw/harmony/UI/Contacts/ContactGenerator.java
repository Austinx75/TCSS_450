package edu.uw.harmony.UI.Contacts;

import java.util.Arrays;
import java.util.List;

public class ContactGenerator {
    private static final ContactCard[] CONTACTS;
    public static final int COUNT = 20;


    static {
        CONTACTS = new ContactCard[COUNT];
        for (int i = 0; i < CONTACTS.length; i++) {
            CONTACTS[i] = new ContactCard
                    .Builder("Joe Smith", "0001").addUsername("Ajoe").addNumber("2156062185").addStatus("Idle")
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
