package org.akka.actor;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;

public class MathActor extends AbstractActor {
//    public static void main(String[] args) {
//
//    }

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(Integer.class, msg->System.out.println(msg*msg))
                .build();
    }
    public static Props props(){
        return Props.create(MathActor.class);
    }
}
