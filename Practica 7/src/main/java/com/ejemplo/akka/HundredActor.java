package com.ejemplo.akka;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

public class HundredActor extends AbstractActor {
    private final ActorRef printer;
    //TODO:
    // crear el constructor
    public HundredActor(ActorRef printer) {
        super();
        this.printer = printer;
    }

    //invocar una instancia de createReceive 
    // programar el contenido de la clase como se ha descrito en las transparencias
    @Override
    public Receive createReceive() {
        return receiveBuilder()
            .match(String.class, msg -> msg.equals("stop"), msg ->
                    getContext().stop(getSelf()))
            .match(Integer.class, msg -> msg < 100, msg -> System.out.println("Hundred recibió número par menor a 100: " + msg))
            .match(Integer.class, msg -> msg >= 100, msg -> {
                System.out.println(msg + " es par e igual o mayor a 100.");
                printer.tell(msg, getSelf());
            })
            .matchAny(msg -> System.out.println("Hundred recibió mensaje no reconocido: " + msg))
        .build();
    }
}
