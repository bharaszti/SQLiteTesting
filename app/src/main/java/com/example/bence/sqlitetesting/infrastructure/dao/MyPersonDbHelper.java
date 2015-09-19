package com.example.bence.sqlitetesting.infrastructure.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.bence.sqlitetesting.util.FileUtil;

/**
 * Created by bence on 31.08.15.
 */
public class MyPersonDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "person.db";

    private static final int DATABASE_VERSION = 1;
    private Context context;

    public MyPersonDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO: use AssetManager: context.getAssets()
        db.execSQL(FileUtil.readResourceFile("create-database-v1.sql"));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean dropDatabase() {
        return context.deleteDatabase(DATABASE_NAME);
    }

}
