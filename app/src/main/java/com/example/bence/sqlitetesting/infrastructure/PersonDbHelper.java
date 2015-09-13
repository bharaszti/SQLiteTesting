package com.example.bence.sqlitetesting.infrastructure;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bence on 31.08.15.
 */
public class PersonDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "person.db";

    private static final int DATABASE_VERSION = 1;

    public PersonDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PersonContract.SQL_CREATE_TABLE_PERSON);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
