package com.electric.handbook.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.electric.handbook.R;
import com.electric.handbook.listeners.DialogAddChildListener;

/**
 * Created by Константин on 28.12.2015.
 */
public class DialogAddChid implements View.OnClickListener {

private Dialog dialog;
private EditText editText, editText1;
private DialogAddChildListener numberEnteredListener;

public DialogAddChid(DialogAddChildListener numberEnteredListener) {
        this.numberEnteredListener = numberEnteredListener;
        }

public void show(Context actv, String title, String prev) {
        dialog = new Dialog(actv);

        dialog.getWindow().setBackgroundDrawable(
        new ColorDrawable(actv.getResources().getColor(android.R.color.transparent)));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.diaog_add_chid);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        window.setAttributes(wlp);

        /**((TextView) dialog.findViewById(R.id.dialogEnterNumberTitle)).setText(title);*/
        dialog.findViewById(R.id.dialogEnterNegativeBtn).setOnClickListener(this);
        dialog.findViewById(R.id.dialogEnterPositiveBtn).setOnClickListener(this);
        editText = (EditText) dialog.findViewById(R.id.dialogNameCatal);
        editText1 = (EditText) dialog.findViewById(R.id.dialogkolvo);


        if (prev != null)
        editText.setText(prev);

        dialog.show();
        }

@Override
public void onClick(View view) {
        switch (view.getId()) {
        case R.id.dialogEnterPositiveBtn:
        numberEnteredListener.onTextEntered(editText.getText().toString(),editText1.getText().toString());
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
