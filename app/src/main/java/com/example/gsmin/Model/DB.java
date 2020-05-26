package com.example.gsmin.Model;

import android.graphics.Bitmap;
import android.media.Image;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class DB {
    private String boardTitle, boardName, boardInfo, boardThumb, boardMsg;

    public String[] getBoardData() {
        return new String[]{boardTitle, boardName, boardInfo, boardThumb, boardMsg};
    }

    public void setBoardData(String boardTitle, String boardName, String boardInfo, String boardThumb, String boardMsg) {
        this.boardTitle = boardTitle;
        this.boardName = boardName;
        this.boardInfo = boardInfo;
        this.boardThumb = boardThumb;
        this.boardMsg = boardMsg;
    }
}
