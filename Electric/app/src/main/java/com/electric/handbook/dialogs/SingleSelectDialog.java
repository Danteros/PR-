/**
 *
 */
package com.electric.handbook.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.electric.handbook.R;


/**
 * @author Dmitry Tankovich
 *         date: 16.03.2015
 *         time: 9:37:49
 */
public class SingleSelectDialog implements OnItemClickListener {

    public static final String MASK_IN = "@d";

    private Dialog dialog;
    private ListView listView;
    private OnItemClickListener itemClickListener;
    private String mask = null;

    public SingleSelectDialog(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void show(Activity actv, String title, String[] data) {
        dialog = new Dialog(actv);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(actv.getResources().getColor(android.R.color.transparent)));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_single_select);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        window.setAttributes(wlp);

        ((TextView) dialog.findViewById(R.id.dialogSingleSelectTitle)).setText(title);
        listView = (ListView) dialog.findViewById(R.id.dialogSingleSelectList);
        listView.setAdapter(new AdapterSigleSelect(actv, data));
        listView.setOnItemClickListener(this);

        dialog.show();
    }

    public void setOutMask(String mask) {
        this.mask = mask;
    }

    class AdapterSigleSelect extends BaseAdapter {

        LayoutInflater inflater = null;
        String[] data;

        public AdapterSigleSelect(Context context, String[] data) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.length;
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int arg0, View convertView, ViewGroup viewGroup) {
            View v = convertView;
            if (convertView == null) {
                v = inflater.inflate(R.layout.cell_dialog_single_select, null);
            }
            TextView tvName = (TextView) v.findViewById(R.id.tvName);
            if (mask != null)
                tvName.setText(mask.replace(MASK_IN, data[arg0]));
            else
                tvName.setText(data[arg0]);
            return v;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        try {
            dialog.dismiss();
            itemClickListener.onItemClick(arg0, arg1, arg2, arg3);
        } catch (Exception ignore) {
        }
    }
}
