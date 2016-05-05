package com.electric.handbook.mview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.electric.handbook.R;


/**
 * Created by Dmitry Tankovich
 * OOO "ProgrammeroF"
 * dmitry.tankovich@gmail.com
 * on 22:02 15.07.2015.
 */
public class MSwitch extends LinearLayout implements View.OnClickListener{

    private TextView one, two;
    private boolean isEnableOne = false;
    private OnClickListener onClickListener;

    public MSwitch(Context context) {
        super(context);
        init(null);
    }

    public MSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        LayoutInflater.from(getContext()).inflate(R.layout.mswitch, this, true);
        one = (TextView) findViewById(R.id.mSwitcherOne);
        two = (TextView) findViewById(R.id.mSwitcherTwo);
        findViewById(R.id.mSwitcher).setOnClickListener(this);

        XmlResourceParser xrp = getResources().getXml(R.drawable.mswitch_text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(), xrp);
            one.setTextColor(csl);
            two.setTextColor(csl);
        } catch (Exception ignored) {}
        two.setEnabled(true);
        one.setEnabled(false);
        isEnableOne = false;

        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs,
                    R.styleable.MSwitch);
            one.setText(a.getString(R.styleable.MSwitch_textOne));
            two.setText(a.getString(R.styleable.MSwitch_textTwo));
            a.recycle();
        }

    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public void onClick(View view) {
        isEnableOne = !isEnableOne;
        one.setEnabled(isEnableOne);
        two.setEnabled(!isEnableOne);
        onClickListener.onClick(view);
    }

    public boolean isSelectedOne() {
        return !isEnableOne;
    }
}
