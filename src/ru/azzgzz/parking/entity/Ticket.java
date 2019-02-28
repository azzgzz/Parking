package ru.azzgzz.parking.entity;

public class Ticket {
    private final int ticketId;
    private int carLineNumber;
    private String carName;
    private boolean isFree;
    private boolean isArrived;

    public Ticket(int ticketId) {
        this.ticketId = ticketId;
        isFree = true;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setCarLineNumber(int carLineNumber) {
        this.carLineNumber = carLineNumber;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public boolean isArrived() {
        return isArrived;
    }

    public void setArrived(boolean arrived) {
        isArrived = arrived;
    }

    @Override
    public int hashCode() {
        return ticketId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Ticket)
            return ticketId == ((Ticket) obj).ticketId;
        return false;
    }

    @Override
    public String toString() {
        return "Car: " + carName + "\t\tTicketId: " + ticketId + "\t\tCar line number: " + carLineNumber;
    }

    /**
     * @return true if change happened
     */
    public boolean setBusyIfFree() {
        synchronized (this) {
            if (isFree) {
                isFree = false;
                return true;
            }
        }
        return false;
    }
}
