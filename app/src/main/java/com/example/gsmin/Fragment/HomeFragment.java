package com.example.gsmin.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class HomeFragment extends Fragment {
    private ImageView gsmin;
    private TextView mainText;
    private EditText mainEdit;
    public static int[] menuBtn = new int[]{
            R.id.b1, R.id.b2, R.id.b3,
            R.id.b4, R.id.b5, R.id.b6,
            R.id.b7, R.id.b8, R.id.b9,
            R.id.b10, R.id.b11};

    public static int[] searchLay = new int[]{
            R.id.l1, R.id.l2, R.id.l3,
            R.id.l4, R.id.l5, R.id.l6,
            R.id.l7, R.id.l8, R.id.l9,
            R.id.l10, R.id.l11, R.id.l12};

    public static int[] r = new int[]{
            R.id.t1, R.id.t2, R.id.t3,
            R.id.t4, R.id.t5, R.id.t6,
            R.id.t7, R.id.t8, R.id.t9,
            R.id.t10, R.id.t11};
    public static String[][] listData = new String[][]{
            {"boardTitle1", "boardName", "boardInfo", "0", "0"}
    };


    public static int IB_LEN = menuBtn.length, SL_LEN =  searchLay.length, TV_LEN = r.length;
    public static TextView[] tvArr = new TextView[TV_LEN];
    public static ImageButton[] ibArr = new ImageButton[IB_LEN];
    public static LinearLayout[] slArr = new LinearLayout[SL_LEN];
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
                String channel = tvArr[finalI].getText().toString();

//                Data.setData(new String[]{"channel"}, new String[]{channel});
//                listData[0] = new String[]{"boardTitle10", "boardName", "boardInfo", "0", "0"};
//                try {
//                    String result = new JSONTask().execute(Data.url+"/board").get(); // channel
//                    JSONArray ja = new JSONArray(result);
////                        // {"boardTitle1", "boardName", "boardInfo", "0", "0"}
//////                        "likeCount":"06",
//////                        "section":"자유",
//////                        "content":"Lorem Ipsum, giving information on its origins, as well as a random Lipsum generator.",
//////                        "writer":"양현승",
//////                        "viewer":32,
//////                        "previous":"0초 전"
//
//
////                    for (int i = 0; i < ja.length(); i++){
////                        JSONObject jo = ja.getJSONObject(i);
////                        listData[i][0] = jo.getString("title");
////                        listData[i][1] = jo.getString("writer");
////                        listData[i][2] = jo.getString("previous")+ " ・ 조회수 "+jo.getString("viewer");
////                        listData[i][3] = jo.getString("likeCount");
////                        listData[i][4] = jo.getString("chatCount");
////                    }
//                } catch (ExecutionException | InterruptedException | JSONException e) {
//                    e.printStackTrace();
//                }

                Intent intent = new Intent(getContext(), BoardActivity.class);
                intent.putExtra("channel", channel);
                startActivity(intent);
                getActivity().finish();
                }
            });
        }
        for(int i = 0; i < HomeFragment.IB_LEN; i++) {
            final int finalI = i;
            HomeFragment.ibArr[finalI].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("finalData", "onClick: "+finalI);
                    registerForContextMenu(HomeFragment.ibArr[finalI]);
                }
            });
        }

        return view;
    }

    private void init(View v) {
        for(int i = 0; i<TV_LEN;i++){
            tvArr[i] = v.findViewById(r[i]);
        }
        for(int i = 0; i<IB_LEN;i++){
            ibArr[i] = v.findViewById(menuBtn[i]);
        }
        for(int i = 0; i<SL_LEN;i++){
            slArr[i] = v.findViewById(searchLay[i]);
        }
    }
    ;
}
