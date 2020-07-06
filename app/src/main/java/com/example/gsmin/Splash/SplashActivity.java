package com.example.gsmin.Splash;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import com.example.gsmin.Fragment.NoticeFragment;
import com.example.gsmin.Json.JSONTask;
import com.example.gsmin.Main.InfoActivity;
import com.example.gsmin.Main.LoginActivity;
import com.example.gsmin.Main.MainActivity;
import com.example.gsmin.Main.StartActivity;
import com.example.gsmin.Main.TitleActivity;
import com.example.gsmin.Model.Data;
import com.example.gsmin.R;
import com.example.gsmin.Util.Login;

import org.json.JSONException;
import org.json.JSONObject;

public class SplashActivity extends Activity {
    public String jsonResult = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_background);

        Handler hd = new Handler();
//        hd.postDelayed(new splashhandler(), 2000);
        hd.postDelayed(new splashhandler(), 1);
    }

    private class splashhandler implements Runnable {
        public void run() {
            SharedPreferences userLogin = getSharedPreferences("login_data",MODE_PRIVATE);
            String email = userLogin.getString("email","");
            String pw = userLogin.getString("pw","");
            if (!email.equals("") && !pw.equals("")){

                Login l = new Login();
                l.login_user(email, pw);

                startActivity(new Intent(getApplication(), MainActivity.class));
            }else{ startActivity(new Intent(getApplication(), StartActivity.class)); }
            SplashActivity.this.finish();
        }
    }

    @Override
    public void onBackPressed() {
        //초반 플래시 화면에서 넘어갈때 뒤로가기 버튼 못누르게 함
    }

}