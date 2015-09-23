package com.example.bence.sqlitetesting.infrastructure.android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bence.sqlitetesting.domain.Person;
import com.example.bence.sqlitetesting.util.UtcDateFormat;

import static com.example.bence.sqlitetesting.infrastructure.android.PersonContract.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by bence on 31.08.15.
 */
public class PersonRepositoryImpl implements com.example.bence.sqlitetesting.infrastructure.PersonRepository {
    private final Context context;
    private final PersonDbHelper dbHelper;
    private SQLiteDatabase db;

    private final UtcDateFormat utcDateFormat = new UtcDateFormat();

    public PersonRepositoryImpl(Context context) {
        this.context = context;
        dbHelper = new PersonDbHelper(context);
    }

    @Override
    public boolean open() {
        db = dbHelper.getWritableDatabase();
        return db.isOpen();
    }

    @Override
    public void close() {
        db.close();
    }

    @Override
    public boolean drop() {
        close();
        return context.deleteDatabase(PersonDbHelper.DATABASE_NAME);
    }

    @Override
    public void createPerson(Person person) {
        ContentValues values = personToContentValues(person);
        db.insert(PersonTable.TABLE_NAME, null, values);
    }

    @Override
    public List<Person> getAllPersons() {
        String sql = PersonTable.SQL_SELECT_ALL_COLUMNS + " ORDER BY " + PersonTable._ID + " ASC";
        Cursor cursor = db.rawQuery(sql, null);

        List<Person> result = new ArrayList<>();
        while (cursor.moveToNext()) {
            Person person = cursorToPerson(cursor);
            result.add(person);
        }
        cursor.close();
        return result;
    }

    @Override
    public Person getPerson(int id) {
        String sql = PersonTable.SQL_SELECT_ALL_COLUMNS + " WHERE " + PersonTable._ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.rawQuery(sql, selectionArgs);

        Person result = null;
        if (cursor.moveToFirst()) {
            result = cursorToPerson(cursor);
        }
        cursor.close();
        return result;
    }

    @Override
    public void deletePerson(int id) {
        String selection = PersonTable._ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        db.delete(PersonTable.TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public void updatePerson(Person person) {
        ContentValues values = personToContentValues(person);

        String selection = PersonTable._ID + " = ?";
        String[] selectionArgs = {String.valueOf(person.getId())};
        db.update(PersonTable.TABLE_NAME, values, selection, selectionArgs);
    }

    private ContentValues personToContentValues(Person person) {
        ContentValues values = new ContentValues();
        values.put(PersonTable._ID, person.getId());
        values.put(PersonTable.COLUMN_NAME_NAME, person.getName());
        values.put(PersonTable.COLUMN_NAME_TIMESTAMP, utcDateFormat.formatDateTime(person.getTimestamp()));
        values.put(PersonTable.COLUMN_NAME_BIRTHDAY, utcDateFormat.formatDate(person.getBirthday()));
        values.put(PersonTable.COLUMN_NAME_WEIGHT, person.getWeight());
        values.put(PersonTable.COLUMN_NAME_IMAGE, person.getImageAsBytes());
        return values;
    }

    private Person cursorToPerson(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(PersonTable._ID));
        String name = cursor.getString(cursor.getColumnIndex(PersonTable.COLUMN_NAME_NAME));
        Date timestamp = utcDateFormat.parseDateTime(cursor.getString(cursor.getColumnIndex(PersonTable.COLUMN_NAME_TIMESTAMP)));
        Date birthday = utcDateFormat.parseDate(cursor.getString(cursor.getColumnIndex(PersonTable.COLUMN_NAME_BIRTHDAY)));

        Float weight = null;
        if (!cursor.isNull(cursor.getColumnIndex(PersonTable.COLUMN_NAME_WEIGHT))) {
            weight = cursor.getFloat(cursor.getColumnIndex(PersonTable.COLUMN_NAME_WEIGHT));
        }

        byte[] imageAsBytes = null;
        if (!cursor.isNull(cursor.getColumnIndex(PersonTable.COLUMN_NAME_IMAGE))) {
            imageAsBytes = cursor.getBlob(cursor.getColumnIndex(PersonTable.COLUMN_NAME_IMAGE));
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
