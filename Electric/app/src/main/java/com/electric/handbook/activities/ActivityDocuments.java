package com.electric.handbook.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.electric.handbook.R;
import com.electric.handbook.dialogs.SingleSelectDialog;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;


public class ActivityDocuments extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = ActivityDocuments.class.getSimpleName();


    TextView tvSend;
    TextView tvTypeOfDocument;
    TextView tvViewOfDocument;
    TextView tvDocument;
    Context context;
    String typeId = "";
    String viewId = "";
    String documentId = "";
    String documentName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;
        initUI();
        initGlobal();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

    private void initUI() {
        tvTypeOfDocument = (TextView) findViewById(R.id.tvTypeOfDocument);
        tvViewOfDocument = (TextView) findViewById(R.id.tvViewOfDocument);
        tvDocument = (TextView) findViewById(R.id.tvDocument);
        tvSend = (TextView) findViewById(R.id.tvSend);
    }

    private void initGlobal() {
        tvTypeOfDocument.setOnClickListener(this);
        tvViewOfDocument.setOnClickListener(this);
        tvDocument.setOnClickListener(this);
        tvSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvTypeOfDocument:
                showDocumentTypes();
                break;
            case R.id.tvViewOfDocument:
                showDocumentViews(typeId);
                break;
            case R.id.tvDocument:
                showDocuments(typeId, viewId);
                break;
            case R.id.tvSend:
                startActivity(new Intent(this, ActivityWebView.class)
                        .putExtra(ActivityWebView.EXTRA_TITLE, documentName)
                        .putExtra(ActivityWebView.EXTRA_FULL_PATH, true)
                        .putExtra(ActivityWebView.EXTRA_PATH_PAGE, documentId));
                break;
        }

    }


    private void showDocumentTypes() {
        try {
            JSONObject jsonObject = new JSONObject(readFromAssets("document.json"));
            ArrayList<String> items = new ArrayList<>();
            for (Iterator<String> iter = jsonObject.keys(); iter.hasNext(); ) {
                String key = iter.next();
                items.add(key);


            }
            final String[] docTypes = new String[items.size()];
            for (int i = 0; i < items.size(); ++i) {
                docTypes[i] = items.get(i);
            }
            SingleSelectDialog singleSelectDialog = new SingleSelectDialog(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    typeId = docTypes[position];
                    tvTypeOfDocument.setText(typeId);
                    tvViewOfDocument.setText("");
                    tvDocument.setText("");
                    viewId = "";
                    documentId = "";
                    tvSend.setVisibility(View.INVISIBLE);
                }
            });
            singleSelectDialog.show(this, getString(R.string.title_type_of_document), docTypes);
        } catch (Exception ex) {
            Log.e(TAG, Log.getStackTraceString(ex));
        }
    }

    private void showDocumentViews(String documentType) {
        try {
            if (documentType != null && documentType.length() > 0) {
                JSONObject jsonObject = new JSONObject(readFromAssets("document.json"));
                JSONObject typeObject = jsonObject.optJSONObject(documentType);
                if (typeObject != null) {
                    ArrayList<String> items = new ArrayList<>();
                    for (Iterator<String> iter = typeObject.keys(); iter.hasNext(); ) {
                        String key = iter.next();
                        items.add(key);
                    }
                    final String[] viewTypes = new String[items.size()];
                    for (int i = 0; i < items.size(); ++i) {
                        viewTypes[i] = items.get(i);
                    }
                    SingleSelectDialog singleSelectDialog = new SingleSelectDialog(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                            viewId = viewTypes[position];
                            tvViewOfDocument.setText(viewId);
                            tvDocument.setText("");
                            documentId = "";
                            tvSend.setVisibility(View.INVISIBLE);
                        }
                    });
                    singleSelectDialog.show(this, getString(R.string.title_view_of_document), viewTypes);
                }
            }
        } catch (Exception ex) {
            Log.e(TAG, Log.getStackTraceString(ex));
        }
    }

    private void showDocuments(String documentType, String documentView) {
        try {
            if (documentType != null && documentType.length() > 0) {
                JSONObject jsonObject = new JSONObject(readFromAssets("document.json"));
                JSONObject typeObject = jsonObject.optJSONObject(documentType);
                if (typeObject != null) {
                    JSONObject documentsObject = typeObject.optJSONObject(documentView);
                    if (documentsObject != null) {
                        ArrayList<String> items = new ArrayList<>();
                        final ArrayList<String> pathItems = new ArrayList<>();
                        for (Iterator<String> iter = documentsObject.keys(); iter.hasNext(); ) {
                            String key = iter.next();
                            String path = documentsObject.optString(key);
                            items.add(key);
                            pathItems.add(path);
                        }
                        final String[] documentNames = new String[items.size()];
                        for (int i = 0; i < items.size(); ++i) {
                            documentNames[i] = items.get(i);
                        }
                        SingleSelectDialog singleSelectDialog = new SingleSelectDialog(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                                documentName = documentNames[position];
                                tvDocument.setText(documentName);
                                documentId = pathItems.get(position);
                                Log.e(TAG, documentId + "");
                                tvSend.setVisibility(View.VISIBLE);
                            }
                        });
                        singleSelectDialog.show(this, getString(R.string.title_document), documentNames);
                    }
                }
            }
        } catch (Exception ex) {
            Log.e(TAG, Log.getStackTraceString(ex));
        }
    }

    public String readFromAssets(String name) {
        AssetManager assetManager = getResources().getAssets();
        try {
            InputStream inputStream = assetManager.open(name);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = "";
            StringBuilder strBuilder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                strBuilder.append(line);
            }
            //Log.e(TAG, strBuilder.toString());
            return strBuilder.toString();
        } catch (Exception ex) {
            Log.e(TAG, Log.getStackTraceString(ex));
            return "";
        }
    }


}
