package com.example.gsmin.Splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.example.gsmin.Main.MainActivity;
import com.example.gsmin.Main.StartActivity;
import com.example.gsmin.Main.TitleActivity;
import com.example.gsmin.R;

public class SplashActivity extends Activity {

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
//            startActivity(new Intent(getApplication(), TitleActivity.class));
            startActivity(new Intent(getApplication(), MainActivity.class));
//            startActivity(new Intent(getApplication(), StartActivity.class));

        SplashActivity.this.finish();
        }
    }

    @Override
    public void onBackPressed() {
        //초반 플래시 화면에서 넘어갈때 뒤로가기 버튼 못누르게 함
    }

}