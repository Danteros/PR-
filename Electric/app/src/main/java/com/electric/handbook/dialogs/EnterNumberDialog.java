/**
 *
 */
package com.electric.handbook.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.electric.handbook.R;

import com.electric.handbook.listeners.OnNumberEnteredListener;


/**
 * @author Dmitry Tankovich
 *         date: 16.03.2015
 *         time: 9:37:49
 */
public class EnterNumberDialog implements View.OnClickListener {

    private Dialog dialog;
    private EditText editText;
    private OnNumberEnteredListener numberEnteredListener;

    public EnterNumberDialog(OnNumberEnteredListener numberEnteredListener) {
        this.numberEnteredListener = numberEnteredListener;
    }

    public void show(Activity actv, String title, String prev) {
        dialog = new Dialog(actv);

        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(actv.getResources().getColor(android.R.color.transparent)));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_enter_number);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        window.setAttributes(wlp);

        ((TextView) dialog.findViewById(R.id.dialogEnterNumberTitle)).setText(title);
        dialog.findViewById(R.id.dialogEnterNegativeBtn).setOnClickListener(this);
        dialog.findViewById(R.id.dialogEnterPositiveBtn).setOnClickListener(this);
        editText = (EditText) dialog.findViewById(R.id.dialogEnterNumberEdit);

        if (prev != null)
            editText.setText(prev);

        dialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialogEnterPositiveBtn:
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
