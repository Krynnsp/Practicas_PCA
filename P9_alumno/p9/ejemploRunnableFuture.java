package p9;

import java.util.concurrent.*;
public class ejemploRunnableFuture {
    public static void main(String[] args) {
        Runnable tarea = () ->{
            try{
                System.out.println(Thread.currentThread().getName()+": ejecutando tarea runnable...");
                Thread.sleep(3000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }finally {
                System.out.println(Thread.currentThread().getName()+": ...fin de la tarea runnable");
            }
        };
        // FIRST PART: RunnableFuture with Thread
        // Create a FutureTask with the previous Runnable task
        FutureTask<String> futureTaskOne = new FutureTask<String>(tarea, "Fin de tarea 1.");

        // Create a thread with the previous FutureTask
        Thread thread = new Thread(futureTaskOne);


        System.out.println(Thread.currentThread().getName()+": lanzando tarea 1 en hilo");
        // Execute the thread
        thread.start();

        try {
            System.out.println(Thread.currentThread().getName() + ": " + futureTaskOne.get());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        // SECOND PART: RunnableFuture with the main thread
        // Create a second FutureTask with the Runnable task
        FutureTask<String> futureTaskTwo = new FutureTask<String>(tarea, "Fin de tarea 2.");

        System.out.println(Thread.currentThread().getName()+": ejecutando tarea 2");
        // Execute the futureTask
        futureTaskTwo.run();

        // print the thread name and the result of the future
        try {
            System.out.println(Thread.currentThread().getName() + ": " + futureTaskTwo.get());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }


        // THIRD PART: RunnableFuture with executor
        // Create an executor with the method Executors.newSingleThreadExecutor()
        ExecutorService executor = Executors.newSingleThreadExecutor();
        // Executes a third futuretask
        FutureTask<String> futureTaskThree = new FutureTask<String>(tarea, "Fin de tarea 3.");
        executor.submit(futureTaskThree);
        // Get the result of the future
        try {
            System.out.println(Thread.currentThread().getName() + ": " + futureTaskThree.get());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        // Don't forget shutdown the executor
        executor.close();
    }
}
