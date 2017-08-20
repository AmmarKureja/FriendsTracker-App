package com.ammarkureja.friendstrackerapp.model;

/**
 * Created by admin on 15/08/2017.
 */

public class ContactEntry {
    private String id ;
    private String name;
    private String phone;
    private String email;
    private String imageUrl;

    ContactEntry(){
        this.id=null;
        this.name=null;
        this.phone=null;
        this.email=null;
        this.imageUrl=null;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "ContactEntry{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}

