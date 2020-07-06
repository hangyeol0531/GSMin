package com.example.gsmin.Model;

public class Data {
    private static String[] jsonData, jsonId;
    public static String url = "http://10.53.68.184";
    public static String UserEmail = "";
    public static String UserName = "";
    public static int UserGrade = 0;
    public static String token = "";

    public static void clear(){
        UserEmail = "";
        UserName = "";
        token = "";
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
        return jsonData.length;
    }
}
