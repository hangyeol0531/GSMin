package com.example.gsmin.Util;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.gsmin.Json.JSONTask;
import com.example.gsmin.Main.LoginActivity;
import com.example.gsmin.Main.MainActivity;
import com.example.gsmin.Main.StartActivity;
import com.example.gsmin.Model.Data;
import com.example.gsmin.Splash.SplashActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class Login {
    public static String jsonResult = "";
    public void login_user (String email, String pw){
        Data.setData(new String[]{"email", "pw"}, new String[]{email, pw});
        JSONTask jt = new JSONTask();
        jt.execute(Data.url + "/login_check");

        Handler hd = new Handler();
        hd.postDelayed(new Login.splashhandler(jt), 2000);
    }
    public class splashhandler implements Runnable {
        JSONTask jt;
        public splashhandler(JSONTask jt) {
            this.jt = jt;
        }

        public void run() {
            if (jt.jsonReturn().equals("")){
                jsonResult = "network_error";
                return;
            }
            try {
                JSONObject jo = new JSONObject(jt.jsonReturn());
                if (!jo.getString("token").isEmpty()){
                    Data.token = jo.getString("token");
                    Data.UserEmail = jo.getString("email");
                    Data.UserName = jo.getString("name");
                    Data.UserGrade = Integer.parseInt(jo.getString("grade"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("login.java", "result: "+jt.jsonReturn());
            jsonResult = jt.jsonReturn();
        }
    }
}
