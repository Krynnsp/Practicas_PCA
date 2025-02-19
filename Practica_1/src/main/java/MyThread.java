import java.util.List;
import java.util.Collections;

public class MyThread extends Thread {
    private final List<Integer> bucket;

    public MyThread(List<Integer> bucket) {
        this.bucket = bucket;
    }

    @Override
    public void run() {
        Collections.sort(bucket);
    }
}
