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
        contentValues.put("timestamp", utcDateFormat.formatDateTime(person.getTimestamp()));
        contentValues.put("birthday", utcDateFormat.formatDate(person.getBirthday()));
        contentValues.put("weight", person.getWeight());
        contentValues.put("image", person.getImageAsBytes());
        db.insert("person", null, contentValues);
    }

    public List<Person> getAllPersons() {
        List<Person> result = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT id, name, timestamp, birthday, weight, image FROM person",
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

        Cursor cursor = db.rawQuery("SELECT id, name, timestamp, birthday, weight, image FROM person WHERE id = ?",
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

    public void updatePerson(Person person) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", person.getId());
        contentValues.put("name", person.getName());
        contentValues.put("timestamp", utcDateFormat.formatDateTime(person.getTimestamp()));
        contentValues.put("birthday", utcDateFormat.formatDate(person.getBirthday()));
        contentValues.put("weight", person.getWeight());
        contentValues.put("image", person.getImageAsBytes());

        String[] parameter = new String[]{String.valueOf(person.getId())};

        db.update("person", contentValues, "id = ?", parameter);
    }

    private Person convertToPerson(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex("id"));

        String name = cursor.getString(cursor.getColumnIndex("name"));

        Date timestamp = utcDateFormat.parseDateTime(cursor.getString(cursor.getColumnIndex("timestamp")));

        Date birthday = utcDateFormat.parseDate(cursor.getString(cursor.getColumnIndex("birthday")));

        Float weight = null;
        if (!cursor.isNull(cursor.getColumnIndex("weight"))) {
            weight = cursor.getFloat(cursor.getColumnIndex("weight"));
        }

        byte[] imageAsBytes = null;
        if (!cursor.isNull(cursor.getColumnIndex("image"))) {
            imageAsBytes = cursor.getBlob(cursor.getColumnIndex("image"));
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
