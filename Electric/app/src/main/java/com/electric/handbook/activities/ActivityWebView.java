package com.electric.handbook.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.electric.handbook.R;



public class ActivityWebView extends AppCompatActivity {

    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_PATH_PAGE = "path";
    public static final String EXTRA_FULL_PATH = "full_path";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra(EXTRA_TITLE));

        WebView webView = (WebView) findViewById(R.id.webview);
        if (getIntent().getBooleanExtra(EXTRA_FULL_PATH, false)) {
            WebSettings settings = webView.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setBuiltInZoomControls(true);
            webView.loadUrl("http://docs.google.com/gview?embedded=true&url="+getIntent().getStringExtra(EXTRA_PATH_PAGE));
        } else {
            webView.loadUrl(getUrlPage(getIntent().getStringExtra(EXTRA_PATH_PAGE)));
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

    private String getUrlPage(String key) {//TODO Forms url address
        return "file:///android_asset/" + key + ".html";
    }

}
