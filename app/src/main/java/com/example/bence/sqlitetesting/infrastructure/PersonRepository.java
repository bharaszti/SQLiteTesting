package com.example.bence.sqlitetesting.infrastructure;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bence.sqlitetesting.domain.Person;
import com.example.bence.sqlitetesting.util.UtcDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by bence on 31.08.15.
 */
public class PersonRepository {
    private final Context context;
    private final UtcDateFormat utcDateFormat;
    private final PersonDBHelper dbHelper;
    private SQLiteDatabase db;

    public PersonRepository(Context context) {
        this.context = context;
        dbHelper = new PersonDBHelper(context);
        utcDateFormat = new UtcDateFormat();
    }

    public boolean open() {
        db = dbHelper.getWritableDatabase();
        return db.isOpen();
    }

    public boolean drop() {
        return context.deleteDatabase(PersonDBHelper.DATABASE_NAME);
    }

    public void createPerson(Person person) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", person.getId());
        contentValues.put("name", person.getName());
        if (person.getTimestamp() != null)
            contentValues.put("timestamp", utcDateFormat.formatDateTime(person.getTimestamp()));
        if (person.getBirthday() != null)
            contentValues.put("birthday", utcDateFormat.formatDate(person.getBirthday()));
        db.insert("person", null, contentValues);
    }

    public List<Person> getAllPersons() {
        List<Person> result = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT id, name, timestamp, birthday FROM person",
                new String[]{});

        while (cursor.moveToNext()) {
            Person person = convertToPerson(cursor);
            result.add(person);
        }
        cursor.close();
        return result;
    }

    public Person getPersonById(int id) {
        Person result = null;

        Cursor cursor = db.rawQuery("SELECT id, name, timestamp, birthday FROM person WHERE id = ?",
                new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            result = convertToPerson(cursor);
        }
        cursor.close();
        return result;
    }

    public void deletePersonById(int id) {
        db.delete("person", "id = ?", new String[]{String.valueOf(id)});
    }

    private Person convertToPerson(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex("id"));
        String name = cursor.getString(cursor.getColumnIndex("name"));
        Date timestamp = utcDateFormat.parseDateTime(cursor.getString(cursor.getColumnIndex("timestamp")));
        Date birthday = utcDateFormat.parseDate(cursor.getString(cursor.getColumnIndex("birthday")));
        return new Person(id, name, timestamp, birthday);
    }
}
