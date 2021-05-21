package edu.uw.harmony.UI.Contacts;

import java.io.Serializable;

/**
 * This class represents a contact card in the ContactListFragment.
 *
 * @author  Jack Lin
 * @version 1.0
 */

public class ContactCard implements Serializable{

    private String name;
    private String username;
    private final String id;
    private String status;
    private int avatar;

    /**
     * Nested class builder
     */
    public static class Builder{
        private String name;
        private String username = "";
        private final String id;
        private String status = "";
        private int avatar;

        /**
         * Constructs a new Builder.
         *
         * @param name of the user
         * @param id of the user
         */
        public Builder(String name, String id){
            this.name = name;
            this.id = id;
        }


        /**
         * Method to add username to Builder obj
         * @param val string of username
         * @return builder obj
         */
        public Builder addUsername(String val){
            username = val;
            return this;
        }

        /**
         * Method to add status to Builder obj
         * @param val string of status
         * @return builder obj
         */
        public Builder addStatus(String val){
            status = val;
            return this;
        }

        /**
         * Method to add avatar to Builder obj
         * @param val int of avatar
         * @return builder obj
         */
        public Builder addAvatar(int val){
            avatar = val;
            return this;
        }

        /**
         * Method to return ContactCard obj
         * @return ContactCard
         */
        public ContactCard build() { return new ContactCard(this);}
    }

    /**
     * Construct a ContactCard using Builder as parameter
     * @param builder Builder
     */
    private ContactCard(final Builder builder){
        this.name = builder.name;
        this.username = builder.username;
        this.id = builder.id;
        this.status = builder.status;
        this.avatar = builder.avatar;
    }

    /**
     * @return The name this class represents.
     */
    public String getName(){ return name; }


    /**
     * @return The username this class represents.
     */
    public String getUsername() {return username;}

    /**
     * @return The id this class represents.
     */
    public String getId(){ return id; }

    /**
     * @return The status this class represents.
     */
    public String getStatus(){ return status;}

    /**
     * @return The avatar this class represents.
     */
    public int getAvatar(){return avatar;}
}
