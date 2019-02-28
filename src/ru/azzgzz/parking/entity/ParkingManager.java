package ru.azzgzz.parking.entity;

import java.util.Arrays;
import java.util.Scanner;

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
        if (s.matches("p:[\\d]+")){
            int incomeNumberOfCars = Integer.parseInt(s.substring(2));
            Thread[] t = new Thread[incomeNumberOfCars];

            for (int i = 0; i < incomeNumberOfCars; i++) {
                t[i] = new Thread(()-> {
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
                });
            }
            Arrays.stream(t).forEach(Thread::start);
        }
    }
}
