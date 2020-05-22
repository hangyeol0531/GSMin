package com.example.gsmin.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.gsmin.Json.JSONTask;
import com.example.gsmin.Main.MainActivity;
import com.example.gsmin.Model.Data;
import com.example.gsmin.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.example.gsmin.Main.MainActivity.homeFragment;
import static com.example.gsmin.Main.MainActivity.transaction;

public class HomeFragment extends Fragment {
    public static String rice_list;
    static String month, day, year, eatTime;
    TextView rice;
    TextView date, ricetime;
    TextView con1, con2, con3;
    TextView id1, id2, id3;
    TextView t1, t2, t3;
    TextView c1, c2, c3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        init(view);
        date.setText(setDate());
        Data.setData(new String[]{"dateyear", "datemonth", "dateday", "eatTime"}, new String[]{year, month, day, eatTime});
        new JSONTask().execute("@string/serverUrl"+"/gsmschoolfood");//AsyncTask 시작시킴

        rice.setText(rice_list);

        final SwipeRefreshLayout layout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
//        layout.setOnRefreshListener();

        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // start refresh
//                transaction.detach(homeFragment).attach(homeFragment).commit();
                layout.setRefreshing(false);
            }
        });

        return view;
    }

    private String setDate() {
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat weekdayFormat = new SimpleDateFormat("EE", Locale.getDefault());
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat hourFormat = new SimpleDateFormat("kk", Locale.getDefault());
        SimpleDateFormat yearFormat = new SimpleDateFormat("YYYY", Locale.getDefault());

        String weekDay = weekdayFormat.format(currentTime);
        day = dayFormat.format(currentTime);
        month = monthFormat.format(currentTime);
        int hour = Integer.parseInt(hourFormat.format(currentTime));
        year= yearFormat.format(currentTime);

        Log.d("hour", "setDate: "+hour);
        if (hour < 9){
            eatTime = "조식";
        }else if(hour < 13){
            eatTime = "중식";
        }else {
            eatTime = "석식";
        }

        ricetime.setText(eatTime);
        return (month+"/"+day+"("+weekDay+")");
    }

    private void init(View view) {
        rice = view.findViewById(R.id.rice);
        date = view.findViewById(R.id.date);
        ricetime = view.findViewById(R.id.ricetime);

        con1 = view.findViewById(R.id.content1);
        con2 = view.findViewById(R.id.content2);
        con3 = view.findViewById(R.id.content3);

        id1 = view.findViewById(R.id.id1);
        id2 = view.findViewById(R.id.id2);
        id3 = view.findViewById(R.id.id3);

        t1 = view.findViewById(R.id.t_cnt_1);
        t2 = view.findViewById(R.id.t_cnt_2);
        t3 = view.findViewById(R.id.t_cnt_3);

        c1 = view.findViewById(R.id.c_cnt_1);
        c2 = view.findViewById(R.id.c_cnt_2);
        c3 = view.findViewById(R.id.c_cnt_3);
    }
}
