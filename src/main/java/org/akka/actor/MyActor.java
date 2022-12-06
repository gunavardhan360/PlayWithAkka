package org.akka.actor;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class MyActor extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(
                        String.class,
                        s -> {
                            System.out.println("Received String message: "+ s);
                        })
                .matchAny(o -> System.out.println("received unknown message"))
                .build();
    }
}