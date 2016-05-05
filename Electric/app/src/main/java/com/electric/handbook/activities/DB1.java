package com.electric.handbook.activities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.electric.handbook.tasks.Utils;


 public class
DB1 extends SQLiteOpenHelper {

    public static final String DEFAULT = "eblock.sqlite";
    private static final int DATABASE_VERSION = 1;



    private SQLiteDatabase dataBase;
    private Context context;
     public class Tables {
        public static final String ORDERS = "orders";
        public static final String GROUPS = "groups";


        public class Order {
            public static final String ID = "_id";
            public static final String NAME = "name";
            public static final String GROUP_ID = "group_id";
            public static final String COUNT = "count";
        }

        public class Group {
            public static final String ID = "_id";
            public static final String NAME = "name";
        }



    }

    // -- constants --
    public class Queries {
        public static final String CREATE_ORDERS_TABLE = "create table if not exists \"" +
                Tables.ORDERS + "\" " + "(\"" +
                Tables.Order.ID +
                "\" integer primary key not null, " + "\"" +
                Tables.Order.GROUP_ID + "\" text, " + "\"" +
                Tables.Order.NAME + "\" text, " + "\"" +
                Tables.Order.COUNT + "\" text);";
        public static final String CREATE_TAGS_TABLE = "create table if not exists \"" +
                Tables.GROUPS + "\" " + "(\"" +
                Tables.Group.ID +
                "\" integer primary key not null, " + "\"" +
                Tables.Group.NAME + "\" text);";

    }


    public DB1 (Context context) {
        super(context, DEFAULT, null, DATABASE_VERSION);
        this.context = context;
    }

    /**
     * open database on default path(DefaultFileNames.DATABASE)
     */
    public void openDataBase() throws SQLException {
        String path = context.getExternalFilesDir("dbbase") + "/" + DEFAULT;
        openDataBase(path);
    }

    /**
     * open database on specific path
     */
    public void openDataBase(String path) throws SQLException {
        Utils.createFolderPath(Utils.getParentFolderPath(path)); // create
        // folder
        // path if
        // necessary
        close(); // close database if it was previously opened
        dataBase = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.CREATE_IF_NECESSARY |
                SQLiteDatabase.NO_LOCALIZED_COLLATORS);

        createTables();
    }

    public SQLiteDatabase getDataBase() {
        return dataBase;
    }

    private void createTables() {
        if (dataBase == null)
            return;
        dataBase.execSQL(Queries.CREATE_ORDERS_TABLE);
        dataBase.execSQL(Queries.CREATE_TAGS_TABLE);
        if (!getGroups().moveToFirst()){
            addGroupOrder("Кабели","10","Кабель ВВГ");
            addGroupOrder("Рассходники","","");
            addGroupOrder("Инструменты","5","Кусачки");
            addGroupOrder("Прочее","","");

        }

    }

    @Override
    public synchronized void close() {
        if (dataBase != null)
            dataBase.close();

        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor getGroups() {
        if (dataBase == null)
            return null;

        // @f:off
        String table = Tables.GROUPS;
        String columns[] = null;
        String selection = null;
        String selectionArgs[] = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;
        // @f:on

        return dataBase.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
    }


    public Cursor getOrders(String groupId) {
        if (dataBase == null)
            return null;

        // @f:off
        String table = Tables.ORDERS;
        String columns[] = null;
        String selection = Tables.Order.GROUP_ID + " = ?";
        String selectionArgs[] = new String[]{groupId};
        String groupBy = null;
        String having = null;
        String orderBy = null;
        // @f:on

        return dataBase.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
    }






    public Cursor getTags() {
        if (dataBase == null)
            return null;

        // @f:off
        String table = Tables.GROUPS;
        String columns[] = null;
        String selection = null;
        String selectionArgs[] = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;
        // @f:on
        return dataBase.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, "10");
    }

    public Cursor getOrder(String orderId) {
        if (dataBase == null)
            return null;

        // @f:off
        String table = Tables.ORDERS;
        String columns[] = null;
        String selection = Tables.Order.ID + " = ?";
        String selectionArgs[] = new String[]{orderId};
        String groupBy = null;
        String having = null;
        String orderBy = null;
        // @f:on

        return dataBase.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
    }

    public int removeGroup(String groupId) {
        if (dataBase == null)
            return -1;

        // @f:off
        String table = Tables.GROUPS;
        String columns[] = null;
        String selection = Tables.Group.ID + " = ?";
        String selectionArgs[] = new String[]{groupId};
        String groupBy = null;
        String having = null;
        String orderBy = null;
        // @f:on

        return dataBase.delete(table, selection, selectionArgs);
    }
    public int removeOrder(String groupId) {
        if (dataBase == null)
            return -1;

        // @f:off
        String table = Tables.ORDERS;
        String columns[] = null;
        String selection = Tables.Order.ID + " = ?";
        String selectionArgs[] = new String[]{groupId};
        String groupBy = null;
        String having = null;
        String orderBy = null;
        // @f:on

        return dataBase.delete(table, selection, selectionArgs);
    }
    public int removeOrderForGroup(String groupId) {
        if (dataBase == null)
            return -1;

        // @f:off
        String table = Tables.ORDERS;
        String columns[] = null;
        String selection = Tables.Order.GROUP_ID + " = ?";
        String selectionArgs[] = new String[]{groupId};
        String groupBy = null;
        String having = null;
        String orderBy = null;
        // @f:on

        return dataBase.delete(table, selection, selectionArgs);
    }

    public long addOrder(ContentValues cv) {
        if (dataBase == null)
            return -1;
        // @f:off
        String table = Tables.ORDERS;
        String columns[] = null;
        String selection = null;
        String selectionArgs[] = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;
        // @f:on

        return dataBase.insertWithOnConflict(table, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
    }






    public long addGroupOrder(String group,String count,String name){
        long param = addGroup(group);
        ContentValues cv = new ContentValues();
        String table = Tables.ORDERS;
        cv.put(Tables.Order.GROUP_ID,param);
        cv.put(Tables.Order.COUNT,count);
        cv.put(Tables.Order.NAME, name);
        return dataBase.insertWithOnConflict(table, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

    }
    public long addOrder(String groupId,String name,String count){

        ContentValues cv = new ContentValues();
        cv.put(Tables.Order.GROUP_ID,groupId);
        cv.put(Tables.Order.COUNT,count);
        cv.put(Tables.Order.NAME,name);
        String table = Tables.ORDERS;
        return dataBase.insertWithOnConflict(table, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
    }
    public void updateOrder(String orderId,String count,String name,String groupID) {
        if (dataBase == null)
            return;
        // @f:off
        ContentValues cv = new ContentValues();
        cv.put(Tables.Order.ID,orderId);
        cv.put(Tables.Order.GROUP_ID,groupID);
        cv.put(Tables.Order.COUNT,count);
        cv.put(Tables.Order.NAME, name);
        String table = Tables.ORDERS;
        String columns[] = null;
        String selection = Tables.Order.ID + " = ?";
        String selectionArgs[] = new String[]{orderId};
        String groupBy = null;
        String having = null;
        String orderBy = null;
        // @f:on
        dataBase.updateWithOnConflict(table, cv, selection, selectionArgs, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public long addGroup(String tag) {
        if (dataBase == null)
            return -1;
        // @f:off
        Cursor cursor = getTag(tag);
        if (cursor.moveToFirst()) {
            return cursor.getLong(cursor.getColumnIndex(Tables.Group.ID));
        }
        ContentValues cv = new ContentValues();
        cv.put(Tables.Group.NAME, tag);
        String table = Tables.GROUPS;
        String columns[] = null;
        String selection = null;
        String selectionArgs[] = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;
        // @f:on

        return dataBase.insertWithOnConflict(table, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public Cursor getTag(String tag) {
        if (dataBase == null)
            return null;

        // @f:off
        String table = Tables.GROUPS;
        String columns[] = null;
        String selection = Tables.Group.NAME + " = ?";
        String selectionArgs[] = new String[]{tag};
        String groupBy = null;
        String having = null;
        String orderBy = null;
        // @f:on

        return dataBase.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);

    }




}

