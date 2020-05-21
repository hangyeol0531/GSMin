package com.example.gsmin.Main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gsmin.R;

public class InfoActivity extends AppCompatActivity {

    ImageButton check_btn, back_btn;
    private String email;
    EditText pw1, pw2, name;
    TextView ck1, ck2, ck3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_info);

        pw1 = findViewById(R.id.pw1);
        pw2 = findViewById(R.id.pw2);
        name = findViewById(R.id.name);

        ck1 = findViewById(R.id.check_1);
        ck2 = findViewById(R.id.check_2);
        ck3 = findViewById(R.id.check_3);

        back_btn = findViewById(R.id.back_btn);
        check_btn = findViewById(R.id.check_btn);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplication(), TitleActivity.class));
                InfoActivity.this.finish();
            }
        });

        check_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        pw2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (pw1.getText().toString().equals(pw2.getText().toString())) {
                    ck2.setTextColor(Color.parseColor("#09AA4A"));
                    ck2.setText("비밀번호가 일치합니다.");
                } else {
                    ck2.setTextColor(Color.parseColor("#ff0000"));
                    ck2.setText("비밀번호가 일치하지 않습니다.");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        }
}
