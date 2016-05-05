package com.electric.handbook.tasks;

import android.content.Context;
import android.util.Log;

import com.electric.handbook.R;


public class BaseRequests {
    private static final String TAG = BaseRequests.class.getName();
    public static final int FIRST_ATTEMPT = 0;
    public static final int ATTEMPT_COUNT = 3;
    public static final int NEW_GROUP_ID = -1;
    private Context context;


    public BaseRequests(Context context) {
        this.context = context;

    }



    /*public void userDevice(final String deviceToken,  final RequestTask.OnRequestObtainedListener listener, final int attemptNumber) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", deviceToken);
            jsonObject.put("_format", "json");
            Log.e(TAG, jsonObject.toString());
            RequestTask regTask = new RequestTask.RequestTaskBuilder(context, listener, jsonObject)
                    .errorListener(new RequestTask.OnRequestErrorListener() {
                        @Override
                        public void onRequestError() {
                            int newAttemptNumber = attemptNumber;
                            newAttemptNumber++;
                            if (newAttemptNumber < ATTEMPT_COUNT)
                                userDevice(deviceToken, listener, newAttemptNumber);
                            else
                                Toast.makeText(context, "Error. Attempt=" + newAttemptNumber, Toast.LENGTH_SHORT).show();
                        }
                    })
                    .buildRequestTask();
            String urlString = context.getString(R.string.server_name) +
                    String.format(context.getString(R.string.request_news));
            Log.e(TAG, urlString);
            regTask.execute(urlString);
        } catch (Exception ex) {
            Log.e(TAG, Log.getStackTraceString(ex));
        }
    }*/
    public void receiveNews(final RequestTask.OnRequestObtainedListener listener, final RequestTask.OnRequestErrorListener mErrorListener, final int attemptNumber) {
        try {
            RequestTask regTask = new RequestTask.RequestTaskBuilder(context, listener, null).requestType(RequestTask.RequestType.GET)
                    .errorListener(new RequestTask.OnRequestErrorListener() {
                        @Override
                        public void onRequestError() {
                            int newAttemptNumber = attemptNumber;
                            newAttemptNumber++;
                            if (newAttemptNumber < ATTEMPT_COUNT)
                                receiveNews(listener, mErrorListener, newAttemptNumber);
                            else {
                                mErrorListener.onRequestError();
                            }
                        }
                    })
                    .buildRequestTask();
            String urlString = context.getString(R.string.server_name) +
                    String.format(context.getString(R.string.request_news));
            Log.e(TAG, urlString);
            regTask.setNeedDialog(false);
            regTask.execute(urlString);
        } catch (Exception ex) {
            Log.e(TAG, Log.getStackTraceString(ex));
        }
    }


}
