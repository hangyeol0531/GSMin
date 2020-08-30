package com.example.gsmin.Main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.gsmin.Json.JSONTask;
import com.example.gsmin.Model.Data;
import com.example.gsmin.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class TitleActivity extends AppCompatActivity {
    Context ct;
    public static ImageButton check_btn, back_btn, next_btn;
    public static ImageView ec, email_txt;
    public TextView tvemail;
    private String email = "", result="", checkCode="", jsonResult="";
    public static EditText ed, checkNum;
    SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_title);
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);

        ct = this;
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
                    SweetAlertDialog sd = new SweetAlertDialog(ct, SweetAlertDialog.ERROR_TYPE);
                    sd.setTitleText("음...");
                    sd.setContentText("이메일을 깜빡했나요?");
                    sd.show();
                    return;
                }
                email = ed.getText().toString() + "@gsm.hs.kr";
                sendEmailData();
            }
        });

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            // Real
            if (checkNum.getText().toString().isEmpty()){
                SweetAlertDialog sd = new SweetAlertDialog(ct, SweetAlertDialog.ERROR_TYPE);
                sd.setTitleText("음...");
                sd.setContentText("인증번호를 입력하세요!");
                sd.show();
                return;
            }
            checkCode = checkNum.getText().toString();
            Log.d("","\nckcode: " + checkCode+"\nresult: " + jsonResult);
            if (checkCode.trim().equals(jsonResult.trim())){
                SweetAlertDialog sd = new SweetAlertDialog(TitleActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                sd.setTitleText("굿..!");
                sd.setContentText("인증번호 일치!!");
                sd.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    }
                }, 2000);

                startActivity(new Intent(getApplication(), InfoActivity.class));
                TitleActivity.this.finish();
            }else{
                SweetAlertDialog sd = new SweetAlertDialog(TitleActivity.this, SweetAlertDialog.ERROR_TYPE);
                sd.setTitleText("음...");
                sd.setContentText("인증번호 틀렸어요!");
                sd.show();
                sd.findViewById(R.id.confirm_button).setBackgroundColor(ContextCompat.getColor( TitleActivity.this, R.color.skyblue));
                return;
            }
            startActivity(new Intent(getApplication(), InfoActivity.class));
            TitleActivity.this.finish();

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
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#41AFE5"));
        pDialog.setTitleText("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();

        Data.setData(new String[]{"email"}, new String[]{email});
        JSONTask jt = new JSONTask();
        jt.execute(Data.url + "/emailCheck");//"http://15.164.212.158:3000/emailCheck");//AsyncTask 시작시킴

        Handler hd = new Handler();
        hd.postDelayed(new TitleActivity.splashhandler(jt), 1000);

    }

    public class splashhandler implements Runnable {
        JSONTask jt;
        public splashhandler(JSONTask jt) {
            this.jt = jt;
        }

        @Override
        public void run() {
            jsonResult = jt.jsonReturn();
            if (jsonResult.equals("")){
                SweetAlertDialog sd = new SweetAlertDialog(TitleActivity.this, SweetAlertDialog.ERROR_TYPE);
                sd.setTitleText("음...");
                sd.setContentText("그런 이메일 없다는데요?");
                sd.show();
                sd.findViewById(R.id.confirm_button).setBackgroundColor(ContextCompat.getColor( TitleActivity.this, R.color.skyblue));
                pDialog.hide();
                return;
            }else{
                Data.UserEmail = email;
                ec.setVisibility(View.VISIBLE);
                checkNum.setVisibility(View.VISIBLE);
                check_btn.setVisibility(View.GONE);
                email_txt.setVisibility(View.GONE);
                ed.setVisibility(View.GONE);
                tvemail.setVisibility(View.GONE);
                next_btn.setVisibility(View.VISIBLE);
                pDialog.hide();
            }
        }
    }
}