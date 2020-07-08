package com.example.gsmin.Main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.gsmin.Json.JSONTask;
import com.example.gsmin.Model.Data;
import com.example.gsmin.R;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class JobActivity extends AppCompatActivity {
    JSONTask jt;
    TextView job_grade_rate, Job_grade_rate_text,job_school_rate, job_school_rate_text;
    SweetAlertDialog pDialog;
    private ImageButton back, search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_job);
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);

        back = findViewById(R.id.drawer_btn);
        back.setBackgroundResource(R.drawable.arrow_back);

        search = findViewById(R.id.searchBtn);
        search.setVisibility(View.GONE);

        job_grade_rate = findViewById(R.id.job_grade_rate);
        Job_grade_rate_text  = findViewById(R.id.job_grade_rate_text );
        job_school_rate = findViewById(R.id.job_school_rate);
        job_school_rate_text = findViewById(R.id.job_school_rate_text);

        Data.type = "GET";
        jt = new JSONTask();
        jt.execute(Data.url + "/gsm_employment_rate");

        Handler hd = new Handler();
        hd.postDelayed(new JobActivity.splashhandler(), 1000);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

        final SwipeRefreshLayout slayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        slayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Handler hd = new Handler();
                hd.postDelayed(new JobActivity.splashhandler(), 1000);
                slayout.setRefreshing(false);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                JobActivity.this.finish();
            }
        });
    }

    public class splashhandler implements Runnable {
        @Override
        public void run() {
            try {
                JSONObject jo = new JSONObject(jt.jsonReturn());
                Log.d("jo", "run: "+jo);

                job_school_rate.setText(jo.getString("rate")+"%");
                job_school_rate_text.setText("현재 80명 중 "+jo.getString("emp_num")+"명");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            pDialog.hide();
            Data.type = "POST";
        }
    }
}
