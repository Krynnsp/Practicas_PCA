package main.java;

import java.util.List;
import java.util.Collections;
import java.util.concurrent.Callable;

public class MyCallable implements Callable {

    List<Integer> list;

    public MyCallable(List<Integer> list) {
        this.list = list;
    }

    @Override
    public List<Integer> call(){
        Collections.sort(list);
        return list;
    }
}