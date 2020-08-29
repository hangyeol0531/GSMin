package com.example.gsmin.Model;

import android.graphics.Bitmap;
import android.media.Image;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class DB {
    private String boardTitle, boardName, boardInfo, boardThumb, boardMsg, boardIdx;
    private String chatName, chatInfo, chatThumb, chatContent, chatGrade;
    public String[] getBoardData() {
        return new String[]{boardTitle, boardName, boardInfo, boardThumb, boardMsg, boardIdx};
    }
    public void setBoardData(String boardTitle, String boardName, String boardInfo, String boardThumb, String boardMsg, String boardIdx) {
        this.boardTitle = boardTitle;
        this.boardName = boardName;
        this.boardInfo = boardInfo;
        this.boardThumb = boardThumb;
        this.boardMsg = boardMsg;
        this.boardIdx = boardIdx;
    }
    public String[] getChatData() {
        return new String[]{chatName, chatInfo, chatThumb, chatContent, chatGrade};
    }
    public void setChatData(String chatName, String chatInfo, String chatThumb, String chatContent, String chatGrade) {
        this.chatName = chatName;
        this.chatInfo = chatInfo;
        this.chatThumb = chatThumb;
        this.chatContent = chatContent;
        this.chatGrade = chatGrade;
    }
}
