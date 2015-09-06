package com.example.bence.sqlitetesting.infrastructure;

import android.test.AndroidTestCase;

import com.example.bence.sqlitetesting.domain.Person;
import com.example.bence.sqlitetesting.util.UtcDateFormat;

import java.util.Date;
import java.util.List;

/**
 * Created by bence on 29.08.15.
 */
public class PersonRepositoryTest extends AndroidTestCase {
    private PersonRepository repository;
    private PersonRepositoryTestHelper helper;
    private UtcDateFormat utcDateFormat;

    /*
        TODO: SQL statements for:
         - createPerson DB
         - drop DB
         - insert
         - select
         - delete
         - update

        TODO: classes for contract and helper

        TODO: setup and teardown of test cases (createPerson fresh DB for each test case)

        TODO: write and read all data types

        TODO: how to use a test DB instance?

     */

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        repository = new PersonRepository(this.mContext);
        boolean success = repository.open();
        assertTrue(success);

        helper = new PersonRepositoryTestHelper();
        utcDateFormat = new UtcDateFormat();
    }

    @Override
    protected void tearDown() throws Exception {
        boolean success = repository.drop();
        assertTrue(success);

        super.tearDown();
    }

    public void testShouldInsertAndGetEntries() {
        // given
        Person person1 = new Person(1, "Senior");
        Person person2 = new Person(2, "Junior");

        repository.createPerson(person1);
        repository.createPerson(person2);

        // when
        List<Person> persisted = repository.getAllPersons();

        // then
        helper.assertPersons(persisted, person1, person2);
    }

    public void testShouldGetEntryById() {
        // given
        Person person1 = new Person(1, "Neo");
        Person person2 = new Person(2, "Trinity");

        repository.createPerson(person1);
        repository.createPerson(person2);

        // when
        Person persisted = repository.getPersonById(2);

        // then
        assertEquals(person2, persisted);
    }

    public void testShouldDeleteEntryById() {
        // given
        Person person1 = new Person(1, "Smith");
        Person person2 = new Person(2, "Braun");

        repository.createPerson(person1);
        repository.createPerson(person2);

        // when
        repository.deletePersonById(1);

        // then
        List<Person> persisted = repository.getAllPersons();
        helper.assertPersons(persisted, person2);
    }

    public void testShouldPersistDateTime() {
        // given
        Date dateTime = utcDateFormat.parseDateTime("2001-02-03 04:05:06.000");
        Person expected = new Person(1, "Somebody", dateTime);

        repository.createPerson(expected);

        // when
        Person persisted = repository.getPersonById(1);

        // then
        assertEquals(dateTime, persisted.getTimestamp());
    }

    public void testShouldPersistDateTimeForNull() {
        // given
        Person expected = new Person(1, "Somebody", null);

        repository.createPerson(expected);

        // when
        Person persisted = repository.getPersonById(1);

        // then
        assertNull(persisted.getTimestamp());
    }

    public void testShouldPersistDate() {
        // given
        Date birthday = utcDateFormat.parseDate("2001-02-03");
        Person expected = new Person(1, "Somebody", null, birthday);

        repository.createPerson(expected);

        // when
        Person persisted = repository.getPersonById(1);

        // then
        assertEquals(birthday, persisted.getBirthday());
        assertEquals("2001-02-03 00:00:00.000", utcDateFormat.formatDateTime(persisted.getBirthday()));
    }


}
