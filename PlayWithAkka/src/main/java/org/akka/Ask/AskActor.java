package org.akka.Ask;

import akka.actor.AbstractActor;

public class AskActor extends AbstractActor {
    @Override
    public Receive createReceive(){
        return receiveBuilder()
                .matchEquals("question", p -> sendReply())
                .matchAny(p -> getSender().tell("No Answer", self()))
                .build();
    }

    private void sendReply() throws InterruptedException {
//        wait(1000);
        getSender().tell("answer", self());
    }
}
