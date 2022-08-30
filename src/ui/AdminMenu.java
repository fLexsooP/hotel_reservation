package ui;

import api.*;
import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    static HotelResource hotelResource = HotelResource.getInstance();
    static AdminResource adminResource = AdminResource.getInstance();
    public static void printMenu() {
        System.out.println("Admin Menu:");
        System.out.println("---------------------------------------------");
        System.out.println("1. See all Customers");
        System.out.println("2. See all Rooms");
        System.out.println("3. See all Reservations");
        System.out.println("4. Add a Room");
        System.out.println("5. Back to Main Menu");
        System.out.println("---------------------------------------------");
        System.out.println("Please select a number of menu option:");
    }

    public static void runAdmin() {
        Scanner adminScanner = new Scanner(System.in);
        boolean keepRunning = true;
        String menuSelection;
        while(keepRunning) {
            printMenu();
            menuSelection = adminScanner.next();
            switch (menuSelection) {
                case "1":
                    displayAllCustomers();
                    break;
                case "2":
                    displayAllRooms();
                    break;
                case "3":
                    displayAllReservations();
                    break;
                case "4":
                    addARoom();
                    break;
                case "5":
                    keepRunning = false;
                    break;
                default:
                    System.out.println("Please enter number 1-5:");
            }
        }
    }

    public static void displayAllCustomers() {
        System.out.println("All Customer List:");
        if (adminResource.getAllCustomers().isEmpty()) {
            System.out.println("No Customer Added");
        } else {
            for (Customer customer : adminResource.getAllCustomers()) {
                System.out.println(customer);
            }
        }
    }
    public static void displayAllRooms() {
        System.out.println("All Room List: ");
        if (adminResource.getAllRooms().isEmpty()) {
            System.out.println("No Room Added");
        } else {
            for (IRoom room : adminResource.getAllRooms()) {
                System.out.println(room);
            }
        }
    }
    public static void displayAllReservations() {
        System.out.println("All Reservations List: ");
        adminResource.displayAllReservations();
    }

    public static void addARoom() {
        Scanner roomScanner = new Scanner(System.in);
        List<IRoom> roomList = new ArrayList<>();
        boolean isAdding = true;
        while (isAdding) {
            System.out.println("Please enter room number: ");
            String roomNumber = roomScanner.nextLine();

            double price = 0.00;
            boolean validPrice = false;
            while (!validPrice) {
                try {
                    System.out.println("Please enter price: ");
                    price = Double.parseDouble(roomScanner.nextLine());
                    if (price < 0) {
                        System.out.println("The price must be greater or equal than 0");
                    } else {
                        validPrice = true;
                    }
                } catch (Exception ex) {
                    System.out.println("Please enter a valid price");
                }
            }

            String roomTypeString = null;
            boolean validRoomType = false;
            RoomType roomType = null;
            while (!validRoomType) {
                try {
                    System.out.println("Please enter room type(single/double): ");
                    roomTypeString = roomScanner.nextLine().toUpperCase();
                    roomType = RoomType.valueOf(roomTypeString);
                    validRoomType = true;
                } catch (IllegalArgumentException ex) {
                    System.out.println("Illegal room type, please enter single/double");
                }
            }
            roomList.add(new Room(roomNumber, price, roomType));

            System.out.println("Do you want to add another? (y/n): ");
            String anotherAdding = roomScanner.nextLine();
            if (!anotherAdding.equalsIgnoreCase("y"))
                isAdding = false;
        }
        adminResource.addRoom(roomList);
    }
}
