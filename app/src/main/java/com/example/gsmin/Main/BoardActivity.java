package com.example.gsmin.Main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.gsmin.Adapter.HomeRecyclerViewAdapter;
import com.example.gsmin.Model.DB;
import com.example.gsmin.R;

public class BoardActivity extends AppCompatActivity {
    private static HomeRecyclerViewAdapter adapter = new HomeRecyclerViewAdapter();
    private static String[][] listData = new String[][]{
            {"boardTitle1", "boardName", "boardInfo", "0", "0"},
            {"boardTitle2", "boardName", "boardInfo", "0", "0"},
            {"boardTitle3", "boardName", "boardInfo", "0", "0"},
            {"boardTitle4", "boardName", "boardInfo", "0", "0"},
            {"boardTitle5", "boardName", "boardInfo", "0", "0"}

    };
    private ImageView gsmin;
    private TextView mainText;
    private ImageButton back, floating;
    public static String channel = "";
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        channel = intent.getExtras().getString("channel");

        gsmin = findViewById(R.id.gsmin);
        mainText = findViewById(R.id.mainText);
        floating = findViewById(R.id.fab);
        back = findViewById(R.id.drawer_btn);
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
//        layout.setOnRefreshListener();

        slayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // start refresh
//                transaction.detach(homeFragment).attach(homeFragment).commit();
                slayout.setRefreshing(false);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                BoardActivity.this.finish();
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
        getData();
    }

    public static void getData(){
        for (int i = 0; i < listData.length; i++) {
            Log.d("suc", "onClick: "+listData.length);
            DB db = new DB();
            db.setBoardData(listData[i][0], listData[i][1], listData[i][2], listData[i][3], listData[i][4]);
            adapter.addItem(db);
        }
        adapter.notifyDataSetChanged();
    }
}
