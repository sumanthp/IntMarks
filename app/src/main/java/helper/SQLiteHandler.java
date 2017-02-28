package helper;

/**
 * Created by HP on 24/02/2017.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileReader;
import java.util.HashMap;
public class SQLiteHandler extends SQLiteOpenHelper{
    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "android_api.db";

    // Login table name
    private static final String TABLE_USER = "user";
    private static final String TABLE_MARKS = "MARKS";

    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_USN = "USN";
    private static final String KEY_COURSE_CODE="COURSE_CODE";
    private static final String KEY_F_INT="F_INT";
    private static final String KEY_S_INT="S_INT";
    private static final String KEY_T_INT="T_INT";
    private static final String KEY_QUIZ="QUIZ";
    private static final String KEY_LAB="LAB";
    private static final String KEY_S_STUDY="S_STUDY";
    private static final String KEY_F_MARKS="FINAL_MARKS";
    private static final String KEY_BOT="BOT";

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    private static final String CREATE_MARKS_TABLE = "CREATE TABLE " + TABLE_MARKS + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_USN +" TEXT NOT NULL,"
            + KEY_COURSE_CODE+" TEXT NOT NULL,"
            + KEY_F_INT +" INTEGER,"
            + KEY_S_INT+" INTEGER,"
            + KEY_T_INT+" INTEGER,"
            + KEY_QUIZ+" INTEGER,"
            + KEY_LAB+" INTEGER,"
            + KEY_S_STUDY+" INTEGER,"
            + KEY_F_MARKS+" INTEGER,"
            + KEY_BOT+" INTEGER)";

    private static final String CREATE_LOGIN_TABLE="CREATE TABLE " + TABLE_USER + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_NAME + " TEXT NOT NULL,"
            + KEY_USN +" TEXT NOT NULL)";

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_LOGIN_TABLE);
        db.execSQL(CREATE_MARKS_TABLE);

        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MARKS);
        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addUser(String name, String username) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name); // Name
        values.put(KEY_USN, username);// USN



        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        //db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }
    public void addmarks(String USN,String subject_code,String firstInternals,
                         String secondInternals,String thirdInternals,String quiz,
                         String lab,String self_study,String finalMarks,String bestofTwo)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USN,USN);
        values.put(KEY_COURSE_CODE,subject_code);
        values.put(KEY_F_INT, firstInternals);
        values.put(KEY_S_INT,secondInternals);
        values.put(KEY_T_INT,thirdInternals);
        values.put(KEY_QUIZ,quiz);
        values.put(KEY_LAB,lab);
        values.put(KEY_S_STUDY,self_study);
        values.put(KEY_F_MARKS,finalMarks);
        values.put(KEY_BOT,bestofTwo);

        // Inserting Row
        long id = db.insert(TABLE_MARKS, null, values);
        //db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);

    }
    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("name", cursor.getString(1));
            user.put("USN", cursor.getString(2));
        }
        //cursor.close();
       // db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }

    public HashMap<String, String> getMarks() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_MARKS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("USN", cursor.getString(1));
            user.put("COURSE_CODE", cursor.getString(2));
            user.put("F_INT", cursor.getString(3));
            user.put("S_INT", cursor.getString(4));
            user.put("T_INT", cursor.getString(5));
            user.put("QUIZ", cursor.getString(6));
            user.put("LAB", cursor.getString(7));
            user.put("S_STUDY", cursor.getString(8));
            user.put("FINAL_MARKS", cursor.getString(9));
            user.put("BOT", cursor.getString(10));
        }
        //cursor.close();
        // db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }


    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);

       // db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }
    public void deleteMarks(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MARKS,null,null);

        Log.d(TAG,"Deleted all user marks");
    }


}
