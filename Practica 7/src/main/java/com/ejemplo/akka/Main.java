package com.ejemplo.akka;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // Crear el sistema de actores 
        ActorSystem sistema = ActorSystem.create("miSistema");

        // Crear el actor printerActor 
        ActorRef printerActor = sistema.actorOf(Props.create(PrinterActor.class),"printerActor");

        // Crear hundredActor
        ActorRef hundredActor = sistema.actorOf(Props.create(HundredActor.class, printerActor), "hundredActor");

        // Crear evenActor
        ActorRef evenActor = sistema.actorOf(Props.create(EvenActor.class, printerActor, hundredActor), "evenActor");

        int[] ints = {10, 2, 3, 100, 245, 102, 234};
        // enviar cada cifra de la secuencia al actor evenActor
        for (int i : ints) {
            evenActor.tell(i, ActorRef.noSender());
        }

        // esperar 5 segundos
        Thread.sleep(5000);

        // enviar el mensaje "stop" a cada actor
        printerActor.tell("stop", ActorRef.noSender());
        hundredActor.tell("stop", ActorRef.noSender());
        evenActor.tell("stop", ActorRef.noSender());

        //  terminar el sistema
        sistema.terminate();

        System.out.println("Actor system terminated");
    }
}
