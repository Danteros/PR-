package com.electric.handbook.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.electric.handbook.R;
import com.electric.handbook.activities.ActivityMain;
import com.electric.handbook.settings.Settings;

import info.hoang8f.android.segmented.SegmentedGroup;

public class FragmentAbout  extends Fragment implements RadioGroup.OnCheckedChangeListener, View.OnClickListener{

    private WebView webView;
    private TextView buy;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_about, container, false);
        webView = (WebView) view.findViewById(R.id.frmAboutWebView);
        SegmentedGroup segmentedGroup = (SegmentedGroup) view.findViewById(R.id.frmAboutSegmentGroup);
        segmentedGroup.setTintColor(Color.parseColor("#5294c3"), Color.WHITE);
        segmentedGroup.setOnCheckedChangeListener(this);
        webView.loadUrl("file:///android_asset/temp/about.html");

        view.findViewById(R.id.frmAboutSetComment).setOnClickListener(this);
        buy = (TextView) view.findViewById(R.id.frmAboutBuy);
        if (Settings.isBuyed())
            isBayed();
        else
            buy.setOnClickListener(this);

        return view;
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.frmAboutSegment1:
                webView.loadUrl("file:///android_asset/temp/about.html");
                break;
            case R.id.frmAboutSegment2:
                webView.loadUrl("file:///android_asset/temp/responsobility.html");
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.frmAboutBuy:
                buy.setEnabled(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        buy.setEnabled(true);
                    }
                }, 3000);
                ((ActivityMain) getActivity()).buy();
                break;
            case R.id.frmAboutSetComment:
                final String appPackageName = getActivity().getPackageName();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
                break;
        }
    }

    public void isBayed() {
        try {
            buy.setVisibility(View.GONE);
        } catch (NullPointerException ignored){}
    }
}
