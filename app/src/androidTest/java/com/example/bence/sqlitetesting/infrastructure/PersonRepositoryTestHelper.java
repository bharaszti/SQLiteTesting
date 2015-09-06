package com.example.bence.sqlitetesting.infrastructure;

import com.example.bence.sqlitetesting.domain.Person;

import junit.framework.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by bence on 04.09.15.
 */
public class PersonRepositoryTestHelper {
    public void assertPersons(List<Person> persons, Person... expectedPersons) {
        Assert.assertNotNull(persons);
        Assert.assertNotNull(expectedPersons);
        Assert.assertEquals(expectedPersons.length, persons.size());

        for (int i = 0; i < persons.size(); i++) {
            Person actual = persons.get(i);
            Person expected = expectedPersons[i];
            Assert.assertEquals(expected, actual);
        }
    }
}
