package ru.azzgzz.parking.entity;

import java.util.concurrent.atomic.AtomicInteger;

public class Car {

    private String name;
    private static AtomicInteger totalCarCreated = new AtomicInteger(0);
    public Car (String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static int getTotalCarCreated() {
        return totalCarCreated.incrementAndGet();
    }
}
