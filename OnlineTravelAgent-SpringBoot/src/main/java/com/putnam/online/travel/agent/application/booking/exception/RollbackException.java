package com.putnam.online.travel.agent.application.booking.exception;

public class RollbackException extends Throwable {

    private static final long serialVersionUID = 4094045717609123924L;

    public RollbackException(String message) {
	super(message);
    }
}
