package P3;

import java.util.concurrent.Semaphore;

public class Semaforo1 {
    private final Semaphore semaforo = new Semaphore(0);

    public void ejecutarHilos() {
        Thread hilo1 = new Thread(() -> {
            System.out.println("Hilo 1: Iniciando trabajo...");
            try {
                Thread.sleep(2000); // Simula una tarea de 2 segundos
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Hilo 1: Trabajo terminado.");
            semaforo.release(); // Libera el semáforo para que el Hilo 2 pueda ejecutarse
        });

        Thread hilo2 = new Thread(() -> {
            try {
                semaforo.acquire(); // Espera a tener acceso al recurso
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Hilo 2: Ahora puedo ejecutarme.");
        });

        hilo2.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        hilo1.start();
    }
}

