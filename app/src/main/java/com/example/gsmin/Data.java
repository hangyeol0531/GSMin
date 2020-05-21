package com.example.gsmin;

public class Data {
    private static String[] jsonData, jsonId;

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
