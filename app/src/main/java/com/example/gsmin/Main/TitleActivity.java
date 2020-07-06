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
import com.example.gsmin.Util.Login;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.BreakIterator;
import java.util.concurrent.ExecutionException;

public class TitleActivity extends AppCompatActivity {
    public static ImageButton check_btn, back_btn, next_btn;
    public static ImageView ec, email_txt;
    public TextView tvemail;
    private String email = "", result="", checkCode="", jsonResult="";
    public static EditText ed, checkNum;

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
        checkNum = findViewById(R.id.checkNum);
        email_txt = findViewById(R.id.imageView4);
        next_btn = findViewById(R.id.check_btn2);
        tvemail = findViewById(R.id.tv_email);
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
                Data.UserEmail = email;
                ec.setVisibility(View.VISIBLE);
                checkNum.setVisibility(View.VISIBLE);
                check_btn.setVisibility(View.GONE);
                email_txt.setVisibility(View.GONE);
                ed.setVisibility(View.GONE);
                tvemail.setVisibility(View.GONE);
                next_btn.setVisibility(View.VISIBLE);
                sendEmailData();
            }
        });

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            // Real
            if (checkNum.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(), "인증번호를 입력하세요!", Toast.LENGTH_LONG).show();
                return;
            }
            checkCode = checkNum.getText().toString();
            Log.d("","\nckcode: " + checkCode+"\nresult: " + jsonResult);
            if (checkCode.trim().equals(jsonResult.trim())){
                startActivity(new Intent(getApplication(), InfoActivity.class));
                TitleActivity.this.finish();
            }else{
                Toast.makeText(getApplicationContext(), "인증번호가 틀립니다!", Toast.LENGTH_LONG).show();
                return;
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

    private void sendEmailData() {
        Data.setData(new String[]{"email"}, new String[]{email});
        JSONTask jt = new JSONTask();
        jt.execute(Data.url + "/emailCheck");//"http://15.164.212.158:3000/emailCheck");//AsyncTask 시작시킴

        Handler hd = new Handler();
        hd.postDelayed(new TitleActivity.splashhandler(jt), 2000);

    }

    public class splashhandler implements Runnable {
        JSONTask jt;
        public splashhandler(JSONTask jt) {
            this.jt = jt;
        }

        @Override
        public void run() {
            jsonResult = jt.jsonReturn();
        }
    }
}