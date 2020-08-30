package com.example.gsmin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gsmin.Json.JSONTask;
import com.example.gsmin.Main.BoardActivity;
import com.example.gsmin.Main.BulletinActivity;
import com.example.gsmin.Main.JobActivity;
import com.example.gsmin.Main.WriteActivity;
import com.example.gsmin.Model.DB;
import com.example.gsmin.Model.Data;
import com.example.gsmin.R;
import com.example.gsmin.Util.Login;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.ItemViewHolder> {
    private static String[] strli;
    private static ImageView boardGrade_image;
    private static TextView boardName, boardTitle, boardInfo, boardThumb, boardMsg, boardIdx, boardGrade;
    public static String jsonResult = "";
    // adapter에 들어갈 list 입니다.
    private ArrayList<DB> listData = new ArrayList<>();

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        Log.d("TEST", "onCreateViewHolder: ");
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return listData.size();
    }
    public void clear() {
        int size = getItemCount();
        listData.clear();
        notifyItemRangeRemoved(0, size);
    }
    public void addItem(DB data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout mLayout;

        ItemViewHolder(final View itemView) {
            super(itemView);
            mLayout = itemView.findViewById(R.id.mainLayout);
            boardName = itemView.findViewById(R.id.boardName);
            boardTitle = itemView.findViewById(R.id.boardTitle);
            boardInfo = itemView.findViewById(R.id.boardInfo);
            boardThumb = itemView.findViewById(R.id.t_cnt_1);
            boardMsg = itemView.findViewById(R.id.c_cnt_1);
            boardIdx = itemView.findViewById(R.id.boardIdx);
            boardGrade = itemView.findViewById(R.id.boardGrade);
            boardGrade_image = itemView.findViewById(R.id.boardGrade_image);
        }

        void onBind(DB db) {
            strli = db.getBoardData();
            boardTitle.setText(strli[0]);
            boardName.setText(strli[1]);
            boardInfo.setText(strli[2]);
            boardThumb.setText(strli[3]);
            boardMsg.setText(strli[4]);
            boardIdx.setText(strli[5]);
            boardGrade.setText(strli[6]);
            final String
                    title = boardTitle.getText().toString(),
                    name = boardName.getText().toString(),
                    info = boardInfo.getText().toString(),
                    idx = boardIdx.getText().toString(),
                    grade = boardGrade.getText().toString();
            switch (grade){
                case "1" : boardGrade_image.setImageResource(R.drawable.one_icon);break;
                case "2" :boardGrade_image.setImageResource(R.drawable.two_icon);break;
                case "3" :boardGrade_image.setImageResource(R.drawable.three_icon);break;
                default: boardGrade_image.setImageResource(R.drawable.grad_icon);break;
            }

            mLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("HomeRecyclerViewAdapter", "onClick: "+ BoardActivity.channel + " / " + boardIdx.getText().toString()+":"+boardTitle.getText().toString()+ "|" + boardName.getText().toString());
                    Intent intent = new Intent(itemView.getContext(), BulletinActivity.class);
                    intent.putExtra("title", title);
                    intent.putExtra("name", name);
                    intent.putExtra("date", info);
                    intent.putExtra("idx", idx);
                    intent.putExtra("grade", grade);
                    itemView.getContext().startActivity(intent);
                }
            });
            Log.d("HomeRecyclerViewAdapter", "onClick: "+
                    BoardActivity.channel + " / " +
                    boardIdx.getText().toString()+":"+
                    boardTitle.getText().toString()+ "|" +
                    boardName.getText().toString());
        }

    }
    public class splashhandler implements Runnable {
        JSONTask jt;
        public splashhandler(JSONTask jt) {
            this.jt = jt;
        }

        @Override
        public void run() {
            if (jt.jsonReturn().equals("")){
                jsonResult = "network_error";
                return;
            }
            try {
                JSONObject jo = new JSONObject(jt.jsonReturn());
                if (!jo.getString("token").isEmpty()){
                    Intent intent = new Intent();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("login.java", "result: "+jt.jsonReturn());
            jsonResult = jt.jsonReturn();
        }
    }
}