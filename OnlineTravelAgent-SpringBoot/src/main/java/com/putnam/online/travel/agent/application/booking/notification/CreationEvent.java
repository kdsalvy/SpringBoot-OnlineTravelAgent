package com.putnam.online.travel.agent.application.booking.notification;

public interface CreationEvent<T> {
    public T getEntity();
}
