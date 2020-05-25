package com.example.gsmin.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.gsmin.Json.JSONTask;
import com.example.gsmin.Main.BoardActivity;
import com.example.gsmin.Model.Data;
import com.example.gsmin.R;

public class HomeFragment extends Fragment {
    private ImageView gsmin;
    private TextView mainText;
    private static int[] r = new int[]{
            R.id.t1, R.id.t2, R.id.t3,
            R.id.t4, R.id.t5, R.id.t6,
            R.id.t7, R.id.t8, R.id.t9,
            R.id.t10, R.id.t11};
//    private String[] strData = new String[]{
//            "전체",
//            "HOT",
//            "전체",
//            "전체",
//            "전체",
//            "전체",
//    };
    private int TV_LEN = r.length;
    private TextView[] tvArr = new TextView[TV_LEN];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);

//        gsmin = (ImageView) view.findViewById(R.id.gsmin);
//        mainText = (TextView) view.findViewById(R.id.mainText);
//        gsmin.setVisibility(View.VISIBLE);
//        mainText.setVisibility(View.GONE);

        init(view);

        // setOnClickListener
        for(int i = 0; i < TV_LEN; i++) {
            final int finalI = i;
            tvArr[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("finalData", "onClick: "+finalI);

//                    Data.setData(new String[]{"clickData"}, new String[]{String.valueOf(finalI)});
//                    new JSONTask().execute("@string/serverUrl"+"/emailCheck");//AsyncTask 시작시킴
                    Intent intent = new Intent(getContext(), BoardActivity.class);
                    intent.putExtra("channel", finalI);
                    startActivity(intent);
                    getActivity().finish();
                }
            });
        }
        return view;
    }

    private void init(View v) {
        for(int i = 0; i<TV_LEN;i++){
            tvArr[i] = v.findViewById(r[i]);
        }
//        t1 = v.findViewById(R.id.t1);
//        t2 = v.findViewById(R.id.t2);
//        t3 = v.findViewById(R.id.t3);
//        t4 = v.findViewById(R.id.t4);
//        t5 = v.findViewById(R.id.t5);
//        t6 = v.findViewById(R.id.t6);
//        t7 = v.findViewById(R.id.t7);
//        t8 = v.findViewById(R.id.t8);
//        t9 = v.findViewById(R.id.t9);
//        t10 = v.findViewById(R.id.t10);
//        t11 = v.findViewById(R.id.t11);
//        t12 = v.findViewById(R.id.t12);
//        t13 = v.findViewById(R.id.t13);
//        t14 = v.findViewById(R.id.t14);
//        t15 = v.findViewById(R.id.t15);
//        t16 = v.findViewById(R.id.t16);
    }

    ;
}
