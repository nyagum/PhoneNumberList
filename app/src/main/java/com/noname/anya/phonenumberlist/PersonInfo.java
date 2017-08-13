package com.noname.anya.phonenumberlist;

import android.util.Log;

/**
 * Created by anya on 2017. 7. 27..
 */

public class PersonInfo {
    long contactId;
    public String mNickName;
    public String mMessage;
    public String mPhoneNumber;

    public String toString(){
        return mNickName+","+mMessage+","+mPhoneNumber;
    }
    public long getPhotoid() {
        return contactId;
    }
    public void setContactId(long photoid) {
        this.contactId = photoid;
    }
    //TODO: 핸드폰 번호처럼 출력되게 만들것!
    public String getPhonenum() {
        String formatNumber="";
        Log.e("PersonInfo","mPhoneNumber before format"+mPhoneNumber);
        if (mPhoneNumber.length() == 10) {
            formatNumber = mPhoneNumber.substring(0, 3) + "-"
                            + mPhoneNumber.substring(3, 6) + "-"
                            + mPhoneNumber.substring(6,9);
                } else if (mPhoneNumber.length() == 8) {
            formatNumber = mPhoneNumber.substring(0, 3) + "-"
                            + mPhoneNumber.substring(3, 7) + "-"
                            + mPhoneNumber.substring(7);
                }
        Log.e("PersonInfo","mPhoneNumber after format"+formatNumber);
        return mPhoneNumber;
    }
    public void setPhonenum(String phonenum) {
        this.mPhoneNumber = phonenum;
    }
    public String getName() {
        return this.mNickName;
    }
    public void setName(String name) {
        this.mNickName = name;
    }
}
