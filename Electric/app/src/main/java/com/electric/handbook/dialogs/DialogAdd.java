package com.electric.handbook.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.electric.handbook.R;
import com.electric.handbook.listeners.DialogAddListener;

/**
 * Created by Константин on 23.12.2015.
 */
public class DialogAdd implements View.OnClickListener {


    private Dialog dialog;
    private EditText editText;
    private DialogAddListener numberEnteredListener;

    public DialogAdd(DialogAddListener numberEnteredListener) {
        this.numberEnteredListener = numberEnteredListener;
    }

    public void show(Activity actv, String title, String prev) {
        dialog = new Dialog(actv);

        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(actv.getResources().getColor(android.R.color.transparent)));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.addform);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        window.setAttributes(wlp);

        /**((TextView) dialog.findViewById(R.id.dialogEnterNumberTitle)).setText(title);*/
        dialog.findViewById(R.id.dialogEnterNegativeBtn).setOnClickListener(this);
        dialog.findViewById(R.id.dialogEnterPositiveBtnAdd).setOnClickListener(this);
        editText = (EditText) dialog.findViewById(R.id.dialogAddCategory);


        if (prev != null)
            editText.setText(prev);

        dialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialogEnterPositiveBtnAdd:
                numberEnteredListener.onTextEntered(editText.getText().toString());

                try {
                    dialog.dismiss();
                } catch (Exception ignored) {
                }
                break;
            case R.id.dialogEnterNegativeBtn:
                try {
                    dialog.dismiss();
                } catch (Exception ignored) {
                }
                break;
        }
    }
}

