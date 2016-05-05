package com.electric.handbook.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.electric.handbook.R;
import com.electric.handbook.fragments.FragmentAbout;
import com.electric.handbook.fragments.FragmentAuto;
import com.electric.handbook.fragments.FragmentCalcs;
import com.electric.handbook.fragments.FragmentElectric;
import com.electric.handbook.fragments.FragmentNews;
import com.electric.handbook.fragments.FragmentStock;
import com.electric.handbook.settings.Settings;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.HashMap;
import java.util.List;


public class ActivityMain extends AppCompatActivity implements ViewPager.OnPageChangeListener, TabHost.OnTabChangeListener, BillingProcessor.IBillingHandler {
    private static final int FRAGMENT_ABOUT_POS = 5;
    private static String[] PAGE_NAMES;
    private ViewPager viewPager;
    private TabHost host;
    private ColorStateList csl;
    private static final int NUM_ITEMS = 6;
    private MPagerAdapter adapter;
    private AdView mAdView;
    private String remove_banner = "remove_banner";
    private BillingProcessor bp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PAGE_NAMES = getResources().getStringArray(R.array.page_names);
        // Стиль для текста
        XmlResourceParser xrp = getResources().getXml(R.xml.tab_text_selector);
        try {
            csl = ColorStateList.createFromXml(getResources(), xrp);
        } catch (Exception ignored) {
        }


        host = (TabHost) findViewById(android.R.id.tabhost);
        host.setup();
        setupTab(0);
        setupTab(1);
        setupTab(2);
        setupTab(3);
        setupTab(4);
        setupTab (5);
        host.setOnTabChangedListener(this);

        Settings.init(this);
        viewPager = (ViewPager) findViewById(R.id.pager);
        mAdView = (AdView) findViewById(R.id.adView);

        adapter = new MPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);
        viewPager.setOffscreenPageLimit(NUM_ITEMS);
        viewPager.setCurrentItem(0);
        getSupportActionBar().setTitle(PAGE_NAMES[0]);
        bp = new BillingProcessor(this, null, this);
        if (!Settings.isBuyed())
            bp.loadOwnedPurchasesFromGoogle();
    }

    public void setLoginPass(View v) {

        new CustomDialogFragment().show(getSupportFragmentManager(),
                "login");}
    public void addBox(View v) {

        new DialogAdd().show(getSupportFragmentManager(),
                "login");}
    public void registrator(View v) {

        new DialogRegister().show(getSupportFragmentManager(),
                "login");}





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(this, ActivityWebView.class)
                .putExtra(ActivityWebView.EXTRA_TITLE, "Ответственность")
                .putExtra(ActivityWebView.EXTRA_PATH_PAGE, "temp/page"));
        return super.onOptionsItemSelected(item);
    }

    private void setupTab(int page) {
        View view = LayoutInflater.from(this).inflate(R.layout.cell_tab, null);
        TextView tv = (TextView) view.findViewById(R.id.cellTabText);
        tv.setText(PAGE_NAMES[page]);
        tv.setTextColor(csl);
        switch (page) {
            case 0:
                ((ImageView) view.findViewById(R.id.cellTabIc)).setImageResource(R.drawable.selector_ic_tab_news);
                break;
            case 1:
                ((ImageView) view.findViewById(R.id.cellTabIc)).setImageResource(R.drawable.selector_ic_tab_automation);
                break;
            case 2:
                ((ImageView) view.findViewById(R.id.cellTabIc)).setImageResource(R.drawable.selector_ic_tab_electro);
                break;
            case 3:
                ((ImageView) view.findViewById(R.id.cellTabIc)).setImageResource(R.drawable.selector_ic_tab_calc);
                break;
            case 5:
                ((ImageView) view.findViewById(R.id.cellTabIc)).setImageResource(R.drawable.selector_ic_tab_about);
                break;
            case 4:
                ((ImageView) view.findViewById(R.id.cellTabIc)).setImageResource(R.drawable.selector_ic_tab_calc);
                break;
        }
        TabHost.TabSpec setContent = host.newTabSpec("" + page).setIndicator(view).setContent(new TabFactory(this));
        host.addTab(setContent);
    }

    @Override
    public void onTabChanged(String s) {
        viewPager.setCurrentItem(host.getCurrentTab());
    }

    class TabFactory implements TabHost.TabContentFactory {

        private final Context mContext;

        public TabFactory(Context context) {
            mContext = context;
        }

        public View createTabContent(String tag) {
            View v = new View(mContext);
            v.setMinimumWidth(0);
            v.setMinimumHeight(0);
            return v;
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
    }

    @Override
    public void onPageSelected(int i) {
        getSupportActionBar().setTitle(PAGE_NAMES[i]);
        host.setCurrentTab(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {
    }


    public static class MPagerAdapter extends FragmentPagerAdapter {

        private HashMap<Integer, Fragment> cache;

        public MPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            cache = new HashMap<>();
            cache.put(0, new FragmentNews());
            cache.put(1, new FragmentAuto());
            cache.put(2, new FragmentElectric());
            cache.put(3, new FragmentCalcs());
            cache.put(5, new FragmentAbout());
            cache.put(4, new FragmentStock());
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            return cache.get(position);
        }
    }

    public void removeBanner() {
        mAdView.destroy();
        mAdView.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        if (isConnect() && !Settings.isBuyed()) {
            mAdView.setVisibility(View.VISIBLE);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }
        super.onResume();
    }

    private boolean isConnect() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();
        return !(nInfo == null || !nInfo.isConnected());
    }

    public void buy() {
        bp.purchase(this, remove_banner);
    }

    @Override
    public void onProductPurchased(String s, TransactionDetails transactionDetails) {
        if (s.equalsIgnoreCase(remove_banner)) {
            Settings.setIsBuyed(true);
            removeBanner();
            ((FragmentAbout) adapter.getItem(FRAGMENT_ABOUT_POS)).isBayed();
        }
    }

    @Override
    public void onPurchaseHistoryRestored() {
        List<String> data = bp.listOwnedProducts();
        if (data.contains(remove_banner)) {
            Settings.setIsBuyed(true);
            removeBanner();
            ((FragmentAbout) adapter.getItem(FRAGMENT_ABOUT_POS)).isBayed();
        }
    }

    @Override
    public void onBillingError(int i, Throwable throwable) {
        Toast.makeText(this, "Ошибка", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBillingInitialized() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data))
            super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        if (bp != null)
            bp.release();

        super.onDestroy();
    }

}
