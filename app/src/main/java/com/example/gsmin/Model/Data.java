package com.example.gsmin.Model;

public class Data {
    private static String[] jsonData, jsonId;
    public static String url = "http://15.164.212.158:3000";

    public void init(){
        jsonData = null;
        jsonId = null;
    };

    public static void setData(String[] s1, String[] s2){
        jsonId = s1;
        jsonData = s2;
    }

    public static String[] getData(){
        return jsonData;
    }

    public static String[] getId(){
        return jsonId;
    }

    public static int getIdx(){
        return jsonData.length;
    }
}
