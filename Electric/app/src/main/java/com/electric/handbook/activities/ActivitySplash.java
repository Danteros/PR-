package com.electric.handbook.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.electric.handbook.R;



public class ActivitySplash extends Activity{

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_splash);
        super.onCreate(savedInstanceState);

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                finish();
                startActivity(new Intent(ActivitySplash.this, ActivityMain.class));
                return false;
            }
        });
        handler.sendEmptyMessageDelayed(0, 3000);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (handler != null)
            handler.removeMessages(0);
    }
}
