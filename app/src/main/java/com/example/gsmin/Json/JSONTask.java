package com.example.gsmin.Json;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

//import com.example.gsmin.Fragment.HomeFragment;
import com.example.gsmin.Main.InfoActivity;
import com.example.gsmin.Main.TitleActivity;
import com.example.gsmin.Model.Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JSONTask extends AsyncTask<String, String, String> {
    private static String jsonResult = "";
    @Override
    protected String doInBackground(String... urls) {
        try {
            //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
            JSONObject jsonObject = new JSONObject();
            String[] data = Data.getData();
            String[] id = Data.getId();
            Log.d("idx", "doInBackground: "+Data.getIdx());
            for (int i = 0; i < Data.getIdx(); i++){
                jsonObject.accumulate(id[i], data[i]);
            }

            HttpURLConnection con = null;
            BufferedReader reader = null;
            Log.d("JSON_ON", jsonObject.toString());
            try{
                URL url = new URL(urls[0]);
                con = (HttpURLConnection) url.openConnection();

                if (Data.type.equals("POST")){
                    con.setRequestMethod("POST");//POST방식으로 보냄
                    con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
                    con.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송


                    con.setRequestProperty("Accept", "text/html");//서버에 response 데이터를 html로 받음
                    con.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
                    con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미
                }

                con.connect();

                if (Data.type.equals("POST")) {
                    //서버로 보내기위해서 스트림 만듬
                    OutputStream outStream = con.getOutputStream();
                    //버퍼를 생성하고 넣음
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                    writer.write(jsonObject.toString());
                    writer.flush();
                    writer.close();//버퍼를 받아줌
                }
                //서버로 부터 데이터를 받음
                InputStream stream = con.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line = "";
                while((line = reader.readLine()) != null){
                    buffer.append(line);
                }

                return buffer.toString();//서버로 부터 받은 값을 리턴해줌

            } catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(con != null){
                    con.disconnect();
                }
                try {
                    if(reader != null){
                        reader.close();//버퍼를 닫아줌
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        if (result == null){
            Log.d("null", "onPostExecute: "+result);
            return;
        }
//        String dataKey = "";
        Log.d("test node", "onPostExecute: "+result);
        jsonResult = result;
        // test / anything to pass

    }

    public String jsonReturn(){
        return jsonResult;
    }
}