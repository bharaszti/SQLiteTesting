package com.example.bence.sqlitetesting.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * Created by bence on 31.08.15.
 */
public class Person {
    private final int id;
    private final String name;
    private final Date timestamp;
    private final Date birthday;

    public Person(int id, String name, Date timestamp, Date birthday) {
        this.id = id;
        this.name = name;
        this.timestamp = timestamp;
        this.birthday = birthday;
    }

    public Person(int id, String name, Date timestamp) {
        this.id = id;
        this.name = name;
        this.timestamp = timestamp;
        this.birthday = null;
    }

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
        timestamp = null;
        this.birthday = null;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public Date getBirthday() {
        return birthday;
    }
}
