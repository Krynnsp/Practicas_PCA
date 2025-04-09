package com.ejemplo.akka;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

public class EvenActor extends AbstractActor {
    private final ActorRef printer;
    private final ActorRef hundred;
    //TODO:
    // crear el constructor

    public EvenActor(ActorRef printer, ActorRef hundred) {
        super();
        this.printer = printer;
        this.hundred = hundred;
    }

    //invocar una instancia de createReceive 
    // programar el contenido de la clase como se ha descrito en las transparencias
    @Override
    public Receive createReceive() {
        return receiveBuilder()
            .match(String.class, msg -> msg.equals("stop"), msg ->
                    getContext().stop(getSelf()))
            .match(Integer.class, msg -> msg % 2 == 1, msg -> System.out.println("Even recibió número impar: " + msg))
            .match(Integer.class, msg -> msg % 2 == 0, msg -> {
                    printer.tell(msg, getSelf());
                    hundred.tell(msg, getSelf());
            })
            .matchAny(msg -> System.out.println("Even recibió mensaje no reconocido: " + msg))
        .build();
    }
}
