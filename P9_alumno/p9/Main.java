package p9;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static boolean isPalindrome(int number) {
        String str = Integer.toString(number);
        int str_len = str.length();

        for (int i = 0; i < str_len / 2; i++) {
            if (str.charAt(i) != str.charAt(str_len - 1 - i)) {
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args) {
        // Create your own Callable class that implements Callable
                    // Inside the Callable class: add a private List<Integer> attribute
                    // Inside the Callable class: add the constructor of the class
                    // Inside the Callable class: override the call method
        class MyCallable implements Callable<List<Integer>> {
            private List<Integer> integerList;
            private List<Integer> palindromeList;

            public MyCallable(List<Integer> integerList) {
                this.integerList = integerList;
                palindromeList = new ArrayList<>();
            }

            @Override
            public List<Integer> call() throws Exception {
                for (Integer integer : integerList) {
                    if (isPalindrome(integer)) {
                        palindromeList.add(integer);
                    }
                }
                return palindromeList;
            }
        }

        final int MAX_RANGE = 1000000;
        final int NUM_THREADS = 10;
        long time_ini = System.currentTimeMillis();
        long time_end;
        List<Integer> list = IntStream.rangeClosed(1, MAX_RANGE).boxed().collect(Collectors.toList());
        // Create an executor with NUM_THREADS threads
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);

        // Execute each thread with a sublist of integers inside a loop and get the futures
        List<Future<List<Integer>>> futureList = new ArrayList<>();
        for (int listIndex = 0; listIndex < NUM_THREADS; listIndex++) {
            List<Integer> integerSublist = list.subList((listIndex * 100000), (listIndex + 1) * 100000);
            futureList.add(executorService.submit(new MyCallable(integerSublist)));
        }

        // Get the results from the futures and print them
        int palindromeCount = 0;
        for (Future<List<Integer>> palindromeList : futureList) {
            try {
                System.out.println(palindromeList.get());
                palindromeCount += palindromeList.get().size();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        // Shutdown the executor
        executorService.close();

        time_end = System.currentTimeMillis();
        System.out.println("El tiempo que ha tardado es: "+(time_end-time_ini)+" milisegundos.");
        System.out.println("Hay " + palindromeCount + " palíndromos.");
    }
}
