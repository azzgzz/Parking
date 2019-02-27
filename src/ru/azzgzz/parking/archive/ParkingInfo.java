package ru.azzgzz.parking.archive;

public class ParkingInfo {
    private String carName;
    private int ticketId;
    private int carCount;
    private boolean isArrived;

    public ParkingInfo(String carName, int ticketId, int carCount) {
        this.carName = carName;
        this.ticketId = ticketId;
        this.carCount = carCount;
    }

    public ParkingInfo(String carName, int ticketId, int carCount, boolean isArrived) {
        this.carName = carName;
        this.ticketId = ticketId;
        this.carCount = carCount;
        this.isArrived = isArrived;
    }

    public String getCarName() {
        return carName;
    }

    public int getTicketId() {
        return ticketId;
    }

    public int getCarCount() {
        return carCount;
    }

    public boolean isArrived() {
        return isArrived;
    }

    public void setArrived(boolean arrived) {
        isArrived = arrived;
    }
}
