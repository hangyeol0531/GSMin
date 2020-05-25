package com.example.gsmin.Main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.gsmin.Fragment.HomeFragment;
import com.example.gsmin.Json.JSONTask;
import com.example.gsmin.Model.Data;
import com.example.gsmin.R;

public class BoardActivity extends AppCompatActivity {

    private ImageView gsmin;
    private TextView mainText;
    private ImageButton back, floating;
    private String channel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        channel = intent.getExtras().getString("channel");

//        Data.setData(new String[]{"channel"}, new String[]{channel});
//        new JSONTask().execute("@string/serverUrl"+"/emailCheck");

        gsmin = findViewById(R.id.gsmin);
        mainText = findViewById(R.id.mainText);
        floating = findViewById(R.id.fab);
        back = findViewById(R.id.drawer_btn);

        back.setBackgroundResource(R.drawable.arrow_back);
        gsmin.setVisibility(View.GONE);
        mainText.setVisibility(View.VISIBLE);
        mainText.setText(String.valueOf(channel));

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
                Log.d("suc", "onClick: z");
//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(intent);
//                BoardActivity.this.finish();
            }
        });
    }
}
