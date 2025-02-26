package Practica3;

import java.util.concurrent.Semaphore;

public class Semaforo2 {
    private final Semaphore semaforo = new Semaphore(1);

    public void ejecutarHilos() {
        Runnable tarea = () -> {
            String nombreHilo = Thread.currentThread().getName();
            System.out.println(nombreHilo + " intentando entrar en la zona compartida...");

            try {
                semaforo.acquire(); //  Bloquea el semáforo (mutex)
                System.out.println(nombreHilo + " ha entrado en la zona compartida.");
                Thread.sleep(2000); // Simula trabajo
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                System.out.println(nombreHilo + " ha salido de la zona compartida.");
                semaforo.release(); //  Libera el semáforo
            }
        };


        Thread hilo1 = new Thread(tarea, "Hilo 1");
        Thread hilo2 = new Thread(tarea, "Hilo 2");
        //COMPLETAR EL CÓDIGO
        //lanzar los hilos
    }
}

