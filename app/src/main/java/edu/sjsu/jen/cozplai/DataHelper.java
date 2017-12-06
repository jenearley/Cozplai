package edu.sjsu.jen.cozplai;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jen0e on 12/5/2017.
 */

public class DataHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = DataHelper.class.getName();

    public static final int DATABASE_VERSION = 6;
    public static final String DATABASE_NAME = "Data.db";

    public DataHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String TABLE_CHARACTER = "character";
    private static final String TABLE_ELEMENT = "element";
    private static final String TABLE_USER = "user";
    private static final String TABLE_STEP = "steps";

    private static final String CREATE_TABLE_CHARACTER =
            "CREATE TABLE " + TABLE_CHARACTER +
                    " (_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " NAME TEXT, SOURCE TEXT, COMPLETION INT," +
                    " ELEMENTS_ID TEXT, PHOTO_ID REAL);";
    private static final String CREATE_TABLE_ELEMENT =
            "CREATE TABLE " + TABLE_ELEMENT +
                    " (_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " NAME TEXT, COST REAL, COMPLETION INT," +
                    " STEPS_ID TEXT, MAKEORBUY TEXT);";
    private static final String CREATE_TABLE_USER =
            "CREATE TABLE " + TABLE_USER +
                    " (_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " NAME TEXT, EMAIL TEXT, PASSWORD TEXT);";
    private static final String CREATE_TABLE_STEP =
            "CREATE TALBE " + TABLE_STEP +
                    " (_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " STEP TEXT);";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //TODO: CREATE NECESSARY TABLES
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) { }
}
