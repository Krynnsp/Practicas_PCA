package com.ejemplo.akka;

import akka.actor.AbstractActor;

public class PrinterActor extends AbstractActor {
    //TODO:
    // crear el constructor

    public PrinterActor() {
        super();
    }

    //invocar una instancia de createReceive
    // programar el contenido de la clase como se ha descrito en las transparencias
    @Override
    public Receive createReceive() {
        return receiveBuilder()
            .match(String.class, msg -> msg.equals("stop"), msg ->
                    getContext().stop(getSelf()))
            .match(Integer.class, msg -> System.out.println("Printer recibido número: " + msg))
            .matchAny(msg -> System.out.println("Printer recibió mensaje no reconocido: " + msg))
        .build();
    }
}
