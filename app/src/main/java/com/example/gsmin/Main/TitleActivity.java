package com.example.gsmin.Main;

import android.content.Intent;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.os.Handler;
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

public class TitleActivity extends AppCompatActivity {

    ImageButton check_btn, back_btn;
    ImageView ec;
    private static String email = "";
    EditText ed;
    public static boolean emailCheck = false;
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
                // Test
                email = "s18011@gsm.hs.kr";

                // Real
                if (ed.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "이메일을 입력하지 않았습니다!", Toast.LENGTH_LONG).show();
                    return;
                }
                email = ed.getText().toString() + "@gsm.hs.kr";

//                ec.setVisibility(View.VISIBLE);
//                check_btn.setVisibility(View.GONE);

                Data.setData(new String[]{"email"}, new String[]{email});
                new JSONTask().execute(Data.url+"/emailCheck");//"http://15.164.212.158:3000/emailCheck");//AsyncTask 시작시킴

//                // if 이메일 인증 성공
//                while (emailCheck){
//                    startActivity(new Intent(getApplication(), InfoActivity.class));
//                    TitleActivity.this.finish();
//                }
            }
        });
//        ed.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                check_btn.performClick();
//                return true;
//            }
//        });
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