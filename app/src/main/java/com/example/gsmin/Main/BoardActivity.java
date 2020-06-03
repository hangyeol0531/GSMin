package com.example.gsmin.Main;

import android.content.Intent;
import android.os.Bundle;
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
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.gsmin.Adapter.HomeRecyclerViewAdapter;
import com.example.gsmin.Fragment.HomeFragment;
import com.example.gsmin.Model.DB;
import com.example.gsmin.R;

public class BoardActivity extends AppCompatActivity {
    private static HomeRecyclerViewAdapter adapter = new HomeRecyclerViewAdapter();

    private static String[][] listData = new String[][]{
            {"boardTitle1", "boardName", "boardInfo", "0", "0"}
    };
    private ImageView gsmin;
    private TextView mainText;
    private ImageButton back, floating, search;
    private static EditText mainEdit;
    public static String channel = "";
    private static RecyclerView recyclerView;
    private boolean searchActivity= true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        Toolbar toolbar = findViewById(R.id.toolbar);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        if (getIntent()!=null && intent.getExtras()!= null) {
            channel = intent.getExtras().getString("channel");
        }
        listData = HomeFragment.listData;

        gsmin = findViewById(R.id.gsmin);
        mainText = findViewById(R.id.mainText);
        floating = findViewById(R.id.fab);
        back = findViewById(R.id.drawer_btn);
        search = findViewById(R.id.searchBtn);
        mainEdit = findViewById(R.id.mainEdit);
        recyclerView = findViewById(R.id.recycler_main);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new HomeRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        back.setBackgroundResource(R.drawable.arrow_back);
        gsmin.setVisibility(View.GONE);
        mainText.setVisibility(View.VISIBLE);
        mainText.setText(String.valueOf(channel));

        final SwipeRefreshLayout slayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        slayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mainEdit.setText("");
                search();
                mainText.setVisibility(View.VISIBLE);
                mainEdit.setVisibility(View.GONE);
                slayout.setRefreshing(false);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WriteActivity.class);
                intent.putExtra("channel", channel);
                startActivity(intent);
                BoardActivity.this.finish();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });
        mainEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                getData();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        getData();
    }

    private static boolean isTextChange(String gdata) {
        if (!gdata.contains( mainEdit.getText().toString() )) {
            return false;
        }else{
            return true;
        }
    }

    public void search(){
        if(searchActivity){
            getData();
            mainText.setVisibility(View.GONE);
            mainEdit.setVisibility(View.VISIBLE);
            searchActivity=false;
        }else {
            getData();
            mainText.setVisibility(View.VISIBLE);
            mainEdit.setVisibility(View.GONE);
            searchActivity=true;
        }
    };

    public static void getData(){
        adapter = new HomeRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);
        for (int i = 0; i < listData.length; i++) {
            DB db = new DB();
            if (mainEdit.getText().toString().length() != 0){
                if (isTextChange(listData[i][0])){
                    db.setBoardData(listData[i][0], listData[i][1], listData[i][2], listData[i][3], listData[i][4]);
                    adapter.addItem(db);
                }
            }else{
                db.setBoardData(listData[i][0], listData[i][1], listData[i][2], listData[i][3], listData[i][4]);
                adapter.addItem(db);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        BoardActivity.this.finish();
    }
}
