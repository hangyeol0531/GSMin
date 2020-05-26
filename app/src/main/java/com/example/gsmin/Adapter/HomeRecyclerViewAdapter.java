package com.example.gsmin.Adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gsmin.Main.BoardActivity;
import com.example.gsmin.Main.WriteActivity;
import com.example.gsmin.Model.DB;
import com.example.gsmin.R;

import java.util.ArrayList;


public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.ItemViewHolder> {
    private String[] strli;
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

    public void addItem(DB data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);
    }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView boardName, boardTitle, boardInfo, boardThumb, boardMsg;
        private LinearLayout mLayout;

//        public Map<String, Object> toMap(View view) {
//            HashMap<String, Object> result = new HashMap<>();
//            result.put("boardTitle", boardTitle);
//            result.put("boardName", boardName);
//            result.put("boardInfo", boardInfo);
//            result.put("boardThumb", boardThumb);
//            result.put("boardMsg", boardMsg);
//            return result;
//        }

        ItemViewHolder(View itemView) {
            super(itemView);

            mLayout = itemView.findViewById(R.id.mainLayout);
            boardName = itemView.findViewById(R.id.boardName);
            boardTitle = itemView.findViewById(R.id.boardTitle);
            boardInfo = itemView.findViewById(R.id.boardInfo);
            boardThumb = itemView.findViewById(R.id.t_cnt_1);
            boardMsg = itemView.findViewById(R.id.c_cnt_1);

            mLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("Layout_Click", "onClick: "+ BoardActivity.channel + " / " + boardTitle.getText().toString());
//                Data.setData(new String[]{"channel", "title"}, new String[]{BoardActivity.channel,  boardTitle.getText().toString()});
//                new JSONTask().execute("@string/serverUrl"+"/emailCheck");//AsyncTask 시작시킴
//                    Intent intent = new Intent(view.getContext(), WriteActivity.class);
//                    intent.putExtra("title", boardTitle.getText().toString());
//                    view.getContext().startActivity(intent);
                }
            });
        }

        void onBind(DB db) {
            strli = db.getBoardData();
            boardTitle.setText(strli[0]);
            boardName.setText(strli[1]);
            boardInfo.setText(strli[2]);
            boardThumb.setText(strli[3]);
            boardMsg.setText(strli[4]);
        }
    }
}