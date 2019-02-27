package ru.azzgzz.multithread;

import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentDemo {

    public static AtomicInteger atomicInteger = new AtomicInteger(0);
    public static volatile int volataliInt = 0;

    static long currentTime;

    public static void main(String[] args) throws InterruptedException {

        System.out.println("Starting threads...");
        currentTime = System.currentTimeMillis();

        Thread t1 = new Thread(new Thread(()-> {
            for (int i = 0; i < 20000000; i++) {
                ConcurrentDemo.volataliInt++;
                ConcurrentDemo.atomicInteger.incrementAndGet();
            }
        }));

        t1.start();

        Thread t2 = new Thread(new Thread(()-> {
            for (int i = 0; i < 20000000; i++) {
                ConcurrentDemo.volataliInt++;
                ConcurrentDemo.atomicInteger.incrementAndGet();
            }
        }));

        t2.start();

        t1.join();
        t2.join();

        System.out.println("Finished int " +
                (System.currentTimeMillis() - currentTime) + "ms");
        System.out.println("Atomic: " + atomicInteger.get() + "; Volatile: " + volataliInt);

    }
}
