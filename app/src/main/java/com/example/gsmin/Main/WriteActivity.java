package com.example.gsmin.Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gsmin.R;

public class WriteActivity extends AppCompatActivity {
    private ImageButton back, search, menu;
    private ImageView gsmin;
    private LinearLayout wLayout;
    private TextView channelText;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_write);

        gsmin = findViewById(R.id.gsmin);
        gsmin.setVisibility(View.GONE);

        back = findViewById(R.id.drawer_btn);
        back.setVisibility(View.VISIBLE);
        back.setBackgroundResource(R.drawable.arrow_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BoardActivity.class);
                intent.putExtra("channel", BoardActivity.channel);
                startActivity(intent);
                WriteActivity.this.finish();
            }
        });

        wLayout = findViewById(R.id.writeLayout);
        wLayout.setVisibility(View.VISIBLE);

        channelText = findViewById(R.id.channelText);
        channelText.setText(BoardActivity.channel);

        search = findViewById(R.id.searchBtn);
        search.setVisibility(View.INVISIBLE);

        menu = findViewById(R.id.menuBtn);
        menu.setBackgroundResource(R.drawable.mdi_send);


        Intent intent = getIntent();
        String title = intent.getExtras().getString("title");

    }
}