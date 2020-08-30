package com.example.gsmin.Adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gsmin.Json.JSONTask;
import com.example.gsmin.Main.BoardActivity;
import com.example.gsmin.Main.BulletinActivity;
import com.example.gsmin.Model.DB;
import com.example.gsmin.Model.Data;
import com.example.gsmin.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class ChatRecyclerViewAdapter extends RecyclerView.Adapter<ChatRecyclerViewAdapter.ItemViewHolder> {
    private static String[] strli;
    private static TextView chatName, chatInfo, chatThumb, chatContent;
    private static ImageView chatGrade;
    public static String jsonResult = "";
    // adapter에 들어갈 list 입니다.
    private ArrayList<DB> listData = new ArrayList<>();

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
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

    public void addItem(DB data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);
    }
    public void clear() {
        int size = getItemCount();
        listData.clear();
        notifyItemRangeRemoved(0, size);
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder {
        ItemViewHolder(final View itemView) {
            super(itemView);
            chatContent = itemView.findViewById(R.id.chatContent);
            chatInfo = itemView.findViewById(R.id.chatInfo);
            chatName = itemView.findViewById(R.id.chatName);
            chatThumb = itemView.findViewById(R.id.chat_thumb_cnt);
            chatGrade = itemView.findViewById(R.id.chatGrade);
        }

        void onBind(DB db) {
            strli = db.getChatData();
            chatName.setText(strli[0]);
            chatInfo.setText(strli[1]);
            chatThumb.setText(strli[2]);
            chatContent.setText(strli[3]);
            switch (strli[4]){
                case "1" : chatGrade.setImageResource(R.drawable.one_icon);break;
                case "2" :chatGrade.setImageResource(R.drawable.two_icon);break;
                case "3" :chatGrade.setImageResource(R.drawable.three_icon);break;
                default: chatGrade.setImageResource(R.drawable.grad_icon);break;
            }
//            chatThumb.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    SweetAlertDialog sd = new SweetAlertDialog(itemView.getContext(), SweetAlertDialog.ERROR_TYPE);
//                    sd.setTitleText(strli[0]);
//                    sd.setContentText(strli[3]);
//                    sd.show();
//                }
//            });
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