package ru.azzgzz.parking.entity;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Parking {

    private final int capacity;
    private AtomicInteger carCountNow;
    private ConcurrentHashMap.KeySetView<Ticket, Boolean> freeTickets;
    private ConcurrentHashMap.KeySetView<Ticket, Boolean> fillTickets;
    private AtomicInteger totalCarCount;
    private final int timeDriveIn;

    public Parking(int capacity, int timeDriveIn) {
        this.capacity = capacity;
        this.timeDriveIn = timeDriveIn;
        carCountNow = new AtomicInteger(0);
        totalCarCount = new AtomicInteger(0);
        freeTickets = ConcurrentHashMap.newKeySet(capacity);
        fillTickets = ConcurrentHashMap.newKeySet(capacity);
        for (int i = 0; i < capacity; i++) {
            freeTickets.add(new Ticket(i + 1));
        }
    }

    /**
     * Предполагается, что машина не будет ждать, пока осовободиться место, а уедет на другую парковку
     */
    public Ticket askTicket(Car car) {

        Ticket t = null;
//        if (carCountNow.intValue() < capacity) {
//            carCountNow.incrementAndGet();
//            t = freeTickets.stream()
//                    .filter(Ticket::isFree)
//                    .collect(Collectors.toSet())
//                    .iterator()
//                    .next();
//            totalCarCount.incrementAndGet();
//            t.setFree(false);
//            t.setCarLineNumber(totalCarCount.intValue());
//            t.setCarName(car.getName());
//            fillTickets.add(t);
//        }

        if (carCountNow.intValue() < capacity) {

            Iterator<Ticket> ticketIterator = freeTickets.iterator();

            while (ticketIterator.hasNext()) {
                t = ticketIterator.next();
                if (t.setBusyIfFree()) {
                    carCountNow.incrementAndGet();
                    totalCarCount.incrementAndGet();
                    t.setCarLineNumber(totalCarCount.intValue());
                    t.setCarName(car.getName());
                    return t;
                }
            }
        }

        return null;
    }

    public void returnTicket(Ticket ticket) {

    }

    public void arrivedNotify(Ticket ticket) {
        ticket.setArrived(true);
    }


    public int getCarCountNow() {
        return (int) freeTickets.stream()
                .filter(Ticket::isArrived)
                .count();
    }

    public Set<Ticket> getFreeTickets() {
        return freeTickets;
    }

    public Set<Ticket> getArrivedCarsSet() {
        return freeTickets.stream().filter(Ticket::isArrived).collect(Collectors.toSet());
    }

    public AtomicInteger getTotalCarCount() {
        return totalCarCount;
    }

    public int getTimeDriveIn() {
        return timeDriveIn;
    }
}
