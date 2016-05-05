package com.electric.handbook.fragments;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorTreeAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.electric.handbook.R;
import com.electric.handbook.activities.ActivityMain;
import com.electric.handbook.activities.DB1;
import com.electric.handbook.dialogs.DialogAddChid;
import com.electric.handbook.dialogs.DialogRenameChild;
import com.electric.handbook.listeners.DialogAddChildListener;
import com.electric.handbook.listeners.DialogRenameChildListener;

import static com.electric.handbook.R.id.buttonChild;




public class FragmentStock extends Fragment implements View.OnClickListener {

    private static final String TAG = "myLogs";
    ExpandableListView listView;
    String name[] = { "Кабели","Расходники","Инструменты","Прочее" };
    String cables []={"JV 87","CK 45","DD 51"};
    int cable [] = { 1400, 311, 195};
    String tools [] = { "Keys", "Cables", "Troub"};
    int tool [] = { 1400, 311, 195};
    final String LOG_TAG = "myLogs";

    DB1 db;
    ExpandableListView elvMain;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_stoki, container, false);
        db = new DB1(getContext());
        db.openDataBase();


        // готовим данные по группам для адаптера
        Cursor cursor = db.getGroups();

        // создаем адаптер и настраиваем список
        CursorTreeAdapter sctAdapter = new TreeAdapter(getContext(), cursor);
        elvMain = (ExpandableListView) view.findViewById(R.id.exListView);
        elvMain.setGroupIndicator(null);
        elvMain.setAdapter(sctAdapter);


        return view;




    }
    public void updateData(){
        Cursor cursor = db.getGroups();
        CursorTreeAdapter sctAdapter = new TreeAdapter(getContext(), cursor);
        elvMain.setAdapter(sctAdapter);
    }
    public void onDestroy() {
        super.onDestroy();
        db.close();
    }
    class TreeAdapter extends CursorTreeAdapter {
        private  LayoutInflater inflater;


        public TreeAdapter(Context context, Cursor cursor
                         ) {
            super(cursor, context);
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        protected Cursor getChildrenCursor(Cursor groupCursor) {
            // получаем курсор по элементам для конкретной группы
            String groupID = groupCursor.getString(groupCursor.getColumnIndex(DB1.Tables.Group.ID));
            return db.getOrders(groupID);


        }
        @Override
        public void bindChildView(View view, Context context, Cursor cursor, boolean isLastChild ) {

            TextView textChild = (TextView) view.findViewById(R.id.textChild);
            TextView textChildnum = (TextView) view.findViewById(R.id.textChildnum);
            final String name = (cursor.getString(cursor.getColumnIndex(DB1.Tables.Order.NAME)));
            textChild.setText(name);
            String name1 = (cursor.getString(cursor.getColumnIndex(DB1.Tables.Order.COUNT)));
            textChildnum.setText(name1);
            TextView button = (TextView)view.findViewById(buttonChild);
            TextView button1 = (TextView)view.findViewById(R.id.deliteChild);
            ImageView imageView2 = (ImageView)view.findViewById(R.id.imageView2);
            final String groupID = (cursor.getString(cursor.getColumnIndex(DB1.Tables.Order.GROUP_ID)));
            final String orderID = (cursor.getString(cursor.getColumnIndex(DB1.Tables.Order.ID)));



            if(isLastChild) button1.setVisibility(View.VISIBLE);
            else button1.setVisibility(View.GONE);

            if (cursor.getPosition()==0) button.setVisibility(View.VISIBLE);
            else button.setVisibility(View.GONE);

            if ((name !=null)&& name.length()>0){
                textChild.setVisibility(View.VISIBLE);
                textChildnum.setVisibility(View.VISIBLE);
                imageView2.setVisibility(View.VISIBLE);


            }
            else{
                textChild.setVisibility(View.GONE);
                textChildnum.setVisibility(View.GONE);
                imageView2.setVisibility(View.GONE);

            }

            textChild.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {


                    switch (view.getId()) {
                        case R.id.textChild:
                            showDialogRenameChild(0, groupID, orderID);
                            updateData();
                            break;
                    }

                }

                                         });
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            switch (view.getId()) {
                                case R.id.buttonChild:
                                    showDialogAddChild(0,groupID);
                                    updateData();
                                    break;
                            }
                        }
                    });
            button1.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    switch (view.getId()) {
                        case R.id.deliteChild:
                            db.removeOrderForGroup(groupID);
                            db.removeGroup(groupID);
                            updateData();
                            break;
                    }
                }
            });
        }

        public void showDialogAddChild(final int param,final String groupID){

            DialogAddChid enterNumberDialog = new DialogAddChid(new DialogAddChildListener() {
                @Override
                public void onTextEntered(String childname,String num) {
                    switch (param){
                        case 0:
                            db.addOrder(groupID,childname,num);
                            break;
                    }
                }
            });
            enterNumberDialog.show(getContext(), "test", "");
        }
        public void showDialogRenameChild(final int param,final String groupID, final String orderID){

            DialogRenameChild enterNumberDialog = new DialogRenameChild(new DialogRenameChildListener() {
                @Override
                public void onTextEntered(String child,String num) {
                    switch (param){
                        case 0:
                            db.updateOrder(orderID, child, num,groupID);
                            break;
                    }
                }
            });
            enterNumberDialog.show((ActivityMain) getContext(), "test", "");
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {



            return true;
        }






        @Override
        protected View newGroupView(Context context, Cursor cursor, boolean isExpanded, ViewGroup parent) {
            View view = inflater.inflate(R.layout.group_view, parent, false);
            return view;
        }

        @Override
        protected void bindGroupView(View view, Context context, Cursor cursor, boolean isExpanded) {
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            TextView textGroup = (TextView) view.findViewById(R.id.textGroup);

            if (isExpanded){
                //Изменяем что-нибудь, если текущая Group раскрыта
                imageView.setImageResource(R.drawable.ic_arrow_down);
            }
            else{
                //Изменяем что-нибудь, если текущая Group скрыта
                imageView.setImageResource(R.drawable.ic_arrow_right);
            }
            textGroup.setText(cursor.getString(cursor.getColumnIndex(DB1.Tables.Group.NAME)));
        }

        @Override
        protected View newChildView(Context context, Cursor cursor, boolean isLastChild, ViewGroup parent) {
            View view = inflater.inflate(R.layout.child_view, parent, false);
            return view;
        }
    }



    @Override
    public void onClick(View v) {




    }




}
