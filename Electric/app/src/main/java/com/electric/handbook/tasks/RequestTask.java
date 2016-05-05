package com.electric.handbook.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RequestTask extends AsyncTask<String, Void, String> {

    private static final String TAG = RequestTask.class.getSimpleName();
    private static final int SEND_TIMEOUT = 30000;
    Context context;
    JSONObject jsonObject;
    private boolean isNeedDialog = true;
    ProgressDialog progressDialog;
    RequestType requestType = RequestType.POST;
    private OnRequestObtainedListener mRequestObtainedListener;
    private OnRequestErrorListener mRequestErrorListener;

    public static class RequestTaskBuilder {
        Context context;
        JSONObject jsonObject;

        RequestType requestType = RequestType.POST;
        private OnRequestObtainedListener mRequestObtainedListener;
        private OnRequestErrorListener mRequestErrorListener = new OnRequestErrorListener() {
            @Override
            public void onRequestError() {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
            }
        };

        RequestTaskBuilder(Context context, OnRequestObtainedListener mRequestObtainedListener,
                           JSONObject jsonObject) {
            this.context = context;
            this.mRequestObtainedListener = mRequestObtainedListener;
            this.jsonObject = jsonObject;
        }

        public RequestTask buildRequestTask() {
            return new RequestTask(context, mRequestObtainedListener, mRequestErrorListener, jsonObject, requestType);
        }

        public RequestTaskBuilder requestType(RequestType type) {
            this.requestType = type;
            return this;
        }

        public RequestTaskBuilder errorListener(OnRequestErrorListener listener) {
            this.mRequestErrorListener = listener;
            return this;
        }


    }



    public RequestTask(Context context, OnRequestObtainedListener mRequestObtainedListener, OnRequestErrorListener mRequestErrorListener,
                       JSONObject jsonObject, RequestType type) {
        super();
        this.context = context;
        this.mRequestObtainedListener = mRequestObtainedListener;
        this.mRequestErrorListener = mRequestErrorListener;
        this.jsonObject = jsonObject;
        requestType = type;
    }

    public void setNeedDialog(boolean isNeedDialog) {
        this.isNeedDialog = isNeedDialog;
    }

    @Override
    protected void onPreExecute() {
        if (isNeedDialog) {
            /*progressDialog = new ProgressDialog(context);
            progressDialog.setMessage(context.getString(R.string.loading));
            progressDialog.setCancelable(false);
            progressDialog.show();*/
        }
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... str) {
        String urlString = str[0];
        //Log.e(TAG, urlString);
        try {
            HttpResponse response = null;
            HttpParams httpParameters;
            HttpClient client;

            switch (requestType) {
                case GET:
                    httpParameters = new BasicHttpParams();
                    HttpConnectionParams.setConnectionTimeout(httpParameters, SEND_TIMEOUT);
                    HttpConnectionParams.setSoTimeout(httpParameters, SEND_TIMEOUT);
                    client = new DefaultHttpClient(httpParameters);
                    HttpGet get = new HttpGet(urlString);
                    get.setHeader("Accept", "application/json");
                    get.setHeader("Content-type", "application/json");
                    // Log.e(TAG, jsonObject.toString());
                    // get.setEntity(new StringEntity(jsonObject.toString(), "utf-8"));
                    response = client.execute(get);
                    break;
                case POST:
                    httpParameters = new BasicHttpParams();
                    HttpConnectionParams.setConnectionTimeout(httpParameters, SEND_TIMEOUT);
                    HttpConnectionParams.setSoTimeout(httpParameters, SEND_TIMEOUT);
                    client = new DefaultHttpClient(httpParameters);
                    HttpPost post = new HttpPost(urlString);
                    post.setHeader("Accept", "application/json");
                    post.setHeader("Content-type", "application/json");
                    //Log.e(TAG, jsonObject.toString());
                    post.setEntity(new StringEntity(jsonObject.toString(), "utf-8"));
                    response = client.execute(post);
                    break;
                case PUT:
                    httpParameters = new BasicHttpParams();
                    HttpConnectionParams.setConnectionTimeout(httpParameters, SEND_TIMEOUT);
                    HttpConnectionParams.setSoTimeout(httpParameters, SEND_TIMEOUT);
                    client = new DefaultHttpClient(httpParameters);
                    HttpPut put = new HttpPut(urlString);
                    put.setHeader("Accept", "application/json");
                    put.setHeader("Content-type", "application/json");
                    //Log.e(TAG, jsonObject.toString());
                    put.setEntity(new StringEntity(jsonObject.toString(), "utf-8"));
                    response = client.execute(put);
                    break;
                case DELETE:
                    httpParameters = new BasicHttpParams();
                    HttpConnectionParams.setConnectionTimeout(httpParameters, SEND_TIMEOUT);
                    HttpConnectionParams.setSoTimeout(httpParameters, SEND_TIMEOUT);
                    client = new DefaultHttpClient(httpParameters);
                    HttpDelete delete = new HttpDelete(urlString);
                    delete.setHeader("Accept", "application/json");
                    delete.setHeader("Content-type", "application/json");
                    // Log.e(TAG, jsonObject.toString());
                    // get.setEntity(new StringEntity(jsonObject.toString(), "utf-8"));
                    response = client.execute(delete);
                    break;
            }

            InputStream is = response.getEntity().getContent();
            StringBuilder strBuilder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = null;

            while ((line = reader.readLine()) != null) {
                strBuilder.append(line);
            }
            //Log.e(TAG, strBuilder.toString() + "");

            return strBuilder.toString() + "";

        } catch (Exception ex) {
            Log.e(TAG, Log.getStackTraceString(ex));
        }
        return "";
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            //Log.e(TAG, "onPostExecute");
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        } catch (Exception ex) {
            Log.e(TAG, Log.getStackTraceString(ex));
        }
        if (isJSONValid(result))
            mRequestObtainedListener.onRequestObtained(result);
        else {
            mRequestErrorListener.onRequestError();
            Log.e(TAG,result+"");
        }
        super.onPostExecute(result);
    }

    public interface OnRequestObtainedListener {
        public void onRequestObtained(String response);
    }

    public interface OnRequestErrorListener {
        public void onRequestError();
    }

    public boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

    public enum RequestType {
        POST, GET, PUT, DELETE
    }
}
