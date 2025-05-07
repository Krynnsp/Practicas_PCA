package p9;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class mainP10 {
    // Function that returns true if an integer is a palindrome
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
    // Function that returns true if an integer is a prime number
    public static boolean isPrime(int number) {
        if(number <= 1) return false;
        else{
            for(int a = 2; a <= Math.sqrt(number); a++){
                if((number % a) == 0) return false;
            }
        }
        return true;
    }
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        final int MAX_RANGE = 10000000;
        final int NUM_THREADS = 10;
        long time_ini = System.currentTimeMillis();
        long time_fin;
        List<Integer> lista = IntStream.rangeClosed(1, MAX_RANGE).boxed().collect(Collectors.toList());
        // Create an executor with NUM_THREADS threads
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);

        // Launch the initial asynchronous tasks to calculate the palindrome numbers,
        // and when these are completed, they will trigger others that, based on their results,
        // will calculate which of them are prime

        List<CompletableFuture<List<Integer>>> futuresList = new ArrayList<>();
        for (int index = 0; index < NUM_THREADS; index++) {
            List<Integer> integerSublist = lista.subList((index * 1000000), (index + 1) * 1000000);
            List<Integer> palindromeList = new ArrayList<>();
            List<Integer> primeList = new ArrayList<>();

            futuresList.add(CompletableFuture.supplyAsync(() -> {
                for (int integer : integerSublist) if (isPalindrome(integer)) palindromeList.add(integer);
                return palindromeList;
            }, executorService).thenApplyAsync(x -> {
                for (int integer : palindromeList) if (isPrime(integer)) primeList.add(integer);
                return primeList;
            }, executorService));
        }

        // Combine the results into a single CompletableFuture
        CompletableFuture<Void> completedFutures = CompletableFuture.allOf(futuresList.toArray(new CompletableFuture[0]));

        // Get the resulting list from the combined future
        CompletableFuture<List<Integer>> combinedFuture = completedFutures.thenApply(v ->
                futuresList.stream()
                        .map(CompletableFuture::join)
                        .flatMap(List::stream)
                        .collect(Collectors.toList())
        );

        // Once the combined futures is completed, it will trigger a final task where
        // the resulting list will be printed
        int palindromePrimeCount = combinedFuture.get().size();
        combinedFuture.thenAccept(result -> System.out.println(result));
        System.out.println("Hay un total de " + palindromePrimeCount + " palíndromos primos entre 1 y 10.000.000");

        // Shutdown the executor
        executorService.close();

        time_fin = System.currentTimeMillis();
        System.out.println("El tiempo que ha tardado es: "+(time_fin-time_ini)+" milisegundos.");
    }
}
