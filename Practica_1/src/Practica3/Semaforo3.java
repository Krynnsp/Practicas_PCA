package Practica3;

public class Semaforo3 {
    //COMPLETAR EL CÓDIGO
    //CONFIGURAR LOS SEMÁFOROS
    //private final Semaphore semaforo1 = new Semaphore(XX); // Bloquea el hilo 1
    //private final Semaphore semaforo2 = new Semaphore(XX); // Bloquea el hilo 2
    //private final Semaphore semaforo3 = new Semaphore(XX); // Bloquea el hilo 3

    private final Runnable tarea = () -> {
        String nombreHilo = Thread.currentThread().getName();

        try {
            if (nombreHilo.equals("Hilo 1")) {
                //COMPLETAR EL CÓDIGO
                //comprobar bloqueo
            } else if (nombreHilo.equals("Hilo 2")) {
                //COMPLETAR EL CÓDIGO
                //comprobar bloqueo
            } else if (nombreHilo.equals("Hilo 3")) {
                //COMPLETAR EL CÓDIGO
                //comprobar bloqueo
            }

            System.out.println(nombreHilo + " iniciando...");
            Thread.sleep(1000); // Simula trabajo
            System.out.println(nombreHilo + " terminado.");

            if (nombreHilo.equals("Hilo 1")) {
                //COMPLETAR EL CÓDIGO
                //desbloquear
            } else if (nombreHilo.equals("Hilo 2")) {
                //COMPLETAR EL CÓDIGO
                //desbloquear
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    };

    public void ejecutarHilos() {
        for (int i = 0; i < 10; i++){
            Thread hilo1 = new Thread(tarea, "Hilo 1");
            Thread hilo2 = new Thread(tarea, "Hilo 2");
            Thread hilo3 = new Thread(tarea, "Hilo 3");
    
            hilo1.start();
            hilo2.start();
            hilo3.start();
        }

    }
}


