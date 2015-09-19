package com.example.bence.sqlitetesting.infrastructure.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.bence.sqlitetesting.domain.Person;
import com.example.bence.sqlitetesting.infrastructure.PersonRepository;

import java.util.List;

/**
 * Created by bence on 14.09.15.
 */
public class MyPersonRepositoryImpl implements PersonRepository {

    private final Context context;
    private final MyPersonDbHelper dbHelper;
    private MyPersonDao personDao;
    private SQLiteDatabase database;

    public MyPersonRepositoryImpl(Context context) {
        this.context = context;
        dbHelper = new MyPersonDbHelper(context);
        personDao = new MyPersonDao();
    }

    @Override
    public boolean open() {
        database = dbHelper.getWritableDatabase();
        personDao.setDatabase(database);
        return database.isOpen();
    }

    @Override
    public boolean drop() {
        database.close();
        return dbHelper.dropDatabase();
    }

    @Override
    public void createPerson(Person person) {
        personDao.createPerson(person);
    }

    @Override
    public List<Person> getAllPersons() {
        return personDao.getAllPersons();
    }

    @Override
    public Person getPerson(int id) {
        return personDao.getPerson(id);
    }

    @Override
    public void deletePerson(int id) {
        personDao.deletePerson(id);
    }

    @Override
    public void updatePerson(Person person) {
        personDao.updatePerson(person);
    }
}
