package com.example.gsmin.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import com.example.gsmin.Json.JSONTask;
import com.example.gsmin.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment {

    TextView rice, date, ricetime;
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
//        Data.setData(new String[]{"email"}, new String[]{email});
        new JSONTask().execute("http://10.53.68.185:3000/emailCheck");//AsyncTask 시작시킴

//        final PullRefreshLayout layout = (PullRefreshLayout)view.findViewById(R.id.swipeRefreshLayout);
//        layout.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);

//        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                // start refresh
//
////                MainActivity.transaction.detach(MainActivity.homeFragment).attach(MainActivity.homeFragment).commit();
//                layout.setRefreshing(false);
//            }
//        });

        return view;
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
