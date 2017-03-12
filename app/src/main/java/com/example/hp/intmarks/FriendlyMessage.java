package com.example.hp.intmarks;

/**
 * Created by HP on 12/03/2017.
 */

public class FriendlyMessage {
    private String text;
    private String usn;
    private String name;
//    private String photoUrl;

    public FriendlyMessage() {
    }

    public FriendlyMessage(String text, String usn,String name) {
        this.text = text;
        this.usn = usn;
        this .name=name;
        //this.photoUrl = photoUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsn() {
        return usn;
    }

    public void setUsn(String usn) {
        this.usn = usn;
    }

   /* public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }*/
}
