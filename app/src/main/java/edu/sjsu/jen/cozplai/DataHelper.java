package edu.sjsu.jen.cozplai;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by jen0e on 12/5/2017.
 */

public class DataHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = "DataHelper";

    public static final int DATABASE_VERSION = 6;
    public static final String DATABASE_NAME = "Data.db";

    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String TABLE_CHARACTER = "character";
    private static final String TABLE_CONVENTION = "convention";

    private static final String CREATE_TABLE_CHARACTER =
            "CREATE TABLE " + TABLE_CHARACTER +
                    " (_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " NAME TEXT, SOURCE TEXT, COMPLETION INT," +
                    " ELEMENTS_ID TEXT, PHOTO_ID TEXT);";

    private static final String CREATE_TABLE_CONVENTION =
            "CREATE TABLE " + TABLE_CONVENTION +
                    " (_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " NAME TEXT, ADDRESS TEXT, DATE TEXT);";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_CHARACTER);
        sqLiteDatabase.execSQL(CREATE_TABLE_CONVENTION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) { }

    public void addCharacter(Character character){
        SQLiteDatabase db = getWritableDatabase();

        //insert table into db
        String name = character.getName();
        String source = character.getSource();
        int completion = character.getCompletion();
        String elementsId = name + "_elements"; //id for elements table for character
        String photoPath = "";
        if (character.getPhotoUri() != null) {
            photoPath = character.getPhotoUri().toString();
        }

        String CREATE_CHARACTER = "INSERT INTO " + TABLE_CHARACTER +
                " (NAME, SOURCE, COMPLETION, ELEMENTS_ID, PHOTO_ID)" +
                " VALUES ('" + name + "', '" + source + "', " + completion +
                ", '" + elementsId + "', '" + photoPath + "');";

        db.execSQL(CREATE_CHARACTER);

        //create table for elements for new character
        String CREATE_TABLE_ELEMENT =
                "CREATE TABLE " + elementsId +
                        " (_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " NAME TEXT, COMPLETION INT);";

        db.execSQL(CREATE_TABLE_ELEMENT);

    }

    public void deleteCharacter(Character character){
        SQLiteDatabase db = getWritableDatabase();

        String name = character.getName();

        String DELETE_CHARACTER = "DELETE FROM " + TABLE_CHARACTER +
                " WHERE NAME = '" + name + "';";

        String DELETE_CHARACTER_ELEMENTS = "DROP TABLE " +
                name + "_elements;";

        db.execSQL(DELETE_CHARACTER);
        db.execSQL(DELETE_CHARACTER_ELEMENTS);
    }

    public void addConvention(Convention convention){
        SQLiteDatabase db = getWritableDatabase();

        String name = convention.getName();
        String address = convention.getAddress();
        String date = convention.getDate();

        String CREATE_CONVENTION = "INSERT INTO " + TABLE_CONVENTION +
                " (NAME, ADDRESS, DATE) VALUES ('" + name + "', '" +
                address + "', '" + date + "');";

        db.execSQL(CREATE_CONVENTION);

    }

    public void addElement(Character character, Element element){
        SQLiteDatabase db = getWritableDatabase();

        String characterName = character.getName();
        String elementName = element.getName();
        int elementCompletion = element.getCompletion();

        String CREATE_ELEMENT = "INSERT INTO " + characterName +
                "_elements (NAME, COMPLETION) VALUES ('" +
                elementName + "', " + elementCompletion + ");";

        db.execSQL(CREATE_ELEMENT);
    }

    public void deleteElement(Character character, Element element){
        SQLiteDatabase db = getWritableDatabase();

        String characterName = character.getName();
        String elementName = element.getName();

        String DELETE_ELEMENT = "DELETE FROM " + characterName +
                "_elements WHERE NAME = '" + elementName + "';";

        db.execSQL(DELETE_ELEMENT);

    }

    public void editElement(Character character, Element element, int id){
        SQLiteDatabase db = getWritableDatabase();

        String characterName = character.getName();
        String elementName = element.getName();
        int elementCompletion = element.getCompletion();
        int elementId = id;

        String EDIT_ELEMENT = "UPDATE " + characterName +
                "_elements SET NAME = '" + elementName +
                "', COMPLETION = " + elementCompletion +
                " WHERE _ID = " + elementId + ";";

        db.execSQL(EDIT_ELEMENT);
    }

    public int getCharacterCompletion(Character character){
        SQLiteDatabase db = getReadableDatabase();

        String characterName = character.getName();

        String COUNT_ELEMENTS = "SELECT * FROM " +
                characterName + "_elements";

        Cursor a = db.rawQuery(COUNT_ELEMENTS, null);
        int elementCount = a.getCount();
        Log.d("DataHelper", "elementCount: " + elementCount);


        String COMPLETION_TOTAL = "SELECT SUM(COMPLETION) FROM " +
                characterName + "_elements";

        Cursor b = db.rawQuery(COMPLETION_TOTAL, null);
        b.moveToFirst();
        int elementCompletion = b.getInt(0);
        Log.d("DataHelper", "elementCompletion: " + elementCompletion);

        int totalPossible = elementCount * 100;
        Log.d("DataHelper", "totalPossible: " + totalPossible);
        float characterCompletion = elementCompletion / (float) totalPossible;
        characterCompletion = characterCompletion * 100;
        Log.d("DataHelper", "getCharacterCompletion: " + characterCompletion);
        return (int) characterCompletion;

    }



}
