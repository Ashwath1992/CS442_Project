package com.cs442.project.splitpay;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class StoreDbHandler extends SQLiteOpenHelper {

    Context c;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SplitPay.db";
    private static final String TABLE_REGISTER = "UserReg";
    private static final String COULUMN_FNAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_USERNAME = "uname";
    private static final String COLUMN_PASSWORD = "passwd";
    private static final String COLUMN_CONPASS = "conpass";
    private static final String COLUMN_CARD = "card";
    private static final String COLUMN_CARDTYPE = "ctype";
    private static final String COLUMN_CARDNAME = "cname";
    private static final String COLUMN_CARDNUM = "cnum";
    private static final String COLUMN_CARDEXP = "exp";
    private static final String COLUMN_CARDCVV = "cvv";

    private static final String TABLE_GROUPS = "Groups";
    private static final String COLUMN_GROUP_NAME = "groupName";

    private static final String TABLE_MEMBER_DETAILS = "MemberDetails";
    private static final String COLUMN_GROUPS_NAME = "groupsName";
    private static final String COLUMN_MEMBER_NAME = "memberName";
    private static final String COLUMN_AMOUNT_OWED = "amountOwed";
    private static final String COLUMN_AMOUNT_PAID_FOR = "amountPaidFor";

    private static StoreDbHandler dbHandler = null;

    public static StoreDbHandler getDbHandlerInstance(Context context, SQLiteDatabase.CursorFactory factory) {
        if (dbHandler == null) {
            dbHandler = new StoreDbHandler(context, factory);
        }
        return dbHandler;
    }

    private StoreDbHandler(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        c = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("on create 1");
        String query = "CREATE TABLE " + TABLE_REGISTER + "(" +
                COULUMN_FNAME + " TEXT not null," +
                COLUMN_EMAIL + " TEXT not null unique," +
                COLUMN_PHONE + " INTEGER not null unique," +
                COLUMN_USERNAME + " TEXT PRIMARY KEY," +
                COLUMN_PASSWORD + " TEXT not null unique," +
                COLUMN_CONPASS + " TEXT not null unique," +
                COLUMN_CARD + " TEXT not null," +
                COLUMN_CARDTYPE + " TEXT not null," +
                COLUMN_CARDNAME + " TEXT not null unique," +
                COLUMN_CARDNUM + " INTEGER not null unique," +
                COLUMN_CARDEXP + " INTEGER not null," +
                COLUMN_CARDCVV + " INTEGER not null unique" + ");";

        String newQuery = "CREATE TABLE " + TABLE_GROUPS + "(" +
                COLUMN_GROUP_NAME + " TEXT PRIMARY KEY" + ");";

        String query2 = "CREATE TABLE " + TABLE_MEMBER_DETAILS + "(" +
                COLUMN_GROUPS_NAME + " TEXT" +
                COLUMN_MEMBER_NAME + " TEXT," +
                COLUMN_AMOUNT_OWED + " REAL," +
                COLUMN_AMOUNT_PAID_FOR + " TEXT" + ");";

        db.execSQL(query);
        db.execSQL(newQuery);
        db.execSQL(query2);
        System.out.println("on create 2");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public boolean isUserAuthenticated(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<String> users = new ArrayList<String>();
        boolean result = false;

        if (db != null) {
            String query = "SELECT " + COLUMN_USERNAME + " FROM " + TABLE_REGISTER + " WHERE " + COLUMN_USERNAME +
                    " = '" + username + "' and " + COLUMN_PASSWORD + " = '" + password + "'";
            //cursor to point the current position of result
            Cursor c = db.rawQuery(query, null);
            c.moveToFirst();
            while (!c.isAfterLast()) {
                users.add(c.getString(c.getColumnIndex(COLUMN_USERNAME)));
                c.moveToNext();
            }
            db.close();
        }

        if (users.size() == 1) {
            result = true;
        }

        return result;
    }

    public boolean addUserDetails(Registration userRegistration) {
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            ContentValues values = new ContentValues();
            values.put(COULUMN_FNAME, userRegistration.getName());
            values.put(COLUMN_EMAIL, userRegistration.getEmail());
            values.put(COLUMN_PHONE, userRegistration.getPhone());
            values.put(COLUMN_USERNAME, userRegistration.getUname());
            values.put(COLUMN_PASSWORD, userRegistration.getPasswd());
            values.put(COLUMN_CONPASS, userRegistration.getConpass());
            values.put(COLUMN_CARD, userRegistration.getCard());
            values.put(COLUMN_CARDTYPE, userRegistration.getCtype());
            values.put(COLUMN_CARDNAME, userRegistration.getCname());
            values.put(COLUMN_CARDNUM, userRegistration.getCnum());
            values.put(COLUMN_CARDEXP, String.valueOf(userRegistration.getExp()));
            values.put(COLUMN_CARDCVV, userRegistration.getCvv());
            if (db.insert(TABLE_REGISTER, null, values) != -1) {
                Toast.makeText(c, "Registered Successfully", Toast.LENGTH_LONG).show();
                db.close();
            } else {
                Toast.makeText(c, "You are already registered for SplitPay", Toast.LENGTH_LONG).show();
            }
            db.close();
        }
        return true;
    }

    public boolean addGroupName(Groups_Reg groupName) {
        boolean result = false;
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_GROUP_NAME, groupName.getGroupName());
            System.out.println("group name is->>> " + groupName.getGroupName());
            if (db.insert(TABLE_GROUPS, null, values) != -1) {
                result = true;
                Toast.makeText(c, "Group name saved", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(c, "Group name already exist", Toast.LENGTH_LONG).show();
                result = false;
            }
            db.close();
        }
        return result;
    }

    public ArrayList<String> getGroupNameList() {
        ArrayList<String> groupList = new ArrayList<String>();
        SQLiteDatabase db = getReadableDatabase();

        if (db != null) {
            String query = "SELECT * FROM " + TABLE_GROUPS;
            //cursor to point the current position of result
            Cursor c = db.rawQuery(query, null);
            c.moveToFirst();
            while (!c.isAfterLast()) {
                String groupName = c.getString(c.getColumnIndex(COLUMN_GROUP_NAME));
                groupList.add(groupName);
                c.moveToNext();
            }
            db.close();
        }
        return groupList;
    }

    public boolean addGroupMemberDetails(Groups_Reg groupName, Members memberDetails) {
        boolean result = false;
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_GROUPS_NAME, groupName.getGroupName());
            values.put(COLUMN_MEMBER_NAME, memberDetails.getMemberName());
            values.put(COLUMN_AMOUNT_OWED, memberDetails.getAmountOwed());
            values.put(COLUMN_AMOUNT_PAID_FOR, memberDetails.getAmountPaidFor());
            System.out.println("groupName for member " + groupName.getGroupName());
            System.out.println("memberName " + memberDetails.getMemberName());
            if (db.insert(TABLE_GROUPS, null, values) != -1) {
                result = true;
                Toast.makeText(c, "Member details saved", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(c, "Member details are not saved", Toast.LENGTH_LONG).show();
                result = false;
            }
            db.close();
        }
        return result;
    }
}

