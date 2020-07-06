package com.example.gsmin.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.gsmin.R;

public class NoticeFragment extends Fragment {

    private LinearLayout notice_lay;
    private ImageView gsmin;
    private TextView mainText;
    private ImageButton search, menu;
    private static EditText mainEdit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notice, container, false);
//        init(view);



        return view;
    }

    private void init(View v) {
        notice_lay = v.findViewById(R.id.no_notice_layout);
        gsmin = v.findViewById(R.id.gsmin);
        mainText = v.findViewById(R.id.mainText);
        menu = v.findViewById(R.id.menuBtn);
        search = v.findViewById(R.id.searchBtn);
        mainEdit = v.findViewById(R.id.mainEdit);

        gsmin.setVisibility(View.GONE);
        search.setVisibility(View.GONE);
        menu.setVisibility(View.GONE);
        mainText.setVisibility(View.VISIBLE);
        mainText.setText("알림");


    }
}
