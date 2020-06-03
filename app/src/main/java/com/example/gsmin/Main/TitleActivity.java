package com.example.gsmin.Main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;

import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gsmin.Model.Data;
import com.example.gsmin.Json.JSONTask;
import com.example.gsmin.R;
import com.example.gsmin.Splash.SplashActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.BreakIterator;
import java.util.concurrent.ExecutionException;

public class TitleActivity extends AppCompatActivity {
    public static ImageButton check_btn, back_btn;
    public static ImageView ec;
    private String email = "", result="";
    public static EditText ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_title);

        back_btn = findViewById(R.id.back_btn);
        check_btn = findViewById(R.id.check_btn);
        ed = findViewById(R.id.email_url);
        ec = findViewById(R.id.email_chk);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplication(), StartActivity.class));
                TitleActivity.this.finish();
            }
        });

        check_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Real
                if (ed.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "이메일을 입력하지 않았습니다!", Toast.LENGTH_LONG).show();
                    return;
                }
                email = ed.getText().toString() + "@gsm.hs.kr";

                ec.setVisibility(View.VISIBLE);
                check_btn.setVisibility(View.GONE);

                Data.setData(new String[]{"email"}, new String[]{email});
                try {
                    result = new JSONTask().execute(Data.url + "/emailCheck").get();//"http://15.164.212.158:3000/emailCheck");//AsyncTask 시작시킴
                    JSONObject jo = new JSONObject(result);
                    Log.d("TITLE", "onPostExecute: " + jo);

//                // if 이메일 인증 성공
                    if (jo.getString("isChecked") == "t"){
                        startActivity(new Intent(getApplication(), MainActivity.class));
//                    startActivity(new Intent(getApplication(), InfoActivity.class));
                        TitleActivity.this.finish();
                    }else{
                        Toast.makeText(getApplicationContext(), "이메일 인증에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        ec.setVisibility(View.GONE);
                        check_btn.setVisibility(View.VISIBLE);
                    }


                } catch (ExecutionException | InterruptedException | JSONException e) {
                    e.printStackTrace();
                }


            }
        });

        ed.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // TODO Auto-generated method stub
                if(keyCode == event.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    check_btn.performClick();
                    return true;
                }
                return false;
            }
        });
    }
}