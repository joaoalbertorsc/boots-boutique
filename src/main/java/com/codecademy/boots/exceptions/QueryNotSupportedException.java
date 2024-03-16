package com.codecademy.boots.exceptions;

public class QueryNotSupportedException extends Exception {
    public QueryNotSupportedException(String message) {
        super(message);
    }
}