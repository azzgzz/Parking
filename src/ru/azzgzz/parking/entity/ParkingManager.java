package ru.azzgzz.parking.entity;

import java.util.List;
import java.util.Scanner;

public class ParkingManager {

    private static Parking parking;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("Welcome to the Parking. For help type \"help\"");
        System.out.print("Enter parking capacity\n->");
        int cap = in.nextInt();

        parking = new Parking(cap);

        System.out.print("Enter command (for help use \"help\")\n->");
        String command = in.next();
        while (!command.equals("e")) {

            System.out.print("______\nEnter command (for help use \"help\")\n->");
            command = in.next();
        }

        System.out.println("______\nGood bye!");
        in.close();
    }


    private void commandSwitcher (String s) {
        if (s.equals("l") || s.equals("list")) {
            parking.getTicketList().forEach(i -> {
                System.out.println("Car: " + i.getCarName() + "\tTicketId: " + i.getTicketId() + "\tCar line number: " + i.getCarCount());
            });
        }
        if (s.equals("c")) {
            System.out.println("Car parked now: " + parking.getCarCountNow());
        }
        if (s.matches("p:[d]+")){
            int incomeNumberOfCars = Integer.parseInt(s.substring(2));

        }
    }
}
