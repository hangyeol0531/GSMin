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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.gsmin.Adapter.ChatRecyclerViewAdapter;
import com.example.gsmin.Json.JSONTask;
import com.example.gsmin.Model.DB;
import com.example.gsmin.Model.Data;
import com.example.gsmin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import us.feras.mdv.MarkdownView;

public class BulletinActivity extends AppCompatActivity {

    JSONTask jt;
    private String title, grade;
    private String date, name;
    private static String idx;
    private TextView title_tv, name_tv, info_tv, chatCnt;
    private EditText chatEdit;
    private ImageView bulletinGrade;
    private ImageButton heart, menu, back, chatSend;
    SweetAlertDialog pDialog;
    MarkdownView markdownView;
    private static ChatRecyclerViewAdapter adapter = new ChatRecyclerViewAdapter();
    private static ArrayList<String[]> listData = new ArrayList<>();
    private static RecyclerView recyclerView;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_bulletin);
        markdownView = (MarkdownView) findViewById(R.id.markdownView);

        heart = findViewById(R.id.searchBtn);
        heart.setBackgroundResource(R.drawable.mdi_thumb_up);
        menu = findViewById(R.id.menuBtn);
        back = findViewById(R.id.drawer_btn);
        back.setBackgroundResource(R.drawable.arrow_back);
        chatCnt = findViewById(R.id.chatCnt);
        chatSend = findViewById(R.id.chatSend);
        chatEdit = findViewById(R.id.chatEdit);

        bulletinGrade = findViewById(R.id.bulletinGrade);

        recyclerView = findViewById(R.id.recycler_main_bulletin);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new ChatRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);

        pDialog.getProgressHelper().setBarColor(Color.parseColor("#41AFE5"));
        pDialog.setTitleText("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();

        title_tv = findViewById(R.id.boardTitle_bulletin);
        name_tv = findViewById(R.id.boardName_bulletin);
        info_tv = findViewById(R.id.boardInfo_bulletin);

        Intent intent = getIntent();
        if (getIntent()!=null && intent.getExtras()!= null) {
            title = intent.getExtras().getString("title");
            name = intent.getExtras().getString("name");
            date = intent.getExtras().getString("date");
            idx = intent.getExtras().getString("idx");
            grade = intent.getExtras().getString("grade");

            Log.d("BulletingActivity",
                    "title: "+title+"\n"+
                    "name: "+name+"\n"+
                    "date: "+date+"\n"+
                    "idx: "+idx+"\n"+
                    "grade: "+grade+"\n");
            switch (grade){
                case "1" : bulletinGrade.setImageResource(R.drawable.one_icon);break;
                case "2" :bulletinGrade.setImageResource(R.drawable.two_icon);break;
                case "3" :bulletinGrade.setImageResource(R.drawable.three_icon);break;
                default: bulletinGrade.setImageResource(R.drawable.grad_icon);break;
            }
        }
        getContent();

        final SwipeRefreshLayout slayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        slayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setChatData();
                slayout.setRefreshing(false);
            }
        });

        title_tv.setText(title);
        name_tv.setText(name);
        info_tv.setText(date);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        chatEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (chatEdit.getText().toString().equals("")){
                    chatSend.setImageResource(R.drawable.ic_mdi_send_gray);
                }else {
                    chatSend.setImageResource(R.drawable.mdi_send);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        chatSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (chatEdit.getText().toString().equals("")){
                    SweetAlertDialog sd = new SweetAlertDialog(BulletinActivity.this, SweetAlertDialog.ERROR_TYPE);
                    sd.setTitleText("음...");
                    sd.setContentText("댓글을 쓰고 눌러주세요!");
                    sd.show();
                    sd.findViewById(R.id.confirm_button).setBackgroundColor(ContextCompat.getColor( BulletinActivity.this, R.color.skyblue));
                    return;
                }
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#41AFE5"));
                pDialog.setTitleText("Loading...");
                pDialog.setCancelable(false);
                pDialog.show();
                Data.setData(
                        new String[]{
                                "idx",
                                "email",
                                "comment"},
                        new String[]{
                                idx,
                                Data.UserEmail,
                                chatEdit.getText().toString()
                        });
                jt = new JSONTask();
                jt.execute(Data.url + "/write_comment");
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pDialog.hide();
                        if (jt.jsonReturn().equals("success")) {
                            SweetAlertDialog sd = new SweetAlertDialog(BulletinActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                            sd.setTitleText("성공!!");
                            sd.setContentText("댓글이 달렸어요!");
                            chatEdit.setText("");
                            sd.show();
                            sd.findViewById(R.id.confirm_button).setBackgroundColor(ContextCompat.getColor( BulletinActivity.this,R.color.skyblue));
                        }else{
                            pDialog.hide();
                            SweetAlertDialog sd = new SweetAlertDialog(BulletinActivity.this, SweetAlertDialog.ERROR_TYPE);
                            sd.setTitleText("오류 발생...");
                            sd.setContentText("잠시 후 다시 시도해 주세요!");
                            sd.show();
                            sd.findViewById(R.id.confirm_button).setBackgroundColor(ContextCompat.getColor( BulletinActivity.this, R.color.skyblue));
                            return;
                        }
                        pDialog.hide();
                        setChatData();
                    }
                }, 1000);
            }
        });
    }

    private void getContent(){
        Data.setData(
                new String[]{
                        "idx"},
                new String[]{
                        idx
                });
        jt = new JSONTask();
        jt.execute(Data.url + "/get_one_board");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if (jt.jsonReturn().equals("null")){
                        markdownView.loadMarkdown(" ");
                        markdownView.loadCssFromAssets("markdown_css_themes/paperwhite.css");
                        return;
                    }
                    String jsonRt = jt.jsonReturn();
                    JSONObject jo = new JSONObject(jsonRt);
                    markdownView.loadMarkdown(jo.getString("content"));
                    info_tv.setText(info_tv.getText().toString()+" ・ 조회수 "+jo.getString("view_count"));
                    pDialog.hide();
                } catch (JSONException e) {
                    pDialog.hide();
                    SweetAlertDialog sd = new SweetAlertDialog(BulletinActivity.this, SweetAlertDialog.ERROR_TYPE);
                    sd.setTitleText("서버 오류 발생...");
                    sd.setContentText("문제가 생겼어요! 잠시만요..");
                    sd.show();
                    sd.findViewById(R.id.confirm_button).setBackgroundColor(ContextCompat.getColor( BulletinActivity.this, R.color.skyblue));

                    e.printStackTrace();
                }
                pDialog.hide();
                markdownView.loadCssFromAssets("markdown_css_themes/paperwhite.css");
                setChatData();
            }
        }, 500);
    }
    private void setChatData() {
        adapter.clear();
        listData.clear();
        Data.setData(
                new String[]{
                        "idx"},
                new String[]{
                        idx
                });
        jt = new JSONTask();
        jt.execute(Data.url + "/get_comment_information");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if (jt.jsonReturn().equals("null")){
                        chatCnt.setText("댓글 0개");
                        pDialog.hide();
                        return;
                    }
                    String jsonRt = jt.jsonReturn();
                    JSONArray ja = new JSONArray(jsonRt);
                    chatCnt.setText("댓글 "+ja.length()+"개");
                    for (int i = 0; i < ja.length(); i++){
                        JSONObject jo = ja.getJSONObject(i);
                        String[] t = jo.getString("date").split("T");
                        Log.d("BulletingActivity", "run: "+jo.getString("comment"));
                        String[] a = new String[]{jo.getString("nickname"), t[0], "0", jo.getString("comment"), jo.getString("grade")};
                        listData.add(i, a);
                    }
                    getData();
                    pDialog.hide();
                } catch (JSONException e) {
                    pDialog.hide();
                    SweetAlertDialog sd = new SweetAlertDialog(BulletinActivity.this, SweetAlertDialog.ERROR_TYPE);
                    sd.setTitleText("서버 오류 발생...");
                    sd.setContentText("문제가 생겼어요! 잠시만요..");
                    sd.show();
                    sd.findViewById(R.id.confirm_button).setBackgroundColor(ContextCompat.getColor(BulletinActivity.this, R.color.skyblue));

                    e.printStackTrace();
                }
            }
        }, 1000);
    }
    public void getData(){
        adapter = new ChatRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);
        for (int i = 0; i < listData.size(); i++) {
            DB db = new DB();
            Log.d("Bulletin", "getData: " + listData.get(i)[3]);
            db.setChatData(listData.get(i)[0], listData.get(i)[1], listData.get(i)[2], listData.get(i)[3], listData.get(i)[4]);
            adapter.addItem(db);
        }
        adapter.notifyDataSetChanged();
        Log.d("BulletinActivity(Chat)", "getData: Adapter");
    }
}