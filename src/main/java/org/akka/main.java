package org.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.akka.actor.MathActor;

public class main {
    public static void main(String[] args) {
        if (args.length == 0) {
            startupClusterNodes(Arrays.asList("2552"));
        } else {
            startupClusterNodes(Arrays.asList(args));
        }
    }

    private static void startupClusterNodes(List<String> ports) {
        System.out.printf("Start cluster on port(s) %s%n", ports);

        ports.forEach(port -> {
            ActorSystem actorSystem = ActorSystem.create("cluster");

//            AkkaManagement.get(actorSystem).start();

            ActorRef mathactor = actorSystem.actorOf(MathActor.props(), "MathActor");

            mathactor.tell(3, ActorRef.noSender());

            Scanner sc = new Scanner(System.in);
            int i;
            while(true) {
                System.out.println("Choose Number");
                i = sc.nextInt();
                mathactor.tell(i, ActorRef.noSender());
            }
        });
    }
}