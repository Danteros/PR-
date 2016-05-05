package com.electric.handbook.activities;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Администратор on 14.01.2016.
 */
public class JsonParse extends AsyncTask<Void, Void, String> {

    public static String LOG_TAG = "my_log";

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";

@Override
protected String doInBackground(Void... params) {
        // получаем данные с внешнего ресурса
        try {
        URL url = new URL("http://electrica.dev-xelentec.com:1337/api/user/auth");

        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();

        InputStream inputStream = urlConnection.getInputStream();
        StringBuffer buffer = new StringBuffer();

        reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        while ((line = reader.readLine()) != null) {
        buffer.append(line);
        }

        resultJson = buffer.toString();

        } catch (Exception e) {
        e.printStackTrace();
        }
        return resultJson;
        }

@Override
protected void onPostExecute(String strJson) {
        super.onPostExecute(strJson);
        // выводим целиком полученную json-строку
        Log.d(LOG_TAG, strJson);

        JSONObject dataJsonObj = null;
        String secondName = "";

        try {
        dataJsonObj = new JSONObject(strJson);
            JSONObject data = dataJsonObj.getJSONObject("data");



        String id = data.getString("id");

        Log.d(LOG_TAG, "id: " + id);



        } catch (JSONException e) {
        e.printStackTrace();
        }
        }
        }
