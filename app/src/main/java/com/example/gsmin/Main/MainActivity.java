package com.example.gsmin.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.gsmin.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView rice, date, ricetime;
    TextView con1, con2, con3;
    TextView id1, id2, id3;
    TextView t1, t2, t3;
    TextView c1, c2, c3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        date.setText(setDate());
    }

    private String setDate() {
        Date currentTime = Calendar.getInstance().getTime();

        SimpleDateFormat weekdayFormat = new SimpleDateFormat("EE", Locale.getDefault());
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());

        String weekDay = weekdayFormat.format(currentTime);
        String day = dayFormat.format(currentTime);
        String month = monthFormat.format(currentTime);

        return (month+"/"+day+"("+weekDay+")");
    }

    private void init() {
        rice = findViewById(R.id.rice);
        date = findViewById(R.id.date);
        ricetime = findViewById(R.id.ricetime);

        con1 = findViewById(R.id.content1);
        con2 = findViewById(R.id.content2);
        con3 = findViewById(R.id.content3);

        id1 = findViewById(R.id.id1);
        id2 = findViewById(R.id.id2);
        id3 = findViewById(R.id.id3);

        t1 = findViewById(R.id.t_cnt_1);
        t2 = findViewById(R.id.t_cnt_2);
        t3 = findViewById(R.id.t_cnt_3);

        c1 = findViewById(R.id.c_cnt_1);
        c2 = findViewById(R.id.c_cnt_2);
        c3 = findViewById(R.id.c_cnt_3);
    }
}
