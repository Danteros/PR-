package com.electric.handbook.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.electric.handbook.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class ActivityOnlineEngineer extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = ActivityOnlineEngineer.class.getSimpleName();
    private static final String DEFAULT_PHONE = "12345";
    EditText etName;
    EditText etEmail;
    EditText etComment;
    TextView tvSend;
    TextView tvDescription;
    ProgressDialog progressDialog;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_engineer);
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
        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etComment = (EditText) findViewById(R.id.etComment);
        tvSend = (TextView) findViewById(R.id.tvSend);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
    }

    private void initGlobal() {
        etName.addTextChangedListener(new GenericTextWatcher(etName));
        etEmail.addTextChangedListener(new GenericTextWatcher(etEmail));
        etComment.addTextChangedListener(new GenericTextWatcher(etComment));
        tvSend.setOnClickListener(this);
        String more = getString(R.string.title_more);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getString(R.string.online_engineer_description));
        stringBuilder.append(" ").append(more);
        SpannableString text = new SpannableString(stringBuilder.toString());
        int pos = stringBuilder.toString().indexOf(more);
        text.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityOnlineEngineer.this, ActivityWebView.class)
                        .putExtra(ActivityWebView.EXTRA_TITLE, getString(R.string.online_engineer_help))
                        .putExtra(ActivityWebView.EXTRA_PATH_PAGE, "help/index"));
            }
        }, pos, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setSpan(new ForegroundColorSpan(Color.BLUE), pos, text.length(),  Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvDescription.setText(text);
        tvDescription.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvSend:
                send();
                break;
        }
    }

    private void send() {
        OrderTask task = new OrderTask();
        task.execute("");
    }

    private class GenericTextWatcher implements TextWatcher {

        private View view;

        private GenericTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        public void afterTextChanged(Editable editable) {
            if (isDataFilled()) {
                tvSend.setVisibility(View.VISIBLE);
            } else {
                tvSend.setVisibility(View.INVISIBLE);
            }
        }
    }

    private boolean isDataFilled() {
        if (etName.getText().toString().trim().length() == 0) return false;
        if (etEmail.getText().toString().trim().length() == 0) return false;
        if (etComment.getText().toString().trim().length() == 0) return false;
        return true;
    }

    private List<BasicNameValuePair> createPairs() {
        List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String comment = etComment.getText().toString().trim();
        pairs.add(new BasicNameValuePair("fio", name));
        pairs.add(new BasicNameValuePair("phone", DEFAULT_PHONE));
        pairs.add(new BasicNameValuePair("email", email));
        pairs.add(new BasicNameValuePair("comment", comment));
        return pairs;
    }

    class OrderTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(context);
            if (progressDialog == null) {
                progressDialog.setIndeterminate(true);
            }

            progressDialog.setMessage("Загрузка");
            progressDialog.setCancelable(false);
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            publishProgress(new String[]{});
            try {
                // Create a new HttpClient and Post Header
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(getString(R.string.request_online_engineer));

                // creating POST data
                List<BasicNameValuePair> pairs = createPairs();
                httpPost.setEntity(new UrlEncodedFormEntity(pairs, "UTF-8"));

                // Execute HTTP Post Request
                HttpResponse response = null;
                HttpEntity entity = null;
                try {
                    response = httpClient.execute(httpPost);
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


            } catch (UnsupportedEncodingException e) {
                Log.e(TAG, Log.getStackTraceString(e));
                return "";
            }

            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
            try {
                JSONObject jsonObject = new JSONObject(result);
                Log.e(TAG, jsonObject.toString());
                String answer = jsonObject.optString("result");
                if (answer.equalsIgnoreCase("ok")) {
                    Toast.makeText(context, jsonObject.optString("message"), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, jsonObject.optString("errors"), Toast.LENGTH_LONG).show();
                }
            } catch (Exception ex) {
                Log.e(TAG, Log.getStackTraceString(ex));
                Toast.makeText(context, R.string.error_connection, Toast.LENGTH_LONG).show();
            }
            etName.setText("");
            etEmail.setText("");
            etComment.setText("");


        }
    }


}
