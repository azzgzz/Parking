package ru.azzgzz.parking;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class ParkingManager {

    private static Parking parking;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("Welcome to the Parking. For help type \"help\"");
        System.out.print("Enter parking capacity\n->");
        int cap = in.nextInt();
        System.out.print("Enter time that Cars need to drive in (1-5 seconds)\n->");
        int timeDriveIn = in.nextInt();

        parking = new Parking(cap, timeDriveIn * 1000);

        System.out.print("Enter command (for help use \"help\")\n->");
        String command = in.next();
        while (!command.equals("e")) {
            commandSwitcher(command);
            System.out.print("______\nEnter command (for help use \"help\")\n->");
            command = in.next();
        }

        System.out.println("______\nGood bye!");
        in.close();

    }


    private static void commandSwitcher (String s) {
        if (s.equals("l") || s.equals("list")) {
            parking.getArrivedCarsSet().forEach(System.out::println);
        }

        if (s.equals("c")) {
            System.out.println("Car parked now count: " + parking.getCarCountNow());
        }

        if (s.matches("p:[\\d]+")) {
            int incomeNumberOfCars = Integer.parseInt(s.substring(2));
            Thread[] t = new Thread[incomeNumberOfCars];

            for (int i = 0; i < incomeNumberOfCars; i++) {
                t[i] = new Thread(new DriveIn());
            }
            Arrays.stream(t).forEach(Thread::start);
        }

        if (s.matches("u:\\d+")) {
            int ticketNumber = Integer.parseInt(s.substring(2));
            if (checkUnparkRequest(new int[] {ticketNumber})) {
                Thread t = new Thread(new Departure(ticketNumber));
                t.start();
            }
        }

        if (s.matches("u:\\[(\\d+,?\\s*)+]")){
            s = s.substring(3,s.length()-1);
            String [] nums = s.split(",");
            int[] unparkId = IntStream.range(0,nums.length)
                    .map(i -> Integer.parseInt(nums[i].trim()))
                    .toArray();
            if (checkUnparkRequest(unparkId)) {
                Thread[] unparkThread = new Thread[unparkId.length];
                for (int i = 0; i < unparkId.length; i++) {
                    unparkThread[i] = new Thread (new Departure(unparkId[i]));
                }

                Arrays.stream(unparkThread).forEach(Thread::start);
            }
        }


    }


    static class DriveIn implements Runnable{

        @Override
        public void run() {
                Car car = new Car("car #" + (Car.getTotalCarCreated()));
                Ticket ticket = parking.askTicket(car);
                if (ticket != null) {
                    try {
                        Thread.sleep(parking.getTimeDriveIn());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    parking.arrivedNotify(ticket);
                }
        }
    }

    static class Departure implements Runnable{
        private int ticketId;

        Departure (int ticketId) {
            this.ticketId=ticketId;
        }
        @Override
        public void run() {
            parking.returnTicket(ticketId);
        }
    }

    private static boolean checkUnparkRequest(int[] a) {

        int faultCount = 0;
        faultCount = (int)Arrays.stream(a)
                .filter(i -> i<1 || i > parking.getCapacity())
                .count();

        Arrays.stream(a)
                .filter(i -> i<1 || i > parking.getCapacity())
                .forEach(i -> System.out.println("#fault: Value " + i + " does not correct"));
        return faultCount == 0;
    }
}
