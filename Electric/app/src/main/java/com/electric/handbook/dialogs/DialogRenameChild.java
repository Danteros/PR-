package com.electric.handbook.dialogs;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import com.electric.handbook.R;
import com.electric.handbook.activities.ActivityMain;
import com.electric.handbook.listeners.DialogRenameChildListener;

/**
 * Created by Администратор on 12.01.2016.
 */
public class DialogRenameChild implements View.OnClickListener {

private Dialog dialog;
private EditText editText, editText1;
private DialogRenameChildListener numberEnteredListener;

public DialogRenameChild(DialogRenameChildListener numberEnteredListener) {
        this.numberEnteredListener = numberEnteredListener;
        }

public void show(ActivityMain actv, String title, String prev) {
        dialog = new Dialog(actv);

        dialog.getWindow().setBackgroundDrawable(
        new ColorDrawable(actv.getResources().getColor(android.R.color.transparent)));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_rename_child);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        window.setAttributes(wlp);

        /**((TextView) dialog.findViewById(R.id.dialogEnterNumberTitle)).setText(title);*/
        dialog.findViewById(R.id.dialogEnterNegativeBtn).setOnClickListener(this);
        dialog.findViewById(R.id.dialogEnterPositiveBtn).setOnClickListener(this);
        editText = (EditText) dialog.findViewById(R.id.dialogReNameCatal);
        editText1 = (EditText) dialog.findViewById(R.id.dialogRekolvo);


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