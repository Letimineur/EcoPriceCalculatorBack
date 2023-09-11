package com.letifactory.gaming.eco.pricecalculator.exception;


public class FailedDatabaseInitException extends RuntimeException {


    public FailedDatabaseInitException(final Throwable cause) {
        super(cause);
    }

    public FailedDatabaseInitException(final String msg, final Throwable cause) {
        super(msg, cause);
    }

    public FailedDatabaseInitException(final String msg) {
        super(msg);
    }
}
