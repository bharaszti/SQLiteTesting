package com.example.bence.sqlitetesting.infrastructure;

import android.test.AndroidTestCase;

import com.example.bence.sqlitetesting.domain.Person;
import com.example.bence.sqlitetesting.infrastructure.dao.MyPersonRepositoryImpl;
import com.example.bence.sqlitetesting.util.UtcDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by bence on 29.08.15.
 *
 * See SQLite data types:
 * http://www.4js.com/online_documentation/fjs-fgl-manual-html/c_fgl_odiagsqt_data_dictionary.html
 */
public class PersonRepositoryTest extends AndroidTestCase {
    private PersonRepository repository;
    private PersonRepositoryTestHelper helper;
    private UtcDateFormat utcDateFormat;

    /*
        SQL statements for:
         - create DB
         - drop DB
         - insert
         - select
         - delete
         - update

        Setup and teardown of test cases (createPerson fresh DB for each test case)

        Write and read all data types
         - INTEGER
         - TEXT
         - DATE
         - DATETIME
         - FLOAT
         - BLOB

        Classes for contract and helper: see http://developer.android.com/training/basics/data-storage/databases.html
        Classes for Repository/DAO design.

        TODO: how to use a test DB instance?

     */

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        repository = new MyPersonRepositoryImpl(this.mContext); // or PersonRepositoryImpl
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
        Person persisted = repository.getPerson(2);

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
        repository.deletePerson(1);

        // then
        List<Person> persisted = repository.getAllPersons();
        helper.assertPersons(persisted, person2);
    }

    public void testShouldUpdateEntry() {
        // given
        Person person1 = new Person();
        person1.setId(1);
        person1.setName("Smith");
        person1.setBirthday(utcDateFormat.parseDate("2001-02-03"));
        person1.setTimestamp(utcDateFormat.parseDateTime("2001-02-03 04:05:06.123"));
        person1.setWeight(12.34f);
        person1.setImageAsBytes("image".getBytes());

        Person person2 = new Person();
        person2.setId(1);
        person2.setName("Braun");
        person2.setBirthday(utcDateFormat.parseDate("2015-02-03"));
        person2.setTimestamp(utcDateFormat.parseDateTime("2015-02-03 04:05:06.123"));
        person2.setWeight(45.67f);
        person2.setImageAsBytes("picture".getBytes());

        repository.createPerson(person1);

        // when
        repository.updatePerson(person2);

        // then
        List<Person> persisted = repository.getAllPersons();
        helper.assertPersons(persisted, person2);
    }

    public void testShouldPersistTimestamp() {
        // given
        int id = 1;
        Date timestamp = utcDateFormat.parseDateTime("2001-02-03 04:05:06.123");

        Person person = new Person();
        person.setId(id);
        person.setTimestamp(timestamp);

        repository.createPerson(person);

        // when
        Person persisted = repository.getPerson(id);

        // then
        assertEquals(timestamp, persisted.getTimestamp());
    }

    public void testShouldPersistEmptyTimestamp() {
        // given
        int id = 1;

        Person person = new Person();
        person.setId(id);
        person.setTimestamp(null);

        repository.createPerson(person);

        // when
        Person persisted = repository.getPerson(id);

        // then
        assertNull(persisted.getTimestamp());
    }

    public void testShouldPersistDate() {
        // given
        int id = 1;
        Date birthday = utcDateFormat.parseDate("2001-02-03");

        Person person = new Person();
        person.setId(id);
        person.setBirthday(birthday);

        repository.createPerson(person);

        // when
        Person persisted = repository.getPerson(id);

        // then
        assertEquals(birthday, persisted.getBirthday());
    }

    public void testShouldPersistEmptyDate() {
        // given
        int id = 1;

        Person person = new Person();
        person.setId(id);
        person.setBirthday(null);

        repository.createPerson(person);

        // when
        Person persisted = repository.getPerson(id);

        // then
        assertNull(persisted.getBirthday());
    }

    public void testShouldPersistFloat() {
        // given
        int id = 2;
        Float weight = 71.567f;

        Person person = new Person();
        person.setId(id);
        person.setWeight(weight);

        repository.createPerson(person);

        // when
        Person persisted = repository.getPerson(id);

        // then
        assertEquals(weight, persisted.getWeight());
    }

    public void testShouldPersistEmptyFloat() {
        // given
        int id = 3;

        Person person = new Person();
        person.setId(id);
        person.setWeight(null);

        repository.createPerson(person);

        // when
        Person persisted = repository.getPerson(id);

        // then
        assertNull(persisted.getWeight());
    }

    public void testShouldPersistByteArray() {
        // given
        int id = 3;
        String dataString = "hello";
        byte[] dataBytes = dataString.getBytes();

        Person person = new Person();
        person.setId(id);
        person.setImageAsBytes(dataBytes);

        repository.createPerson(person);

        // when
        Person persisted = repository.getPerson(id);

        // then
        assertEquals(dataString, new String(persisted.getImageAsBytes()));
    }

    public void testShouldPersistEmptyByteArray() {
        // given
        int id = 3;

        Person person = new Person();
        person.setId(id);
        person.setImageAsBytes(null);

        repository.createPerson(person);

        // when
        Person persisted = repository.getPerson(id);

        // then
        assertNull(persisted.getImageAsBytes());
    }

}
