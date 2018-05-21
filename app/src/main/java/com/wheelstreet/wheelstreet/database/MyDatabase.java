package com.wheelstreet.wheelstreet.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.wheelstreet.wheelstreet.model.Data;
import com.wheelstreet.wheelstreet.model.ProfileDatabase;
import com.wheelstreet.wheelstreet.model.QuestionDatabase;

import java.util.ArrayList;
import java.util.List;

public class MyDatabase extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "WheelStrrt";

    // Table name
    private static final String TABLE_PROFILE_DETAILS = "PROFILE_DETAILS";

    // From name
    private static final String TABLE_QUESTION_WITH_ANSWER_DETAILS = "QUESTION_AND_ANSWER";

    // From Latitude
    private static final String FB_ID = "FACEBOOK_ID";

    //From longitude
    private static final String FB_USER_NAME = "FACEBOOK_USER_NAME";

    //Depature time
    private static final String FB_EMAIL_ID = "FACEBOOK_EMAIL_ID";

    // Favourites
    private static final String FB_PROFILE_PIC = "FACEBOOK_PROFILE_PIC";

    // To name
    private static final String FB_GENDER = "FACEBOOK_GENDER";

    // To latitude
    private static final String FB_PHONENUMBER = "FACEBOOK_PHONE_NUMBER";

    private static final String QUESTION_ID = "QUESTION_ID";

    private static final String QUESTION = "QUESTION";

    private static final String ANSWER_ID = "ANSWER_ID";

    private static final String ANSWER = "ANSWER";

    private static final String DATA_TYPE = "DATA_TYPE";
    private static final String QUESTION_DISPLAYED = "QUESTION_DISPLAYED";

    private static final String ANSWER_DISPLAYED = "ANSWER_DISPLAYED";
    private String POSITION="POSITION";
    private String FB_AGE="AGE";

    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_PROFILE_DETAILS + "" +
                "(" + FB_ID + " TEXT,"
                + FB_USER_NAME + " TEXT," +
                FB_EMAIL_ID + " TEXT," +
                FB_PROFILE_PIC + " TEXT,"
                + FB_GENDER + " TEXT,"
                + FB_AGE + " TEXT,"
                + FB_PHONENUMBER + " TEXT" +
                ")";
        String CREATE_TABLE_QUERY_QUESTION = "CREATE TABLE " + TABLE_QUESTION_WITH_ANSWER_DETAILS + "" +
                "(" + QUESTION_ID + " TEXT,"
                + QUESTION + " TEXT," +
                DATA_TYPE + " TEXT," +
                ANSWER_ID + " TEXT,"+
                ANSWER + " TEXT," +
                QUESTION_DISPLAYED+" TEXT,"+
                ANSWER_DISPLAYED+" TEXT,"+
                POSITION+" TEXT"+
                ")";
        sqLiteDatabase.execSQL(CREATE_TABLE_QUERY);
        sqLiteDatabase.execSQL(CREATE_TABLE_QUERY_QUESTION);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE_DETAILS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION_WITH_ANSWER_DETAILS);

        // Create tables again
        onCreate(sqLiteDatabase);
    }

    /**
     * This method is used to insert the photo in table.
     *
     * @param photo object
     */

    public void insertCollections(ProfileDatabase photo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(FB_ID, photo.getFaceBookId());
        values.put(FB_USER_NAME, photo.getProfileName());
        values.put(FB_EMAIL_ID, photo.getProfileEmailId());
        values.put(FB_PROFILE_PIC, photo.getProfilePic());
        values.put(FB_GENDER, photo.getProfileGender());
        values.put(FB_PHONENUMBER, photo.getProfileNumber());
        values.put(FB_AGE, photo.getProfileAge());

        db.insert(TABLE_PROFILE_DETAILS, null, values);
        db.close(); // Closing database connection
    }

    /**
     * This method is used to get all collection from the table
     *
     * @return list
     */
    public List<ProfileDatabase> getAllProfileDetails() {
        List<ProfileDatabase> photosList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PROFILE_DETAILS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ProfileDatabase photo = new ProfileDatabase();
                photo.setFaceBookId(cursor.getString(0));
                photo.setProfileName(cursor.getString(1));
                photo.setProfileEmailId(cursor.getString(2));
                photo.setProfilePic(cursor.getString(3));
                photo.setProfileGender(cursor.getString(4));
                photo.setProfileAge(cursor.getString(5));

                photo.setProfileNumber(cursor.getString(6));

                photosList.add(photo);
            } while (cursor.moveToNext());
        }

        // return photo list
        return photosList;
    }


    public int updateContact(String updateString, QuestionDatabase collection) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ANSWER,collection.getAnswer());
        values.put(ANSWER_ID,collection.getAnswerId());
        values.put(ANSWER_DISPLAYED,collection.getAnswerDisplay());

        // updating row
        return db.update(TABLE_QUESTION_WITH_ANSWER_DETAILS, values, QUESTION_ID + " = ?",
                new String[]{updateString});
    }
    public int updateQuestion(String updateString, QuestionDatabase collection) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(QUESTION_DISPLAYED,collection.getQuestionDisplay());
        values.put(ANSWER_DISPLAYED,collection.getAnswerDisplay());
        values.put(POSITION,collection.getPosition());

        // updating row
        return db.update(TABLE_QUESTION_WITH_ANSWER_DETAILS, values, QUESTION_ID + " = ?",
                new String[]{updateString});
    }

    /**
     * This method is used to delete the record from db
     */
    public void deleteRecord() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_QUESTION_WITH_ANSWER_DETAILS, null, null);
    }

    public void insertQuestions(QuestionDatabase connection) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(QUESTION_ID, connection.getQuestionId());
        values.put(QUESTION, connection.getQuestion());
        values.put(DATA_TYPE, connection.getDataType());
        values.put(QUESTION_DISPLAYED, connection.getQuestionDisplay());
        values.put(ANSWER_DISPLAYED, connection.getAnswerDisplay());
        values.put(POSITION, connection.getPosition());


        db.insert(TABLE_QUESTION_WITH_ANSWER_DETAILS, null, values);
        db.close(); // Closing database connection
    }

    public List<QuestionDatabase> getQuestionDetails() {
        List<QuestionDatabase> photosList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  QUESTION_ID, QUESTION, DATA_TYPE,ANSWER,POSITION  FROM " + TABLE_QUESTION_WITH_ANSWER_DETAILS+ " WHERE QUESTION_DISPLAYED ='yes'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                QuestionDatabase photo = new QuestionDatabase();
                photo.setQuestionId(cursor.getString(0));
                photo.setQuestion(cursor.getString(1));
                photo.setDataType(cursor.getString(2));

                photo.setAnswer(cursor.getString(3));
                photo.setPosition(cursor.getString(4));

                photosList.add(photo);
            } while (cursor.moveToNext());
        }

        // return photo list
        return photosList;
    }
    public List<QuestionDatabase> getQuestionId() {
        List<QuestionDatabase> photosList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  QUESTION_ID FROM " + TABLE_QUESTION_WITH_ANSWER_DETAILS + " WHERE QUESTION_DISPLAYED='yes' AND ANSWER_DISPLAYED='yes'" ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                QuestionDatabase photo = new QuestionDatabase();
                photo.setQuestionId(cursor.getString(0));

                photosList.add(photo);
            } while (cursor.moveToNext());
        }

        // return photo list
        return photosList;
    }
    public List<QuestionDatabase> getAnswerDetails() {
        List<QuestionDatabase> photosList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  ANSWER_ID, ANSWER, DATA_TYPE,QUESTION FROM " + TABLE_QUESTION_WITH_ANSWER_DETAILS + " WHERE QUESTION_ID==ANSWER_ID AND QUESTION_DISPLAYED='yes' AND ANSWER_DISPLAYED='yes'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if((c != null && c.getCount() >0 && c.moveToFirst())){
            if (c.moveToFirst()) {
                do {
                    QuestionDatabase photo = new QuestionDatabase();
                    photo.setDataType(c.getString(2));
                    photo.setAnswer(c.getString(1));

                    photo.setAnswerId(c.getString(0));
                    photo.setQuestion(c.getString(3));

                    photosList.add(photo);
                } while (c.moveToNext());
            }
        }
        // return photo list
        return photosList;
    }

    public int updateProfileDetails(String strUserName, String strEmail, String strPhoneNumber, String strAge, String strGender, String userToken) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
        values.put(FB_USER_NAME, strUserName);
        values.put(FB_EMAIL_ID, strEmail);
        values.put(FB_GENDER, strGender);
        values.put(FB_AGE, strAge);

        values.put(FB_PHONENUMBER, strPhoneNumber);
            // updating row
            return db.update(TABLE_PROFILE_DETAILS, values, FB_ID + " = ?",
                    new String[]{userToken});


    }
}

