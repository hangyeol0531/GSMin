package com.example.gsmin.Main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gsmin.Json.JSONTask;
import com.example.gsmin.Model.Data;
import com.example.gsmin.R;
import com.example.gsmin.Splash.SplashActivity;

import java.util.concurrent.ExecutionException;

public class InfoActivity extends AppCompatActivity {
    public String jsonResult = "";
    ImageButton check_btn, back_btn;
    EditText pw1, pw2, name;
    TextView ck1, ck2, ck3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_info);

        pw1 = findViewById(R.id.pw1);
        pw2 = findViewById(R.id.pw2);
        name = findViewById(R.id.name);

        ck1 = findViewById(R.id.check_1);
        ck2 = findViewById(R.id.check_2);
        ck3 = findViewById(R.id.check_3);

        back_btn = findViewById(R.id.back_btn);
        check_btn = findViewById(R.id.check_btn);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(getApplication(), TitleActivity.class));
            InfoActivity.this.finish();
            }
        });

        check_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Data.setData(new String[]{"email","pw", "nickname"}, new String[]{Data.UserEmail, pw1.getText().toString(), name.getText().toString()});
                JSONTask jt = new JSONTask();
                jt.execute(Data.url + "/insert_user_information");
                jsonResult = jt.jsonReturn();

                Handler hd = new Handler();
                hd.postDelayed(new InfoActivity.splashhandler(), 1000);
            }
        });

        pw2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (pw1.getText().toString().equals(pw2.getText().toString())) {
                    ck2.setTextColor(Color.parseColor("#09AA4A"));
                    ck2.setText("비밀번호가 일치합니다.");
                } else {
                    ck2.setTextColor(Color.parseColor("#E70C0C"));
                    ck2.setText("비밀번호가 일치하지 않습니다.");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        }

    private class splashhandler implements Runnable {
        public void run() {
            Log.d("","\nckcode: " + "success"+"\nresult: " + jsonResult);

            if (jsonResult.trim().equals("success")){
                startActivity(new Intent(getApplication(), MainActivity.class));
                InfoActivity.this.finish();
            }else{
                Toast.makeText(getApplicationContext(), "INSERT_ERROR", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }
}
