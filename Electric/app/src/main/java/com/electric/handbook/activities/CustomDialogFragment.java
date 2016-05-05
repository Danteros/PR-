package com.electric.handbook.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.electric.handbook.R;

public class CustomDialogFragment extends DialogFragment implements
        DialogInterface.OnClickListener {
    private View form=null;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        form= getActivity().getLayoutInflater()
                .inflate(R.layout.loginform, null);
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        return(builder.setTitle("Форма авторизации").setView(form)
                .setPositiveButton(android.R.string.ok, this)
                .setNegativeButton(android.R.string.cancel, null).create());
    }
    @Override
    public void onClick(DialogInterface dialog, int which) {

        EditText loginBox=(EditText)form.findViewById(R.id.login);
        EditText passwordBox=(EditText)form.findViewById(R.id.password);
        String login = loginBox.getText().toString();
        String password = passwordBox.getText().toString();

        /**TextView loginText = (TextView)getActivity().findViewById(R.id.loginText);
        TextView passwordText = (TextView)getActivity().findViewById(R.id.passwordText);
        loginText.setText(login);
        passwordText.setText(password);*/
    }
    @Override
    public void onDismiss(DialogInterface unused) {
        super.onDismiss(unused);
    }
    @Override
    public void onCancel(DialogInterface unused) {
        super.onCancel(unused);
    }
}
