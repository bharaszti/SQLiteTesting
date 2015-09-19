package com.example.bence.sqlitetesting.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * Created by bence on 31.08.15.
 */
public class Person {
    private Integer id;
    private String name;
    private Date timestamp;
    private Date birthday;
    private Float weight;
    private byte[] imageAsBytes;

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
        timestamp = null;
        this.birthday = null;
    }

    public Person() {
        this.id = 0;
        this.name = null;
        timestamp = null;
        this.birthday = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public byte[] getImageAsBytes() {
        return imageAsBytes;
    }

    public void setImageAsBytes(byte[] imageAsBytes) {
        this.imageAsBytes = imageAsBytes;
    }

    @Override
    public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this);
        builder.setDefaultStyle(ToStringStyle.SHORT_PREFIX_STYLE);
        return builder.
                append("id", id).
                append("name", name).
                append("birthday", birthday).
                append("weight", weight).
                append("timestamp", timestamp).
                append("imageAsBytes", imageAsBytes).
                toString();
    }

}
