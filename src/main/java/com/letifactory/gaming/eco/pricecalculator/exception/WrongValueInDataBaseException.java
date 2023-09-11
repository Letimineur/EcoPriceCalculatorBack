package com.letifactory.gaming.eco.pricecalculator.exception;

public class WrongValueInDataBaseException extends FailedDatabaseInitException{

    public WrongValueInDataBaseException(final Throwable cause) {
        super(cause);
    }

    public WrongValueInDataBaseException(final String msg, final Throwable cause) {
        super(msg, cause);
    }

    public WrongValueInDataBaseException(final String msg) {
        super(msg);
    }
}
