package p9;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ejemploScheduledFuture {
    public static void main(String[] args) {
        // Create a ScheduledExecutorService object and 2 threads
        ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(2);

        // Create a Callable<String> object task that prints the time and returns the following text "end of task <<name>>"
        Callable<String> taskOnTime = new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println(Thread.currentThread().getName() + ": Son las " + LocalDateTime.now());
                return "Fin de taskOnTime.";
            }
        };

        // // Create a Callable<String> object task that prints the time and returns the following text "end of delayed task <<name>>"
        Callable<String> taskDelayed = new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println(Thread.currentThread().getName() + ": Son las " + LocalDateTime.now());
                return "Fin de taskDelayed.";
            }
        };

        // Create a list of ScheduledFuture<String>
        List<ScheduledFuture<String>> futureList = new ArrayList<>();

        // Schedule both tasks with the executor
        futureList.add(executor.schedule(taskOnTime,0, TimeUnit.SECONDS));
        futureList.add(executor.schedule(taskDelayed, 2, TimeUnit.SECONDS));

        // Try to get the futures
        for (ScheduledFuture<String> future : futureList) {
            try {
                System.out.println(future.get());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        // Shutdown the executor system
        executor.close();
    }
}
