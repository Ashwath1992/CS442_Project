package com.cs442.project.splitpay;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GroupDetailsDbHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SplitPay.db";
    private static final String TABLE_GROUP_DETAILS = "GroupDetails";
    private static final String COLUMN_GROUP_NAME = "groupName";
    private static final String COLUMN_MEMBER_NAME = "memberName";
    private static final String COLUMN_AMOUNT_OWED = "amountOwed";
    private static GroupDetailsDbHandler groupDetailsDbHandler = null;

    public static GroupDetailsDbHandler getDbHandlerInstance(Context context, SQLiteDatabase.CursorFactory factory){
        if(groupDetailsDbHandler == null){
            groupDetailsDbHandler = new GroupDetailsDbHandler(context, factory);
        }
        return groupDetailsDbHandler;
    }

    private GroupDetailsDbHandler(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context,DATABASE_NAME,factory,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_GROUP_DETAILS + "(" +
                COLUMN_GROUP_NAME + " TEXT not null," +
                COLUMN_MEMBER_NAME + " TEXT not null," +
                COLUMN_AMOUNT_OWED + " REAL not null," + ");";
        db.execSQL(query);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
