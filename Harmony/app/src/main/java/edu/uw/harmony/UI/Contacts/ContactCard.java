package edu.uw.harmony.UI.Contacts;

import java.io.Serializable;

public class ContactCard implements Serializable{

    private String name;
    private String number;
    private String username;
    private final String id;
    private String status;

    public static class Builder{
        private String name;
        private String number = "";
        private String username = "";
        private final String id;
        private String status = "";

        public Builder(String name, String id){
            this.name = name;
            this.id = id;
        }

        public Builder addNumber(String val){
            number = val;
            return this;
        }

        public Builder addUsername(String val){
            username = val;
            return this;
        }

        public Builder addStatus(String val){
            status = val;
            return this;
        }

        public ContactCard build() { return new ContactCard(this);}
    }

    private ContactCard(final Builder builder){
        this.name = builder.name;
        this.number = builder.number;
        this.username = builder.username;
        this.id = builder.id;
        this.status = builder.status;
    }

    public String getName(){ return name; }
    public String getNumber() { return number; }
    public String getUsername() {return username;}
    public String getId(){ return id; }
    public String getStatus(){ return status;}
}
