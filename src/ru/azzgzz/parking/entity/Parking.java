package ru.azzgzz.parking.entity;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Parking {

    private final int capacity;
    private AtomicInteger busyTicketsCount;
    private ConcurrentHashMap.KeySetView<Ticket, Boolean> allTickets;
    private AtomicInteger totalCarCount;
    private final int timeDriveIn;

    public Parking(int capacity, int timeDriveIn) {
        this.capacity = capacity;
        this.timeDriveIn = timeDriveIn;
        busyTicketsCount = new AtomicInteger(0);
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

        if (busyTicketsCount.intValue() < capacity) {

            for (Ticket allTicket : allTickets) {
                t = allTicket;
                if (t.setBusyIfFree()) {
                    busyTicketsCount.incrementAndGet();
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
        if (ticket.setFreeIfBusy())
            busyTicketsCount.decrementAndGet();
    }

    public void arrivedNotify(Ticket ticket) {
        ticket.setArrived(true);
    }

    public int getBusyTicketsCount() {
        return (int) allTickets.stream()
                .filter(Ticket::isArrived)
                .count();
    }

    public Set<Ticket> getArrivedCarsSet() {
        return allTickets.stream().filter(Ticket::isArrived).collect(Collectors.toSet());
    }

    public int getTimeDriveIn() {
        return timeDriveIn;
    }

    public int getCapacity() {
        return capacity;
    }
}
