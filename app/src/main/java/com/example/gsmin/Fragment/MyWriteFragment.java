package com.example.gsmin.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.gsmin.Adapter.HomeRecyclerViewAdapter;
import com.example.gsmin.Json.JSONTask;
import com.example.gsmin.Main.BoardActivity;
import com.example.gsmin.Main.BulletinActivity;
import com.example.gsmin.Main.MainActivity;
import com.example.gsmin.Model.DB;
import com.example.gsmin.Model.Data;
import com.example.gsmin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MyWriteFragment extends Fragment {

    private static RecyclerView recyclerViewWrite;
    private static ArrayList<String[]> listData = new ArrayList<>();
    private static HomeRecyclerViewAdapter adapterWrite  = new HomeRecyclerViewAdapter();
    static SweetAlertDialog pDialog;

    static JSONTask jt;
    static View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mywrite, container, false);
//        init(view);
        recyclerViewWrite = view.findViewById(R.id.recycler_main_write);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerViewWrite.setLayoutManager(linearLayoutManager);

        adapterWrite = new HomeRecyclerViewAdapter();
        recyclerViewWrite.setAdapter(adapterWrite);

        listData = new ArrayList<>();
        pDialog = new SweetAlertDialog(view.getContext(), SweetAlertDialog.PROGRESS_TYPE);

        final SwipeRefreshLayout slayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        slayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMyWrite();
                slayout.setRefreshing(false);
            }
        });

        MainActivity.mainEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                getDataWrite();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

//        pDialog.getProgressHelper().setBarColor(Color.parseColor("#41AFE5"));
//        pDialog.setTitleText("Loading...");
//        pDialog.setCancelable(false);
//        pDialog.show();

        return view;
    }
    public static void getDataWrite(){
        adapterWrite = new HomeRecyclerViewAdapter();
        recyclerViewWrite.setAdapter(adapterWrite);

        for (int i = 0; i < listData.size(); i++) {
            DB db = new DB();
            if (MainActivity.mainEdit.getText().toString().length() != 0){
                if (isTextChange(listData.get(i)[0])){
                    db.setBoardData(listData.get(i)[0], listData.get(i)[1], listData.get(i)[2], listData.get(i)[3], listData.get(i)[4], listData.get(i)[5], listData.get(i)[6]);
                    adapterWrite.addItem(db);
                }
            }else{
                db.setBoardData(listData.get(i)[0], listData.get(i)[1], listData.get(i)[2], listData.get(i)[3], listData.get(i)[4], listData.get(i)[5], listData.get(i)[6]);
                adapterWrite.addItem(db);
            }
        }
        adapterWrite.notifyDataSetChanged();
        Log.d("MyWriteFragment", "getData: Adapter");
    }

    public static void getMyWrite(){
        adapterWrite.clear();
        listData.clear();
        Data.setData(
                new String[]{
                        "page_num",
                        "email",
                        "b_c"},
                new String[]{
                        "1",
                        Data.UserEmail,
                        "b"
                });
        jt = new JSONTask();
        jt.execute(Data.url + "/get_my_list");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if (jt.jsonReturn().equals("null")){
                        view.findViewById(R.id.no_board_layout).setVisibility(View.VISIBLE);
                        pDialog.hide();
                        return;
                    }
                    String jsonRt = jt.jsonReturn();
                    JSONArray ja = new JSONArray(jsonRt);
                    view.findViewById(R.id.no_board_layout).setVisibility(View.GONE);
                    for (int i = 0; i < ja.length(); i++){
                        JSONObject jo = ja.getJSONObject(i);
                        String[] t = jo.getString("date").split("T");
                        String[] a = new String[]{jo.getString("title"), Data.UserName, t[0], jo.getString("good_count"), jo.getString("comment_count"), jo.getString("idx"), String.valueOf(Data.UserGrade)};
                        listData.add(i, a);
                    }
                    getDataWrite();
                    pDialog.hide();
                } catch (JSONException e) {
                    pDialog.hide();
                    SweetAlertDialog sd = new SweetAlertDialog(view.getContext(), SweetAlertDialog.ERROR_TYPE);
                    sd.setTitleText("서버 오류 발생...");
                    sd.setContentText("문제가 생겼어요! 잠시만요..");
                    sd.show();
                    sd.findViewById(R.id.confirm_button).setBackgroundColor(ContextCompat.getColor( view.getContext(), R.color.skyblue));
                    e.printStackTrace();
                }
            }
        }, 500);
    }

    private static boolean isTextChange(String gdata) {
        if (!gdata.contains( MainActivity.mainEdit.getText().toString() )) {
            return false;
        }else{
            return true;
        }
    }

//    private void init(View v) {
//        nameChange = v.findViewById(R.id.nameChange);
//        notice_lay = v.findViewById(R.id.no_notice_layout);
//        gsmin = v.findViewById(R.id.gsmin);
//        mainText = v.findViewById(R.id.mainText);
//        menu = v.findViewById(R.id.menuBtn);
//        search = v.findViewById(R.id.searchBtn);
//        mainEdit = v.findViewById(R.id.mainEdit);
//
//        gsmin.setVisibility(View.GONE);
//        search.setVisibility(View.GONE);
//        menu.setVisibility(View.GONE);
//        mainText.setVisibility(View.VISIBLE);
//        mainText.setText("설정");
//
//
//    }
}
