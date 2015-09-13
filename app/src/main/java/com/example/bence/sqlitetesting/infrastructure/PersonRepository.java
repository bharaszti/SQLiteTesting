package com.example.bence.sqlitetesting.infrastructure;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bence.sqlitetesting.domain.Person;
import com.example.bence.sqlitetesting.util.UtcDateFormat;
import static com.example.bence.sqlitetesting.infrastructure.PersonContract.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by bence on 31.08.15.
 */
public class PersonRepository {
    private final Context context;
    private final PersonDbHelper dbHelper;
    private SQLiteDatabase db;

    private final UtcDateFormat utcDateFormat = new UtcDateFormat();

    public PersonRepository(Context context) {
        this.context = context;
        dbHelper = new PersonDbHelper(context);
    }

    public boolean open() {
        db = dbHelper.getWritableDatabase();
        return db.isOpen();
    }

    public boolean drop() {
        return context.deleteDatabase(PersonDbHelper.DATABASE_NAME);
    }

    public void createPerson(Person person) {
        ContentValues values = personToContentValues(person);
        db.insert(PersonTable.TABLE_NAME, null, values);
    }

    public List<Person> getAllPersons() {
        String sortOrder = PersonTable._ID + " ASC";
        String sql = PersonTable.SQL_SELECT_ALL_COLUMNS + " ORDER BY " + sortOrder;
        Cursor cursor = db.rawQuery(sql, null);

        List<Person> result = new ArrayList<>();
        while (cursor.moveToNext()) {
            Person person = cursorToPerson(cursor);
            result.add(person);
        }
        cursor.close();
        return result;
    }

    public Person getPerson(int id) {
        String selection = PersonTable._ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        String sql = PersonTable.SQL_SELECT_ALL_COLUMNS + " WHERE " + selection;
        Cursor cursor = db.rawQuery(sql, selectionArgs);

        Person result = null;
        if (cursor.moveToFirst()) {
            result = cursorToPerson(cursor);
        }
        cursor.close();
        return result;
    }

    public void deletePerson(int id) {
        String selection = PersonTable._ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        db.delete(PersonTable.TABLE_NAME, selection, selectionArgs);
    }

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
