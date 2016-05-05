package com.electric.handbook.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.electric.handbook.R;
import com.electric.handbook.dialogs.DialogAddChid;
import com.electric.handbook.listeners.DialogAddChildListener;

import java.util.ArrayList;

import static com.electric.handbook.R.id.buttonChild;


public class ExpListAdapter extends BaseExpandableListAdapter {

    private ArrayList<ArrayList<String>> mGroups;
    private Context mContext;
    private ArrayList<String> groupNames;



    public ExpListAdapter (Context context,ArrayList<ArrayList<String>> groups,ArrayList<String> groupNames){
        this.mContext = context;
        this.mGroups = groups;
        this.groupNames = groupNames;
    }

    @Override
    public int getGroupCount() {
        return mGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mGroups.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mGroups.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                             ViewGroup parent) {


        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.group_view, null);
        }
        ImageView imageView = (ImageView)convertView.findViewById(R.id.imageView);


        if (isExpanded){
            //Изменяем что-нибудь, если текущая Group раскрыта
            imageView.setImageResource(R.drawable.ic_arrow_down);
        }
        else{
            //Изменяем что-нибудь, если текущая Group скрыта
            imageView.setImageResource(R.drawable.ic_arrow_right);
        }


        TextView textGroup = (TextView) convertView.findViewById(R.id.textGroup);
        textGroup.setText(groupNames.get(groupPosition));

        return convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_view, null);
        }
        ImageView imageView = (ImageView)convertView.findViewById(R.id.imageView2);

        TextView textChild = (TextView) convertView.findViewById(R.id.textChild);
        String name = mGroups.get(groupPosition).get(childPosition);
        textChild.setText(name);
        if ((name !=null)&& name.length()>0){
            textChild.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.VISIBLE);

        }
        else{
            textChild.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
        }
        TextView textChildNum = (TextView) convertView.findViewById(R.id.textChildnum);
        textChildNum.setText(mGroups.get(groupPosition).get(childPosition));


        TextView button = (TextView)convertView.findViewById(buttonChild);
        TextView button1 = (TextView)convertView.findViewById(R.id.deliteChild);

        if(childPosition==0) button.setVisibility(View.VISIBLE);
        else button.setVisibility(View.GONE);
        if(isLastChild) button1.setVisibility(View.VISIBLE);
        else button1.setVisibility(View.GONE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "button is pressed", Toast.LENGTH_LONG).show();

                switch (view.getId()) {
                    case R.id.buttonChild:
                        showDialogAddChild(0);
                        break;
                }

            }


        });


        return convertView;
    }
    public void showDialogAddChild(final int param){

        DialogAddChid enterNumberDialog = new DialogAddChid(new DialogAddChildListener() {
            @Override
            public void onTextEntered(String text,String text1) {
                switch (param){
                    case 0:
                        break;
                }

            }
        });
        enterNumberDialog.show(mContext, "test", "");
    }





    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {


        return true;
    }
}
