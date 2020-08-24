package com.example.gsmin.Main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.gsmin.Util.Login;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class InfoActivity extends AppCompatActivity {
    public String jsonResult = "";
    ImageButton check_btn, back_btn;
    EditText pw1, pw2, name;
    TextView ck1, ck2, ck3;
    SweetAlertDialog pDialog;
    Context ct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_info);

        ct = this;
        pDialog = new SweetAlertDialog(ct, SweetAlertDialog.PROGRESS_TYPE);
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

                Handler hd = new Handler();
                hd.postDelayed(new InfoActivity.splashhandler(jt), 1800);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading");
                pDialog.setCancelable(false);
                pDialog.show();
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
        JSONTask jt;
        public splashhandler(JSONTask jt) {
            this.jt = jt;
        }

        public void run() {
            jsonResult = jt.jsonReturn();
            Log.d("","\nckcode: " + "success"+"\nresult: " + jsonResult);
            String pwd_str = pw1.getText().toString();
            if (jsonResult.trim().equals("success")){
//                SharedPreferences sharedPreferences = getSharedPreferences("login_data",MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString("pw", pwd_str);
//                editor.commit();
//
//                SharedPreferences userLogin = getSharedPreferences("login_data",MODE_PRIVATE);
//                String email = userLogin.getString("email","");
//                String pw = userLogin.getString("pw","");

//                Log.d("회원가입", "run: "+ email+"/"+pw);
//                if (!email.equals("") && !pw.equals("")){
//
//                    Login l = new Login();
//                    l.login_user(email, pw);
//
//                    startActivity(new Intent(getApplication(), MainActivity.class));
//                }else{
//                    startActivity(new Intent(getApplication(), StartActivity.class));
////                startActivity(new Intent(getApplication(), JobActivity.class));
//                }
                startActivity(new Intent(getApplication(), LoginActivity.class));
                InfoActivity.this.finish();
            }else{
                Toast.makeText(getApplicationContext(), "INSERT_ERROR", Toast.LENGTH_SHORT).show();
                return;
            }
            pDialog.hide();
        }
    }
}
