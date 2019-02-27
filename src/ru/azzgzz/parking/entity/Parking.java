package ru.azzgzz.parking.entity;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Parking {

    private final int capacity;
    private AtomicInteger carCountNow;
    private List<Ticket> ticketList;
    private AtomicInteger totalCarCount;

    public Parking(int capacity) {
        this.capacity = capacity;
        carCountNow = new AtomicInteger(0);
        totalCarCount = new AtomicInteger(0);
        ticketList = new ArrayList<>(capacity);
        for (int i = 0; i < capacity; i++) {
            ticketList.add(new Ticket (i));
        }
    }

    public Ticket askTicket(Car car){
        while (carCountNow.intValue() >= capacity) {
            Thread.yield();
        }
        Ticket t;
        synchronized (ticketList) {
            t = (Ticket) ticketList.stream()
                    .filter(i -> i.getCarName() == null)
                    .limit(1);
            totalCarCount.incrementAndGet();
            t.setCarCount(totalCarCount.intValue());
            t.setCarName(car.getName());
        }
        return t;
    }
//
//    Ticket returnTicket(Ticket ticket);




    public int getCarCountNow() {
        return (int) ticketList.stream()
                .filter(Ticket::isFilled)
                .count();
    }

    public List<Ticket> getTicketList() {
        return ticketList.stream()
                .filter(Ticket::isFilled)
                .collect(Collectors.toList());
    }


}
