package ru.azzgzz.parking;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Parking {

    private final int capacity;
    private AtomicInteger carCountNow;
    private ConcurrentHashMap.KeySetView<Ticket, Boolean> allTickets;
    private AtomicInteger totalCarCount;
    private final int timeDriveIn;

    public Parking(int capacity, int timeDriveIn) {
        this.capacity = capacity;
        this.timeDriveIn = timeDriveIn;
        carCountNow = new AtomicInteger(0);
        totalCarCount = new AtomicInteger(0);
        allTickets = ConcurrentHashMap.newKeySet(capacity);
        for (int i = 0; i < capacity; i++) {
            allTickets.add(new Ticket(i + 1));
        }
    }

    /**
     * Предполагается, что машина не будет ждать, пока осовободиться место, а уедет на другую парковку
     */
    public Ticket askTicket(Car car) {

        Ticket t;
//
        if (carCountNow.intValue() < capacity) {

            Iterator<Ticket> ticketIterator = allTickets.iterator();

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

    public void returnTicket(int ticketId) {
        Ticket ticket = (Ticket) allTickets.stream().filter(i -> i.getTicketId() == ticketId).toArray()[0];
        ticket.setArrived(false);
        ticket.setFree(true);
        carCountNow.decrementAndGet();
    }

    public void arrivedNotify(Ticket ticket) {
        ticket.setArrived(true);
    }


    public int getCarCountNow() {
        return (int) allTickets.stream()
                .filter(Ticket::isArrived)
                .count();
    }

    public Set<Ticket> getAllTickets() {
        return allTickets;
    }

    public Set<Ticket> getArrivedCarsSet() {
        return allTickets.stream().filter(Ticket::isArrived).collect(Collectors.toSet());
    }

    public AtomicInteger getTotalCarCount() {
        return totalCarCount;
    }

    public int getTimeDriveIn() {
        return timeDriveIn;
    }

    public int getCapacity() {
        return capacity;
    }
}
