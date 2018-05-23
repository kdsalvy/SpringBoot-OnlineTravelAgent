package com.putnam.online.travel.agent.application.booking.exception;

public class NoRollbackException extends Throwable {

    private static final long serialVersionUID = -3374002460138271585L;

    public NoRollbackException(String exceptionMessage) {
	super(exceptionMessage);
    }

}
