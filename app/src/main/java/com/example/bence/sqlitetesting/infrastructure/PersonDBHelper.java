package com.example.bence.sqlitetesting.infrastructure;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bence on 31.08.15.
 */
public class PersonDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "person.db";

    private static final int DATABASE_VERSION = 1;

    public PersonDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE person ( \n" +
                "    id INTEGER NOT NULL PRIMARY KEY, \n" +
                "    name TEXT, \n" +
                "    parentId INTEGER, \n" +
                "    timestamp TIMESTAMP, \n" +
                "    birthday DATE, \n" +
                "    weight REAL, \n" +
                "    image BLOB \n" +
                "    );";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
