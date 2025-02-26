package main.java;
import practicas_pca.TesterRun;

import java.util.Collections;
import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.List;

public class AlgoritmoBucketSortExecutor implements TesterRun {
    @Override
    public ArrayList<Integer> bucketSort(List<Integer> numbers, int numThreads) {
        int minValue = Collections.min(numbers);
        int maxValue = Collections.max(numbers);
        long range = (maxValue - minValue) + 1;
        int bucketRange = (int) Math.ceil((double) range / numThreads);

        ArrayList<List<Integer>> bucketList = new ArrayList<>(numThreads);


        for (int i = 0; i < numThreads; i++) {
            bucketList.add(new ArrayList<>());
        }

        for (int number : numbers) {
            int bucketIndex = (number - minValue) / bucketRange;
            if (bucketIndex >= numThreads) {
                bucketIndex = numThreads - 1;
            } else if (bucketIndex < 0) {
                bucketIndex = 0;
            }
            bucketList.get(bucketIndex).add(number);
        }

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        ArrayList<Future<ArrayList<Integer>>> futureList=new ArrayList<>(numThreads);

        for(List<Integer> bucket: bucketList){
            MyCallable sorting = new MyCallable(bucket);
            Future<ArrayList<Integer>> thisFuture=executor.submit(sorting);
            futureList.add(thisFuture);
        }

        ArrayList<Integer> orderedList = new ArrayList<>(numbers.size());

        for(Future<ArrayList<Integer>> f: futureList){
            try{
                ArrayList<Integer> bucketRes = f.get();
                if(f.isDone()){
                    orderedList.addAll(bucketRes);
                }
                else{
                    while(!f.isDone()){
                        wait();
                    }
                }
                bucketRes.clear();
            }catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

        executor.shutdown();
        bucketList.clear();


        return orderedList;
    }
}
