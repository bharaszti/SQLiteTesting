package com.example.bence.sqlitetesting.infrastructure;

import com.example.bence.sqlitetesting.domain.Person;

import java.util.List;

/**
 * Created by bence on 14.09.15.
 */
public interface PersonRepository {
    boolean open();

    boolean drop();

    void createPerson(Person person);

    List<Person> getAllPersons();

    Person getPerson(int id);

    void deletePerson(int id);

    void updatePerson(Person person);

    void close();
}
