import practicas_pca.TesterRun;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AlgoritmoBucketSort implements TesterRun{

    @Override
    public ArrayList<Integer> bucketSort(List<Integer> numbers, int numThreads) {
        int minValue = Collections.min(numbers);
        int maxValue = Collections.max(numbers);
        long range = (maxValue - minValue) + 1;
        int bucketRange = (int) Math.ceil((double) range / numThreads);

        ArrayList<List<Integer>> bucketList = new ArrayList<>(numThreads);
        ArrayList<Thread> threadList = new ArrayList<>(numThreads);

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

        for (int i = 0; i < numThreads; i++) {
            List<Integer> bucket = bucketList.get(i);
            MyThread thread = new MyThread(bucket);
            threadList.add(thread);
            thread.start();
        }

        ArrayList<Integer> orderedList = new ArrayList<>(numbers.size());

        for (Thread thread : threadList) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        for (List<Integer> bucket : bucketList) {
            orderedList.addAll(bucket);
        }

        bucketList.clear();

        return orderedList;
    }
}
