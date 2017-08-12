package com.noname.anya.phonenumberlist;

/**
 * Created by anya on 2017. 7. 27..
 */

public class PersonInfo {
    long contactId;
    public String mNickName ="";
    public String mMessage ="";
    public String mPhoneNumber="";

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
                        if (mPhoneNumber.length() == 10) {
                            mPhoneNumber = mPhoneNumber.substring(0, 3) + "-"
                            + mPhoneNumber.substring(3, 6) + "-"
                            + mPhoneNumber.substring(6,9);
                } else if (mPhoneNumber.length() == 8) {
                            mPhoneNumber = mPhoneNumber.substring(0, 3) + "-"
                            + mPhoneNumber.substring(3, 7) + "-"
                            + mPhoneNumber.substring(7);
                }

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
