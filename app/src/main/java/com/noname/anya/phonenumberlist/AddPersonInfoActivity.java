package com.noname.anya.phonenumberlist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddPersonInfoActivity extends AppCompatActivity {
    SharedPreferences preference;
    boolean isEndofEnter =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("AddPersonInfoActivity", "onCreate");
        setContentView(R.layout.activity_person_info);

        preference=getSharedPreferences("AddPerson", MODE_PRIVATE);

        EditText nickname_detail=(EditText)findViewById(R.id.textview_nickname_detail);
        EditText message_detail=(EditText)findViewById(R.id.textview_message_detail);
        EditText phonenumber_detail=(EditText)findViewById(R.id.textview_phonenumber_detail);

        nickname_detail.setText(preference.getString("nickname",""));
        message_detail.setText(preference.getString("massage",""));
        phonenumber_detail.setText(preference.getString("phonenumber",""));

        Button addNewInfo=(Button)findViewById(R.id.Btn_add_new_info);
        addNewInfo.setOnClickListener(AddInfoListener);
    }

    private View.OnClickListener AddInfoListener=new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            Log.e("AddInfoListener","AddInfoListener");
            Intent intent = getIntent();
            EditText nickname_detail=(EditText)findViewById(R.id.textview_nickname_detail);
            EditText message_detail=(EditText)findViewById(R.id.textview_message_detail);
            EditText phonenumber_detail=(EditText)findViewById(R.id.textview_phonenumber_detail);

            intent.putExtra("textview_nickname_detail", nickname_detail.getText().toString());
            intent.putExtra("textview_message_detail",message_detail.getText().toString());
            intent.putExtra("textview_phonenumber_detail", phonenumber_detail.getText().toString());

            isEndofEnter =true;
            setResult(RESULT_OK, intent);
            //Toast.makeText(getApplicationContext(), id_editText.getText().toString(), Toast.LENGTH_SHORT).show();
            finish();
        }
    };
    @Override
    protected void onPause(){
        super.onPause();
        Log.e("AddPersonInfoActivity","onPause");



        EditText nickname_detail=(EditText)findViewById(R.id.textview_nickname_detail);
        EditText message_detail=(EditText)findViewById(R.id.textview_message_detail);
        EditText phonenumber_detail=(EditText)findViewById(R.id.textview_phonenumber_detail);

        SharedPreferences.Editor editor=preference.edit();

        editor.putString("nickname",nickname_detail.getText().toString());
        editor.putString("massage",message_detail.getText().toString());
        editor.putString("phonenumber",phonenumber_detail.getText().toString());

        editor.apply();
    }
    @Override
    protected  void onStop(){
        super.onStop();
        Log.e("AddPersonInfoActivity","onStop");

        if(isEndofEnter) {
            SharedPreferences.Editor editor = preference.edit();

            editor.putString("nickname", "");
            editor.putString("massage", "");
            editor.putString("phonenumber", "");

            editor.apply();
        }
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.e("AddPersonInfoActivity","onDestory");

    }
}