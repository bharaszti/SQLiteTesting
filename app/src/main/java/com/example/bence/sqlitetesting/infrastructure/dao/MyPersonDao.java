package com.example.bence.sqlitetesting.infrastructure.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.example.bence.sqlitetesting.domain.Person;
import com.example.bence.sqlitetesting.util.UtcDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by bence on 14.09.15.
 */
public class MyPersonDao {

    private static final String TAB_PERSON = "person";

    private static final String COL_ID = BaseColumns._ID;
    private static final String COL_NAME = "name";
    private static final String COL_TIMESTAMP = "timestamp";
    private static final String COL_BIRTHDAY = "birthday";
    private static final String COL_WEIGHT = "weight";
    private static final String COL_IMAGE = "image";

    private static final String SQL_SELECT_ALL_COLUMNS = "SELECT \n" +
            COL_ID + "," +
            COL_NAME + "," +
            COL_TIMESTAMP + "," +
            COL_BIRTHDAY + "," +
            COL_WEIGHT + "," +
            COL_IMAGE +
            " FROM " + TAB_PERSON;

    private final UtcDateFormat utcDateFormat = new UtcDateFormat();
    private SQLiteDatabase database;

    public void setDatabase(SQLiteDatabase database) {
        this.database = database;
    }

    public void createPerson(Person person) {
        ContentValues values = personToContentValues(person);
        database.insert(TAB_PERSON, null, values);
    }

    public List<Person> getAllPersons() {
        String sql = SQL_SELECT_ALL_COLUMNS + " ORDER BY "+ COL_ID + " ASC";

        Cursor cursor = database.rawQuery(sql, null);
        List<Person> result = new ArrayList<>();
        while (cursor.moveToNext()) {
            Person person = cursorToPerson(cursor);
            result.add(person);
        }
        cursor.close();
        return result;
    }

    public Person getPerson(int id) {
        String sql = SQL_SELECT_ALL_COLUMNS + " WHERE " + COL_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = database.rawQuery(sql, selectionArgs);
        Person result = null;
        if (cursor.moveToFirst()) {
            result = cursorToPerson(cursor);
        }
        cursor.close();
        return result;
    }

    public void deletePerson(int id) {
        String selection = COL_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        database.delete(TAB_PERSON, selection, selectionArgs);
    }

    public void updatePerson(Person person) {
        ContentValues values = personToContentValues(person);
        String selection = COL_ID + " = ?";
        String[] selectionArgs = {String.valueOf(person.getId())};
        database.update(TAB_PERSON, values, selection, selectionArgs);
    }

    private ContentValues personToContentValues(Person person) {
        ContentValues values = new ContentValues();
        values.put(COL_ID, person.getId());
        values.put(COL_NAME, person.getName());
        values.put(COL_TIMESTAMP, utcDateFormat.formatDateTime(person.getTimestamp()));
        values.put(COL_BIRTHDAY, utcDateFormat.formatDate(person.getBirthday()));
        values.put(COL_WEIGHT, person.getWeight());
        values.put(COL_IMAGE, person.getImageAsBytes());
        return values;
    }

    private Person cursorToPerson(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(COL_ID));
        String name = cursor.getString(cursor.getColumnIndex(COL_NAME));
        Date timestamp = utcDateFormat.parseDateTime(cursor.getString(cursor.getColumnIndex(COL_TIMESTAMP)));
        Date birthday = utcDateFormat.parseDate(cursor.getString(cursor.getColumnIndex(COL_BIRTHDAY)));

        Float weight = null;
        if (!cursor.isNull(cursor.getColumnIndex(COL_WEIGHT))) {
            weight = cursor.getFloat(cursor.getColumnIndex(COL_WEIGHT));
        }

        byte[] imageAsBytes = null;
        if (!cursor.isNull(cursor.getColumnIndex(COL_IMAGE))) {
            imageAsBytes = cursor.getBlob(cursor.getColumnIndex(COL_IMAGE));
        }

        Person person = new Person();
        person.setId(id);
        person.setName(name);
        person.setTimestamp(timestamp);
        person.setBirthday(birthday);
        person.setWeight(weight);
        person.setImageAsBytes(imageAsBytes);

        return person;
    }
}
