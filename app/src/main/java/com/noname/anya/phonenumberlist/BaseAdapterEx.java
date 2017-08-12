package com.noname.anya.phonenumberlist;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by anya on 2017. 7. 28..
 */

public class BaseAdapterEx extends BaseAdapter {
    private Context mContext =null;
    private ArrayList<PersonInfo> mData=null;
    private LayoutInflater mLayout_Inflater=null;

    public BaseAdapterEx(Context context, ArrayList<PersonInfo> data){
        this.mContext=context;
        this.mData=data;
        this.mLayout_Inflater=LayoutInflater.from(mContext);
    }

    @Override
    public int getCount(){
        return mData.size();
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public PersonInfo getItem(int position){
        return mData.get(position);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View itemLayout=convertView;

        if(itemLayout==null){
            itemLayout=mLayout_Inflater.inflate(R.layout.row,null);
        }
        ImageView textview_Image=(ImageView)itemLayout.findViewById(R.id.iv_photo);
        TextView textview_nickname=(TextView)itemLayout.findViewById(R.id.textview_nickname);
        TextView textview_message=(TextView)itemLayout.findViewById(R.id.textview_message);
        final TextView textview_phonenumber=(TextView)itemLayout.findViewById(R.id.textview_phonenumber);
        Button textview_phoneview=(Button) itemLayout.findViewById(R.id.Btn_phoneview);

        textview_nickname.setText(mData.get(position).mNickName);
        textview_message.setText(mData.get(position).mMessage);
        textview_phonenumber.setText(mData.get(position).mPhoneNumber);

        PersonInfo info = mData.get(position);
        //Todo: mipmap 에서 비트맵으로 가져오기가 안된다 이미지는 정말 못하고 안드로이드가 끝나는건가
        //Bitmap bm = openPhoto(info.getPhotoid());
        // 사진없으면 기본 사진 보여주기
        //textview_Image.setImageBitmap(bm);

        textview_phoneview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tel="tel:"+textview_phonenumber.getText().toString();
                view.getContext().startActivity(
                        new Intent("android.intent.action.DIAL", Uri.parse(tel))
                );
            }
        });

        return itemLayout;
    }

    private Bitmap openPhoto(long contactId) {
        Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId);
        InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(mContext.getContentResolver(), contactUri);

        if (input != null) {
            return BitmapFactory.decodeStream(input);
        }else{
            return BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.ic_launcher);
        }
    }

    public void add(int index, PersonInfo addData){
        mData.add(index, addData);

        notifyDataSetChanged();
    }
    public void delete(int index){
        mData.remove(index);

        notifyDataSetChanged();
    }
    public void clear(){
        mData.clear();
        notifyDataSetChanged();
    }
    public void modify(int index, PersonInfo modify_data){
        mData.remove(index);
        mData.add(index, modify_data);
        notifyDataSetChanged();
    }
}
