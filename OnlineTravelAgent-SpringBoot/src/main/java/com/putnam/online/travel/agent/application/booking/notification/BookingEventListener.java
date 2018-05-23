package com.putnam.online.travel.agent.application.booking.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class BookingEventListener {

    Logger LOG = LoggerFactory.getLogger(BookingEventListener.class);

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    public void processBookingCreatedEvent(BookingCreatedEvent creationEvent) {
	LOG.info("BOOKING SUCCESSFULL: SENDING EMAIL TO THE CUSTOMER");
    }
}
