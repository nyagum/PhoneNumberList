package com.noname.anya.phonenumberlist;

import android.graphics.Bitmap;

/**
 * Created by anya on 2017. 8. 13..
 */

public class ContactTo {
    private Bitmap picture;
    private String id;
    private String name;
    private String phone;

    public ContactTo() {
        this.id = null;
        this.picture = null;
        this.name = null;
        this.phone = null;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the picture
     */
    public Bitmap getPicture() {
        return picture;
    }

    /**
     * @param picture the picture to set
     */
    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
}