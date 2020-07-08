package com.example.gsmin.Model;

public class Data {
    private static String[] jsonData, jsonId;
    public static String url = "http://10.53.68.186";
    public static String UserEmail = "";
    public static String UserName = "";
    public static int UserGrade = 0;
    public static String token = "";
    public static String type = "POST";

    public static void clear(){
        UserEmail = "";
        UserName = "";
        token = "";
        type = "POST";
    }

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
        if (jsonData == null) return 0;
        return jsonData.length;
    }
}
