package com.example.gsmin.Main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.JsonToken;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.auth0.android.jwt.JWT;
import com.example.gsmin.Json.JSONTask;
import com.example.gsmin.Model.Data;
import com.example.gsmin.R;
import com.example.gsmin.Util.Login;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity  extends AppCompatActivity {
    Context ct;
    public static ImageButton check_btn, back_btn;
    private String email_str = "", pwd_str = "", result="";
    public static EditText email, pwd;
    public String jsonResult = "";
    SweetAlertDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        back_btn = findViewById(R.id.back_btn);
        check_btn = findViewById(R.id.check_btn);
        email = findViewById(R.id.email_url);
        pwd = findViewById(R.id.pwd);
        ct = this;
        pDialog = new SweetAlertDialog(ct, SweetAlertDialog.PROGRESS_TYPE);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplication(), StartActivity.class));
                LoginActivity.this.finish();
            }
        });

        check_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Real
                if (email.getText().toString().isEmpty()){
                    SweetAlertDialog sd = new SweetAlertDialog(ct, SweetAlertDialog.ERROR_TYPE);
                    sd.setTitleText("음...");
                    sd.setContentText("이메일을 깜빡했나요?");
                    sd.show();
                    sd.findViewById(R.id.confirm_button).setBackgroundColor(ContextCompat.getColor( ct, R.color.skyblue));
                    return;
                }
                if (pwd.getText().toString().isEmpty()){
                    SweetAlertDialog sd = new SweetAlertDialog(ct, SweetAlertDialog.ERROR_TYPE);
                    sd.setTitleText("음...");
                    sd.setContentText("비밀번호를 깜빡했나봐요!");
                    sd.show();
                    sd.findViewById(R.id.confirm_button).setBackgroundColor(ContextCompat.getColor( ct, R.color.skyblue));
                    return;
                }
                email_str = email.getText().toString() + "@gsm.hs.kr";
                pwd_str = pwd.getText().toString();

                Login l = new Login();
                l.login_user(email_str, pwd_str);

                Handler hd = new Handler();
                hd.postDelayed(new LoginActivity.splashhandler(l), 2000);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#41AFE5"));
                pDialog.setTitleText("Loading");
                pDialog.setCancelable(false);
                pDialog.show();
            }
        });
    }
    public class splashhandler implements Runnable {
        Login l;
        public splashhandler(Login l) {
            this.l = l;
        }

        public void run() {
            jsonResult = l.jsonResult;
            if (jsonResult.equals("network_error")){
//                Toast.makeText(getApplicationContext(), "네트워크가 불안정합니다!!", Toast.LENGTH_SHORT).show();
                SweetAlertDialog sd = new SweetAlertDialog(ct, SweetAlertDialog.ERROR_TYPE);
                sd.setTitleText("이런...");
                sd.setContentText("네트워크가 불안정합니다!!");
                sd.show();
                pDialog.hide();
                return;
            }else if (jsonResult == null){
//                Toast.makeText(getApplicationContext(), "네트워크가 불안정합니다!!", Toast.LENGTH_SHORT).show();
                Log.d("loginAc", "null");
                SweetAlertDialog sd = new SweetAlertDialog(ct, SweetAlertDialog.ERROR_TYPE);
                sd.setTitleText("이런...");
                sd.setContentText("서버가 불안정합니다!!");
                sd.show();
                pDialog.hide();
                return;
            }
            try {
                JSONObject jo = new JSONObject(jsonResult);
                Log.d("loginAc", "token: "+jo.getString("token"));
                if (!jo.getString("token").isEmpty()){
                    SharedPreferences sharedPreferences = getSharedPreferences("login_data",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("email", email_str);
                    editor.putString("pw", pwd_str);
                    editor.commit();

                    pDialog.hide();
                    startActivity(new Intent(getApplication(), MainActivity.class));
                    LoginActivity.this.finish();
                }else{
                    SweetAlertDialog sd = new SweetAlertDialog(ct, SweetAlertDialog.ERROR_TYPE);
                    sd.setTitleText("이런...");
                    sd.setContentText("이메일 또는 비밀번호가 틀립니다!");
                    sd.show();
                    pDialog.hide();
                    check_btn.setVisibility(View.VISIBLE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}