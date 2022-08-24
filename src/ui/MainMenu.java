package ui;

import api.AdminResource;
import api.HotelResource;
import model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainMenu {
    public static void printMenu() {
        System.out.println("Main Menu:");
        System.out.println("---------------------------------------------");
        System.out.println("1. Find and reserve a room");;
        System.out.println("2. See my reservations");
        System.out.println("3. Create an account");
        System.out.println("4. Admin");
        System.out.println("5. Exit");
        System.out.println("---------------------------------------------");
        System.out.println("Please select a number of menu option:");
    }


    public static boolean runMain() throws ParseException {
        Scanner mainScanner = new Scanner(System.in);
        boolean keepRunning = true;
        String menuSelection;
        while(keepRunning) {
            printMenu();
            menuSelection = mainScanner.next();
            switch (menuSelection) {
                case "1":
                    findAndReserveRoom();
                    break;
                case "2":
                    getCustomerReservation();
                    break;
                case "3":
                    createAccount();
                    break;
                case "4":
                    AdminMenu.runAdmin();
                    break;
                case "5":
                    keepRunning = false;
                    break;
                case "6":
                    loadTest();
                default:
                    System.out.println("Please enter number 1-5:");
            }

        }
        return keepRunning;
    }

    public static void findAndReserveRoom() {
        Scanner dateScanner = new Scanner(System.in);
        Date checkInDate = getCheckInDate(dateScanner);
        Date checkOutDate = getCheckoutDate(dateScanner, checkInDate);
        List<IRoom> availableRooms = new ArrayList<IRoom>(HotelResource.findARoom(checkInDate, checkOutDate));
        boolean isRecommendation = false;
        if (availableRooms.isEmpty()) {
            System.out.println("No room available, trying to get recommendations.");
            Date recommendCheckInDate = getRecommendDate(checkInDate);
            Date recommendCheckOutDate = getRecommendDate(checkOutDate);
            List<IRoom> recommendAvailableRooms = new ArrayList<IRoom>(HotelResource.findARoom(recommendCheckInDate, recommendCheckOutDate));
            if (recommendAvailableRooms.isEmpty()) {
                System.out.println("No room available for your date range.");
                return;
            } else {
                System.out.println("Here is the recommend room available:");
                for (IRoom room : recommendAvailableRooms) {
                    System.out.println(room);
                }
                checkInDate = recommendCheckInDate;
                checkOutDate = recommendCheckOutDate;
                availableRooms = recommendAvailableRooms;
                isRecommendation = true;
            }
        }
        if (isRecommendation) {
            System.out.println("You selection of check-in and check-out date is unavailable, we got out recommendation for you.");
        }
        System.out.println("Rooms available from " + checkInDate + " to " + checkOutDate + " :");
        for (IRoom room : availableRooms) {
            System.out.println(room);
        }
        reserveRoom(availableRooms, checkInDate, checkOutDate);
    }

    public static Date getCheckInDate(Scanner scanner) {
        Date checkInDate = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        boolean isMatch = false;
        while (!isMatch) {
            System.out.println("Enter check in date: (mm/dd/yyyy)");
            String dateInput = scanner.nextLine();;
            try {
                checkInDate = simpleDateFormat.parse(dateInput);
                Date today = new Date();
                if (checkInDate.before(today)) {
                    System.out.println("Check in date must after today.");
                } else {
                    isMatch = true;
                }
            } catch (ParseException exception) {
                System.out.println("Invalid date entry, please try again.");
            }
        }
        return checkInDate;
    }
    private static Date getCheckoutDate(Scanner scanner, Date checkInDate) {
        Date checkOutDate = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        boolean isMatch = false;
        while (!isMatch) {
            System.out.println("Enter check out date: (mm/dd/yyyy)");
            String dateInput = scanner.nextLine();;
            try {
                checkOutDate = simpleDateFormat.parse(dateInput);
                Date today = new Date();
                if (checkOutDate.before(checkInDate)) {
                    System.out.println("Check out date must after check in date.");
                } else {
                    isMatch = true;
                }
            } catch (ParseException exception) {
                System.out.println("Invalid date entry, please try again.");
            }
        }
        return checkOutDate;
    }

    private static Date getRecommendDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 7);
        return calendar.getTime();
    }

    private static void reserveRoom(Collection<IRoom> rooms, Date checkInDate, Date checkOutDate) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to reserve a room: (y/n)");
        String choice = scanner.nextLine();
        if (!choice.equalsIgnoreCase("y"))
            return;
        System.out.println("Please enter room number:");
        String roomNumber = null;

        boolean roomNumberMatch = false;
        while (!roomNumberMatch) {
            roomNumber = scanner.nextLine();
            for (IRoom room : rooms) {
                if (room.getRoomNumber().equals(roomNumber)) {
                    roomNumberMatch = true;
                    break;
                }
            }
            if (!roomNumberMatch)
                System.out.println("Room number not match our record, try again.");
        }
        System.out.println("Please enter your email:");
        String email = scanner.nextLine();
        Customer customer = AdminResource.getCustomer(email);
        if (customer == null) {
            System.out.println("No customer exist, please go back create account.");
            return;
        }
        HotelResource.bookARoom(email, HotelResource.getRoom(roomNumber), checkInDate, checkOutDate);
        System.out.println("Your room is reserved.");
    }

    public static void getCustomerReservation() {
        Scanner emailInput = new Scanner(System.in);
        System.out.println("Please enter customer email:");
        String email = emailInput.nextLine();
        Customer customer = HotelResource.getCustomer(email);
        if(customer == null) {
            System.out.println("No customer exist");
            return;
        }
        Collection< Reservation> reservationCollection = HotelResource.getCustomerReservations(email);
        if(reservationCollection.isEmpty()) {
            System.out.println("You have no reservations");
            return;
        }
        for (Reservation reservation : reservationCollection) {
            System.out.println(reservation);
        }
    }

    public static void createAccount() {
        Scanner accountScanner = new Scanner(System.in);
        System.out.println("Please enter you first name:");
        String firstName = accountScanner.nextLine();
        System.out.println("Please enter you last name:");
        String lastname = accountScanner.nextLine();
        System.out.println("Please enter your email:");
        String email = accountScanner.nextLine();

        String emailRegex = "^(.+)@(.+).com$";
        Pattern accountEmailPattern = Pattern.compile(emailRegex);
        boolean isMatch = accountEmailPattern.matcher(email).matches();

        while (!isMatch) {
            System.out.println("Invalid format, try again.");
            email = accountScanner.nextLine();
            isMatch = accountEmailPattern.matcher(email).matches();
        }
        for (Customer customer : AdminResource.getAllCustomers()) {
            if (customer.getEmail().equals(email)) {
                System.out.println("This email is associated with ana existed account.");
                return;
            }
        }
        HotelResource.createACustomer(email, firstName, lastname);
        System.out.println("account created");

    }

    public static void loadTest() {
        HotelResource.createACustomer("asd@asd.com", "f1", "l1");
        HotelResource.createACustomer("zxc@zxc.com", "f2", "l2");

        List<IRoom> roomList = new ArrayList<>();
        roomList.add(new Room("111", 100.0, RoomType.SINGLE));
        roomList.add(new Room("222", 200.0, RoomType.DOUBLE));
        roomList.add(new Room("333", 300.0, RoomType.SINGLE));
        AdminResource.addRoom(roomList);



    }

}
