package com.example.gsmin.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.gsmin.Json.JSONTask;
import com.example.gsmin.Main.BoardActivity;
import com.example.gsmin.Main.MainActivity;
import com.example.gsmin.Model.Data;
import com.example.gsmin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SettingFragment extends Fragment {
    private JSONTask jt;
    private LinearLayout notice_lay;
    private ImageView gsmin;
    private TextView mainText;
    private ImageButton search, menu;
    private static EditText mainEdit, nameChange;
    SweetAlertDialog pDialog;
    private Button Changebtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
//        init(view);
        nameChange = view.findViewById(R.id.nameChange);
        Changebtn = view.findViewById(R.id.changeBtn);
        pDialog = new SweetAlertDialog(view.getContext(), SweetAlertDialog.PROGRESS_TYPE);

        Changebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                pDialog.getProgressHelper().setBarColor(Color.parseColor("#41AFE5"));
//                pDialog.setTitleText("Loading...");
//                pDialog.setCancelable(false);
//                pDialog.show();

                if (nameChange.getText().toString().equals("")){
                    SweetAlertDialog s1 = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
                    s1.setTitleText("Oops...");
                    s1.setContentText("변경할 닉네임을 입력하세요!");
                    s1.show();
                    s1.findViewById(R.id.confirm_button).setBackgroundColor(ContextCompat.getColor( getContext(),R.color.skyblue));
                    return;
                }

                SweetAlertDialog s1 = new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE);
                s1.setTitleText(nameChange.getText().toString());
                s1.setContentText("수정하시겠습니까?");
                s1.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        final SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
                        pDialog.getProgressHelper().setBarColor(Color.parseColor("#41AFE5"));
                        pDialog.setTitleText("Loading...");
                        pDialog.setCancelable(false);
                        pDialog.show();

                        Data.setData(new String[]{"email", "change_nickname"}, new String[]{Data.UserEmail, nameChange.getText().toString()});
                        jt = new JSONTask();
                        jt.execute(Data.url + "/update_nickname");
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if(jt.jsonReturn().equals("success")){
                                    Data.UserName = nameChange.getText().toString();
                                    MainActivity.navName.setText(Data.UserName);
                                    SweetAlertDialog sd = new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE);
                                    sd.setTitleText(Data.UserName);
                                    sd.setContentText("변경 완료!!");
                                    sd.show();
                                    nameChange.setText("");
                                    sd.findViewById(R.id.confirm_button).setBackgroundColor(ContextCompat.getColor( getContext(),R.color.skyblue));

                                }else{
                                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("이런...")
                                            .setContentText("네트워크가 불안정합니다!!")
                                            .show();
                                }
                                pDialog.hide();
                            }
                        }, 1000);
                    }
                })
                .show();
                s1.findViewById(R.id.confirm_button).setBackgroundColor(ContextCompat.getColor( getContext(),R.color.skyblue));

            }
        });




        return view;
    }

    private void init(View v) {
        nameChange = v.findViewById(R.id.nameChange);
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
        mainText.setText("설정");


    }
}
