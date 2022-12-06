package org.akka.Ask;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import static akka.pattern.Patterns.ask;
import static akka.pattern.Patterns.pipe;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;
//import scala.concurrent.duration.Duration;

import java.time.Duration;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class TrailAsk {
    static final Duration t = Duration.ofSeconds(5);

    public static void main(String[] args) throws InterruptedException, TimeoutException, ExecutionException {
        ActorSystem actorSystem = ActorSystem.create("TrailSystem");
        ActorRef actorRef = actorSystem.actorOf(Props.create(AskActor.class), "askActor");
        ActorRef actorC = actorSystem.actorOf(Props.create(AskActor.class), "askActorC");

//        Timeout timeout = new Timeout(Duration.create(3, "seconds"));
//        Queue<Future<Object>> futures = new LinkedList<>();
//        futures.add(Patterns.ask(actorRef, "question", timeout));
//        futures.add(Patterns.ask(actorRef, "questions", timeout));
//        futures.add(Patterns.ask(actorRef, "questionss", timeout));
//        futures.add(Patterns.ask(actorRef, "question", timeout));
//        futures.add(Patterns.ask(actorRef, "question", timeout));
//        futures.add(Patterns.ask(actorRef, "question", timeout));
//        futures.add(Patterns.ask(actorRef, "questions", timeout));
//        futures.add(Patterns.ask(actorRef, "questionss", timeout));
//
//        System.out.println("asking all...");
//
//        for(int i = 0; i < 8; i++){
//            try {
//                System.out.println(Await.result(futures.remove(), timeout.duration()));
//            } catch (TimeoutException t) {
//                t.printStackTrace();
//            }
//        }
//        System.out.println(futures.size());


        // using 1000ms timeout
        CompletableFuture<Object> future1 =
                ask(actorRef, "question", Duration.ofMillis(2000)).toCompletableFuture();

        // using timeout from above
        CompletableFuture<Object> future2 = ask(actorRef, "questions", t).toCompletableFuture();

        CompletableFuture<Object> transformed =
                future1.thenCombine(future2, (x, s) -> ((String) x + (String) s));

        System.out.println(transformed.get());
        System.out.println(future2.get());
        System.out.println(future1.get());

        pipe(transformed, actorSystem.dispatcher());

        actorSystem.stop(actorRef);
        actorSystem.terminate();
    }
}
