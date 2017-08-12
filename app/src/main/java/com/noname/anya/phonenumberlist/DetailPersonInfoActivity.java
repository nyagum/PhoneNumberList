package com.noname.anya.phonenumberlist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DetailPersonInfoActivity extends AppCompatActivity {
    EditText nickname_detail;
    EditText message_detail;
    EditText phonenumber_detail;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_person_info);
        Intent intent = getIntent();

        nickname_detail=(EditText)findViewById(R.id.detail_textview_nickname_detail);
        message_detail=(EditText)findViewById(R.id.detail_textview_message_detail);
        phonenumber_detail=(EditText)findViewById(R.id.detail_textview_phonenumber_detail);

        String nickname=intent.getStringExtra("nickname");
        String message=intent.getStringExtra("message");
        String phonenumber=intent.getStringExtra("phonenumber");
        position=intent.getIntExtra("position",0);

        Log.e("onCreate position:",Integer.toString(position));
        nickname_detail.setText(nickname);
        message_detail.setText(message);
        phonenumber_detail.setText(phonenumber);

        Button btn_modify=(Button)findViewById(R.id.btn_modify);
        btn_modify.setOnClickListener(modifyOnClickListener);
    }
    private View.OnClickListener modifyOnClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=getIntent();
            intent.putExtra("nickname",nickname_detail.getText().toString());
            intent.putExtra("message", message_detail.getText().toString());
            intent.putExtra("phonenumber",phonenumber_detail.getText().toString());
            intent.putExtra("position",position);

            Log.e("AddInfoListener","nickname= "+nickname_detail.getText().toString()+
                    ", message= "+message_detail.getText().toString()+
                    ", phonenumber= "+phonenumber_detail.getText().toString()+
                    ", position= "+position);

            setResult(RESULT_OK, intent);
            finish();
        }
    };
}
