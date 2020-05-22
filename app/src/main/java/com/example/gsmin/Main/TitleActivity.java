package com.example.gsmin.Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gsmin.Model.Data;
import com.example.gsmin.Json.JSONTask;
import com.example.gsmin.R;

public class TitleActivity extends AppCompatActivity {

    ImageButton check_btn, back_btn;
    ImageView ec;
    private static String email = "";
    EditText ed;

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
//                if (ed.getText().toString().isEmpty()){
//                    Toast.makeText(getApplicationContext(), "이메일을 입력하지 않았습니다!", Toast.LENGTH_LONG).show();
//                    return;
//                }
//                email = ed.getText().toString() + "@gsm.hs.kr";
//
//                ec.setVisibility(View.VISIBLE);
//                check_btn.setVisibility(View.GONE);
//                // if 이메일 인증 성공
                Data.setData(new String[]{"email"}, new String[]{email});
                new JSONTask().execute("http://10.53.68.185:3000/emailCheck");//AsyncTask 시작시킴

                startActivity(new Intent(getApplication(), InfoActivity.class));
                TitleActivity.this.finish();
            }
        });

        ed.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // TODO Auto-generated method stub
                if(keyCode == event.KEYCODE_ENTER)
                {
                    check_btn.performClick();
                    return true;
                }
                return false;
            }
        });
    }
}