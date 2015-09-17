package com.example.bence.sqlitetesting.util;

/**
 * Created by bence on 17.09.15.
 */
public class ApplicationException extends RuntimeException {
    public ApplicationException(String detailMessage) {
        super(detailMessage);
    }
}
