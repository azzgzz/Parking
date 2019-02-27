package ru.azzgzz.multithread;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Main {

    private List namesListl = new ArrayList();
    private int nameCount = 0;

    public void addName (String name) {
        synchronized (this) {
            nameCount++;
        }
        namesListl.add(name);
    }

    public static void main(String[] args) {
        Thread [] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(()-> System.out.println("From thread " + Thread.currentThread().getName()));
        }

        System.out.println("Start program\n---------");
        Stream.of(threads).forEach(Thread::start);
        System.out.println("Program finished;");
    }
}
