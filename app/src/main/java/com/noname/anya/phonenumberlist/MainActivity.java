package com.noname.anya.phonenumberlist;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static android.provider.ContactsContract.Directory.ACCOUNT_TYPE;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private BaseAdapterEx mAdapter=null;
//    private SharedPreferences preferences;
    private ArrayList<PersonInfo> mContents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.lv_list);
        mContents=getContactList();


//        preferences=getSharedPreferences("listview", MODE_PRIVATE);
//        SharedPreferences.Editor editor=preferences.edit();
//        int size=preferences.getInt("size",0);
//        Log.e("onCreate", "onCreate sizeof:"+size);
//        if(size==0){
//            // 처음 실행 할 떄
//            mContents=getContactList();
//        }else{
//            Log.e("onCreate","onCreate "+size);
//            for(int i=0; i<size; i++) {
//                String temp=preferences.getString((Integer.valueOf(i).toString()), "fail");
//                StringTokenizer st = new StringTokenizer(temp, ",");
//                String[] tempArray = new String[3];
//
//                int j=0;
//                while (st.hasMoreTokens()) {
//                    tempArray[j++]=st.nextToken();
//                }
//                PersonInfo info=new PersonInfo();
//                info.mNickName=tempArray[0];
//                info.mMessage=tempArray[1];
//                info.mPhoneNumber=tempArray[2];
//
//                mContents.add(i,info);
//            }
//        }

        mAdapter=new BaseAdapterEx(this, mContents);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(mItemClickListener);
        mListView.setOnItemLongClickListener(mOnItemLongClickListener);

        Button mButton=(Button)findViewById(R.id.Btn_add);
        mButton.setOnClickListener(mAddCLickListener);
    }

    private ListView.OnItemLongClickListener mOnItemLongClickListener=new ListView.OnItemLongClickListener() {

        @Override
        public boolean onItemLongClick(AdapterView<?> parentView, View view, final int position, long id) {
//            Log.d("mOnItemLongClickListener","mOnItemLongClickListener position="+ position + " , id=" + id);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());

            alertDialogBuilder
                    .setTitle("항목 삭제")
                    .setMessage("이 항목을 삭제하시겠습니까?").setCancelable(false)
                    .setCancelable(false)
                    .setPositiveButton("삭제",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialog, int id) {
                                    //
                                    //int result = getContentResolver().delete(RowContacts)
                                    //삭제한다
//                                    preferences.edit().remove(new Integer(position).toString());
                                    long removeID=mAdapter.getItem(position).contactId;
                                    removeContactsToAddressBook(getApplicationContext() ,removeID);
                                    mAdapter.delete(position);

//                                    preferences.edit().commit();
//                                    Log.e("preferences", "완료");
                                }
                            })
                    .setNegativeButton("취소",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialog, int id) {
                                    // 다이얼로그를 취소한다
                                    dialog.cancel();
                                }
                            });

            alertDialogBuilder.show();

            return true;
        }
    };

    private View.OnClickListener mAddCLickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(MainActivity.this, AddPersonInfoActivity.class);
            startActivityForResult(intent, 1);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode,resultCode, data);

        if(resultCode==RESULT_OK){
            if(requestCode==1) {
                String return_textview_message_detail=data.getStringExtra("textview_message_detail");
                String return_textview_nickname_detail=data.getStringExtra("textview_nickname_detail");
                String return_textview_phonenumber_detail=data.getStringExtra("textview_phonenumber_detail");

                Log.e("onActivityResult","정상종료 확인 ("+return_textview_message_detail+") , ("+return_textview_nickname_detail+"),("+return_textview_phonenumber_detail+")");
                PersonInfo info=new PersonInfo();
                info.mNickName=return_textview_nickname_detail;
                info.mMessage=return_textview_message_detail;
                info.mPhoneNumber=return_textview_phonenumber_detail;

                Log.d("info.toString()",info.toString());
                addContactsToAddressBook(info);
                //아무데나 추가했다가
                mAdapter.add(mAdapter.getCount(),info);

                Comparator<PersonInfo> asc=new Comparator<PersonInfo>() {
                    @Override
                    public int compare(PersonInfo personInfo, PersonInfo compare) {
                        return personInfo.mNickName.compareToIgnoreCase(compare.mNickName);
                    }
                };
                //정렬한다
                Collections.sort(mContents, asc);
                mAdapter.notifyDataSetChanged();

            }else if(requestCode==2){

                String return_textview_nickname=data.getStringExtra("nickname");
                String return_textview_message=data.getStringExtra("message");
                String return_textview_phonenumber=data.getStringExtra("phonenumber");

                int return_textview_position=data.getIntExtra("position", 0);

                PersonInfo info=new PersonInfo();
                info.mNickName=return_textview_nickname;
                info.mMessage=return_textview_message;
                info.mPhoneNumber=return_textview_phonenumber;

                long modifyID=mAdapter.getItem(return_textview_position).contactId;

                mAdapter.modify(return_textview_position,info);
                modifyContactsToAddressBook(getApplicationContext(), modifyID, info);
            }
        }
    }
    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //Toast.makeText(getApplicationContext(), "l = " + l_position, Toast.LENGTH_SHORT).show();
            Log.e("OnItemClickListener", "OnItemClick 발생!!!!");
            Intent intent=new Intent(MainActivity.this, DetailPersonInfoActivity.class);
            intent.putExtra("nickname",mAdapter.getItem(position).mNickName);
            intent.putExtra("message",mAdapter.getItem(position).mMessage);
            intent.putExtra("phonenumber",mAdapter.getItem(position).mPhoneNumber);
            intent.putExtra("position", position);

            startActivityForResult(intent, 2);
        }
    };
    @Override
    protected void onResume() {
        super.onResume();
        Log.e("MyInfoActivity", "onResume");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.e("MyInfoActivity", "onPause");

//        SharedPreferences.Editor editor=preferences.edit();
//        editor.putInt("size", mAdapter.getCount());
//        Log.e("onStop", "onStop size"+(Integer.valueOf(mAdapter.getCount()).toString()));
//        for(int i=0; i<mAdapter.getCount(); i++) {
//            editor.putString(Integer.valueOf(i).toString(),mAdapter.getItem(i).toString());
//        }
//        editor.apply();

    }
    @Override
    protected void onStop(){
        super.onStop();
        Log.e("MyInfoActivity", "onStop");
    }

    private ArrayList<PersonInfo> getContactList() {

        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        String[] projection = new String[] {
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME };

        String[] selectionArgs = null;

        String sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " COLLATE LOCALIZED ASC";

        Cursor contactCursor = getContentResolver().query(uri, projection, null, selectionArgs, sortOrder);

        ArrayList<PersonInfo> contactlist = new ArrayList<PersonInfo>();

        if (contactCursor.getCount()>0) {

            while (contactCursor.moveToNext()){
                String phonenumber = contactCursor.getString(1).replaceAll("-", "");

                PersonInfo tempInfo = new PersonInfo();
                tempInfo.setContactId(contactCursor.getLong(0));
                Log.e("getContactList","contactCursor : "+contactCursor.getLong(0)+" , "+contactCursor.getString(2));
                tempInfo.setPhonenum(phonenumber);
                tempInfo.setName(contactCursor.getString(2));
                tempInfo.mMessage=" ";
                contactlist.add(tempInfo);
            }
        }
        contactCursor.close();
        return contactlist;
    }

    // TODO: shardPrefrenece 에 저장해둬서 삭제 취소기능 구현할때 쓸것!!

    public void removeContactsToAddressBook(Context context, long deleteId){
        context.getContentResolver().delete(ContactsContract.RawContacts.CONTENT_URI, ContactsContract.RawContacts.CONTACT_ID+"="+deleteId,null);

    }
    public void modifyContactsToAddressBook(Context context, long modifyID, PersonInfo info){
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

        ContentProviderOperation.Builder builder = ContentProviderOperation.newUpdate(ContactsContract.RawContacts.CONTENT_URI);
        builder.withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null);
        builder.withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null);
        ops.add(builder.build());

        // Name
        builder = ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI);
        builder.withSelection(Data._ID,  new String[]{String.valueOf(modifyID)});
        builder.withValue(Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        builder.withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, info.getName());
        ops.add(builder.build());

        // Number
        builder = ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI);
        builder.withSelection(Data._ID,  new String[]{String.valueOf(modifyID)});
        builder.withValue(Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        builder.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, info.getPhonenum());
        builder.withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK);
        ops.add(builder.build());

        // Update
        try
        {
            context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    /**
     * This function is used to add a new contact to address book
     *
     * @param contact
     */
    public void addContactsToAddressBook(PersonInfo contact){
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, ACCOUNT_TYPE)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, "춘향이")
                .build()
        );

        if(contact != null){
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, contact.getName())
                    .build()
            );

            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(Phone.NUMBER, contact.mPhoneNumber)
                    .withValue(Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                    .build()
            );

            // Asking the Contact provider to create a new contact
            try {
                getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        Toast.makeText(getApplicationContext(), "추가 되었습니다.", Toast.LENGTH_LONG).show();
    }


}
