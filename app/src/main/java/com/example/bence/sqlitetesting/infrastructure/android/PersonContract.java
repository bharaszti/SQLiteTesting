package com.example.bence.sqlitetesting.infrastructure.android;

import android.content.ContentValues;
import android.provider.BaseColumns;

import com.example.bence.sqlitetesting.domain.Person;

/**
 * Created by bence on 13.09.15.
 */
public class PersonContract {
    public static final String SQL_CREATE_TABLE_PERSON =
            "CREATE TABLE " + PersonTable.TABLE_NAME + "(" +
                    PersonTable._ID + " INTEGER NOT NULL PRIMARY KEY," +
                    PersonTable.COLUMN_NAME_NAME + " TEXT," +
                    PersonTable.COLUMN_NAME_TIMESTAMP + " TIMESTAMP," +
                    PersonTable.COLUMN_NAME_BIRTHDAY + " DATE," +
                    PersonTable.COLUMN_NAME_WEIGHT + " REAL," +
                    PersonTable.COLUMN_NAME_IMAGE + " BLOB" +
                    ");";

    public PersonContract() {
    }

    public static abstract class PersonTable implements BaseColumns {
        public static final String TABLE_NAME = "person";

        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_TIMESTAMP = "timestamp";
        public static final String COLUMN_NAME_BIRTHDAY = "birthday";
        public static final String COLUMN_NAME_WEIGHT = "weight";
        public static final String COLUMN_NAME_IMAGE = "image";

        public static final String SQL_SELECT_ALL_COLUMNS = "SELECT \n" +
                PersonTable._ID + "," +
                PersonTable.COLUMN_NAME_NAME + "," +
                PersonTable.COLUMN_NAME_TIMESTAMP + "," +
                PersonTable.COLUMN_NAME_BIRTHDAY + "," +
                PersonTable.COLUMN_NAME_WEIGHT + "," +
                PersonTable.COLUMN_NAME_IMAGE +
                " FROM " + PersonTable.TABLE_NAME;
    }


}
