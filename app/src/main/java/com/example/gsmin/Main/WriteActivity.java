package com.example.gsmin.Main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.gsmin.Json.JSONTask;
import com.example.gsmin.Model.Data;
import com.example.gsmin.R;

import com.github.irshulx.Editor;
import com.github.irshulx.EditorListener;
import com.github.irshulx.models.EditorContent;
import com.github.irshulx.models.EditorTextStyle;
import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class WriteActivity extends AppCompatActivity {
    private ImageButton back, search, menu;
    private ImageView gsmin;
    private LinearLayout wLayout;
    private TextView channelText;
    private EditText editWrite, editTitle;
    private JSONTask jt;
    private Context ct;
    private SweetAlertDialog pDialog;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_write);

        gsmin = findViewById(R.id.gsmin);
        gsmin.setVisibility(View.GONE);

        channelText = findViewById(R.id.channelText);
        channelText.setText(BoardActivity.channel);

        back = findViewById(R.id.drawer_btn);
        back.setVisibility(View.VISIBLE);
        back.setBackgroundResource(R.drawable.arrow_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        wLayout = findViewById(R.id.writeLayout);
        wLayout.setVisibility(View.VISIBLE);

        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        search = findViewById(R.id.searchBtn);
        search.setVisibility(View.INVISIBLE);

        menu = findViewById(R.id.menuBtn);
        menu.setBackgroundResource(R.drawable.mdi_send);

        editWrite = findViewById(R.id.editWrite);
        editTitle = findViewById(R.id.editTitle);

        ct = this;
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editTitle.getText().toString().equals("")){

                    new SweetAlertDialog(ct, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("이런...")
                            .setContentText("제목을 입력하세요!!")
                            .show();
                    return;
                }else if (editWrite.getText().toString().equals("")){

                    new SweetAlertDialog(ct, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("이런...")
                            .setContentText("내용을 입력하세요!!")
                            .show();
                    return;
                }               pDialog.getProgressHelper().setBarColor(Color.parseColor("#41AFE5"));
                pDialog.setTitleText("Loading...");
                pDialog.setCancelable(false);
                pDialog.show();
                Data.setData(new String[]{"title", "type", "email", "content"}, new String[]{
                        editTitle.getText().toString(),
                        channelText.getText().toString(),
                        Data.UserEmail,
                        editWrite.getText().toString()
                });
                jt = new JSONTask();
                jt.execute(Data.url + "/write_Bulletin");
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(jt.jsonReturn().equals("success")){
                            pDialog.hide();

                            SweetAlertDialog sd = new SweetAlertDialog(ct, SweetAlertDialog.SUCCESS_TYPE);
                            sd.setTitleText("굿..!");
                            sd.setContentText("글쓰기 성공!!");
                            sd.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    startActivity(new Intent(getApplication(), BoardActivity.class));
                                    WriteActivity.this.finish();
                                }
                            });
                            sd.findViewById(R.id.confirm_button).setBackgroundColor(ContextCompat.getColor( ct, R.color.skyblue));
                            sd.show();
                        } else {
                            pDialog.hide();
                            SweetAlertDialog sd = new SweetAlertDialog(ct, SweetAlertDialog.ERROR_TYPE);
                            sd.setTitleText("이런...");
                            sd.setContentText("글쓰기 실패!!");
                            sd.show();
                            sd.findViewById(R.id.confirm_button).setBackgroundColor(ContextCompat.getColor( ct, R.color.skyblue));
                        }
                    }
                }, 1000);

            }
        });
    };

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), BoardActivity.class);
        startActivity(intent);
        WriteActivity.this.finish();
    }

}