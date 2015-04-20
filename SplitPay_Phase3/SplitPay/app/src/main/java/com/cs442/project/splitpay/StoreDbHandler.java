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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    private static final String COLUMN_ID = "id";

    private static final String TABLE_GROUPS = "Groups";
    private static final String COLUMN_GROUP_NAME = "groupName";

    private static final String COLUMN_MEMBER_PHONE = "memberPhone";
    private static final String COLUMN_GROUPS_NAME = "groupsName";
    private static final String COLUMN_MEMBER_NAME = "memberName";
    private static final String COLUMN_AMOUNT_OWED = "amountOwed";
    private static final String COLUMN_AMOUNT_PAID = "amountPaid";
    private static final String COLUMN_PAYMENT_STATUS = "memberPaymentStatus";
    private static final String COLUMN_ITEM_NAME = "groupItemName";

    private static final String TABLE_GROUPMEMBERS = "GroupMembers";
    private static final String TABLE_GROUPITEM_PAYMENT = "GroupItemPayment";

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

        String query2 = "CREATE TABLE " + TABLE_GROUPMEMBERS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_GROUP_NAME + " TEXT," +
                COLUMN_MEMBER_PHONE + " TEXT," +
                COLUMN_MEMBER_NAME + " TEXT, CONSTRAINT unqGrpMem_name UNIQUE (" + COLUMN_GROUP_NAME + "," +  COLUMN_MEMBER_NAME + ") );";

        String query3 = "CREATE TABLE " + TABLE_GROUPITEM_PAYMENT + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_GROUP_NAME + " TEXT," +
                COLUMN_ITEM_NAME + " TEXT," +
                COLUMN_AMOUNT_OWED + " REAL," +
                COLUMN_AMOUNT_PAID + " REAL," +
                COLUMN_PAYMENT_STATUS + " TEXT," +
                COLUMN_MEMBER_NAME + " TEXT, CONSTRAINT unqGrpMem_name UNIQUE (" + COLUMN_GROUP_NAME + "," + COLUMN_ITEM_NAME + ","+ COLUMN_MEMBER_NAME + ") );";

        db.execSQL(query);
        db.execSQL(newQuery);
        db.execSQL(query2);
        db.execSQL(query3);
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

    /*public boolean addUserDetails(Registration userRegistration) {
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
    }*/

    public boolean addGroupName(Groups_Reg groupName) {
        boolean result = false;
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            ContentValues values = new ContentValues();
            //values.put(COLUMN_GROUP_NAME, groupName.getGroupName());
            //System.out.println("group name is->>> " + groupName.getGroupName());
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

    public boolean checkColumnInDb(String groupName,String memberName, String itemName){
        boolean result = false;
        ArrayList<String> groupMemberItemList = new ArrayList<String>();
        SQLiteDatabase db = getReadableDatabase();
        if (db != null) {
            String query = "SELECT * FROM " + TABLE_GROUPITEM_PAYMENT + " WHERE " + COLUMN_GROUP_NAME + " = '" + groupName + "'" + " AND " + COLUMN_ITEM_NAME + " = '" + itemName + "'"
                    + " AND " + COLUMN_MEMBER_NAME + " = '" + memberName + "'";
            //cursor to point the current position of result
            Cursor c = db.rawQuery(query, null);
            c.moveToFirst();
            while (!c.isAfterLast()) {
                String grpName = c.getString(c.getColumnIndex(COLUMN_GROUP_NAME));
                String memName = c.getString(c.getColumnIndex(COLUMN_MEMBER_NAME));
                String itmName = c.getString(c.getColumnIndex(COLUMN_ITEM_NAME));
                groupMemberItemList.add(grpName);
                groupMemberItemList.add(itmName);
                groupMemberItemList.add(memName);
                c.moveToNext();
            }
            if(groupMemberItemList.size() > 0) {
                result = true;
            }
            else{
                result = false;
            }
            db.close();
        }

        return result;

    }

    public boolean addGroupMember(String groupName, String memberName, String memberPhone) {
        boolean result = false;
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_GROUP_NAME, groupName);
            values.put(COLUMN_MEMBER_PHONE, memberPhone);
            values.put(COLUMN_MEMBER_NAME, memberName);
            System.out.println("groupName for member " + groupName + " memberName:" + memberName);
            if (db.insert(TABLE_GROUPMEMBERS, null, values) != -1) {
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

    public List<String> getMembersFromDb(String grpName){
        List<String> members = new ArrayList<String>();
        SQLiteDatabase db = getReadableDatabase();

        if (db != null) {
            try {
                String query = "SELECT * FROM " + TABLE_GROUPMEMBERS + " WHERE " + COLUMN_GROUP_NAME + " = '" + grpName + "'";
                //cursor to point the current position of result
                Cursor c = db.rawQuery(query, null);
                c.moveToFirst();
                while (!c.isAfterLast()) {
                    String memberName = c.getString(c.getColumnIndex(COLUMN_MEMBER_NAME));
                    members.add(memberName);
                    c.moveToNext();
                }
                db.close();
            }catch(Exception e){
                System.out.println("Error in db: " + e.getMessage());
            }
        }

        return members;
    }

    public String getMemberPhoneFromDb(String grpName, String memberName){
        String memberPhone = "";
        SQLiteDatabase db = getReadableDatabase();

        if (db != null && grpName != null && memberName != null) {
            try {
                String query = "SELECT * FROM " + TABLE_GROUPMEMBERS + " WHERE " + COLUMN_GROUP_NAME + " = '" +
                        grpName + "' and " + COLUMN_MEMBER_NAME + " = '" + memberName + "'";
                //cursor to point the current position of result
                Cursor c = db.rawQuery(query, null);
                c.moveToFirst();
                while (!c.isAfterLast()) {
                    System.out.println(" member phone:" + c.getString(c.getColumnIndex(COLUMN_MEMBER_PHONE)));
                    if(memberName.equalsIgnoreCase(c.getString(c.getColumnIndex(COLUMN_MEMBER_NAME)))) {
                        memberPhone = c.getString(c.getColumnIndex(COLUMN_MEMBER_PHONE));
                        break;
                    }
                    c.moveToNext();
                }
                db.close();
            }catch(Exception e){
                System.out.println("Error in db: " + e.getMessage());
            }
        }
        System.out.println(" member phone1:" + memberPhone);
        return memberPhone;
    }

    public Map<String, Item> getMemberPaymentDetailsFromDb(String grpName){
        Map<String, Item> itemMap = new HashMap<String, Item>();
        SQLiteDatabase db = getReadableDatabase();

        if (db != null) {
            try {
                String query = "SELECT * FROM " + TABLE_GROUPITEM_PAYMENT + " WHERE " + COLUMN_GROUP_NAME + " = '" + grpName + "'";
                //cursor to point the current position of result
                Cursor c = db.rawQuery(query, null);
                c.moveToFirst();
                while (!c.isAfterLast()) {
                    String memberName = c.getString(c.getColumnIndex(COLUMN_MEMBER_NAME));
                    Double amountOwed = c.getDouble(c.getColumnIndex(COLUMN_AMOUNT_OWED));
                    Double amountPaid = c.getDouble(c.getColumnIndex(COLUMN_AMOUNT_PAID));
                    //String memberPaymentStatus = c.getString(c.getColumnIndex(COLUMN_PAYMENT_STATUS));
                    //String itemName = c.getString(c.getColumnIndex(COLUMN_ITEM_NAME));

                    Item item = new Item(false,memberName,amountOwed,amountPaid,amountOwed,amountPaid);
                    //item.setItemName(itemName);
                    //item.setPaymentStatus(memberPaymentStatus);

                    if(itemMap.containsKey(memberName)){
                        Item itemFromList = itemMap.get(memberName);
                        itemFromList.setTotalAmountOwed(itemFromList.getTotalAmountOwed() + amountOwed);
                        itemFromList.setTotalAmountSpent(itemFromList.getTotalAmountSpent() + amountPaid);
                        itemMap.put(memberName,itemFromList);
                    }else{
                        itemMap.put(memberName,item);
                    }

                    c.moveToNext();
                }
                db.close();
            }catch(Exception e){
                System.out.println("Error in db: " + e.getMessage());
            }
        }

        return itemMap;
    }

    public boolean setMemberPaymentDetailsToDb(String grpName, Item member){
        boolean result = false;
        SQLiteDatabase db = getWritableDatabase();

        if (db != null && member != null) {
            try {
                ContentValues values = new ContentValues();
                values.put(COLUMN_GROUP_NAME, grpName);
                values.put(COLUMN_MEMBER_NAME, member.getMemberName());
                values.put(COLUMN_ITEM_NAME, member.getItemName());
                values.put(COLUMN_AMOUNT_OWED, member.getPerItemAmountOwed());
                values.put(COLUMN_AMOUNT_PAID, member.getPerItemAmountSpent());
                values.put(COLUMN_PAYMENT_STATUS, member.getPaymentStatus());
                System.out.println("groupName for member payment table " + grpName + " memberName:" + member.getMemberName());
                if (db.insert(TABLE_GROUPITEM_PAYMENT, null, values) != -1) {
                    result = true;
                } else {
                    result = false;
                }
                db.close();
            }catch(Exception e){
                System.out.println("Error in db: " + e.getMessage());
            }
        }

        return result;
    }

    public boolean updateMemberPaymentDetailsToDb(String grpName, Item member){
        boolean result = false;
        SQLiteDatabase db = getWritableDatabase();

        if (db != null && member != null) {
            try {
                ContentValues values = new ContentValues();
                values.put(COLUMN_AMOUNT_OWED, member.getPerItemAmountOwed());
                values.put(COLUMN_AMOUNT_PAID, member.getPerItemAmountSpent());
                System.out.println("groupName for update  member payment table " + grpName + " memberName:" + member.getMemberName());
                String[] member1 = new String[]{member.getMemberName()};
                String whereClause = COLUMN_MEMBER_NAME + " = ?" + " and " + COLUMN_GROUP_NAME +
                        " = '" + grpName + "' and " + COLUMN_ITEM_NAME + " = '" + member.getItemName() + "'";
                if (db.update(TABLE_GROUPITEM_PAYMENT, values, whereClause, member1) != -1) {
                    result = true;
                } else {
                    result = false;
                }
                db.close();
            }catch(Exception e){
                System.out.println("Error in db: " + e.getMessage());
            }
        }

        return result;
    }

    public boolean makeMembersPayment(String grpName, List<Item> members){
        boolean result = false;
        SQLiteDatabase db = getWritableDatabase();

        if (db != null && members != null && !members.isEmpty()) {
            try {
                for(Item member : members) {
                    ContentValues newValues = new ContentValues();
                    newValues.put(COLUMN_AMOUNT_OWED, 0.0);

                    String[] args = new String[]{grpName, member.getMemberName()};
                    String filter = COLUMN_GROUP_NAME + " = ? AND " + COLUMN_MEMBER_NAME + " = ?";

                    if (db.update(TABLE_GROUPITEM_PAYMENT, newValues, filter, args) != -1) {
                        result = true;
                    } else {
                        result = false;
                    }
                }
                db.close();
            }catch(Exception e){
                System.out.println("Error in db: " + e.getMessage());
            }
        }

        return result;
    }

    public boolean saveOrUpdate(String groupName, List<Item> members){
        boolean result = false;

        for(Item member : members) {
            boolean itemExits
                    = checkColumnInDb(groupName, member.getMemberName(), member.getItemName());
            if (itemExits) {
                result = updateMemberPaymentDetailsToDb(groupName, member);
            } else {
                result = setMemberPaymentDetailsToDb(groupName, member);
            }
        }
        return result;
    }
}

