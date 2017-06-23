package com.example.shankaryadav.contactsgps;

/**
 * Created by shankaryadav on 20/06/17.
 */


public class User {

    private Contact me;

    private Contact[] contacts;

    public Contact getMe() {
        return me;
    }

    public void setMe(Contact me) {
        this.me = me;
    }

    public Contact[] getContacts() {
        return contacts;
    }

    public void setContacts(Contact[] contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString(){
        return "Me: "+me+" Contacts: "+contacts;

    }

}