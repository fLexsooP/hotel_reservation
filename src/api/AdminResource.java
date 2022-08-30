package api;

import model.*;
import service.*;

import java.util.Collection;
import java.util.List;

public class AdminResource {
    public static final CustomerService customerService = CustomerService.getInstance();
    public static final ReservationService reservationService = ReservationService.getInstance();

    private static AdminResource adminResource = null;
    public static AdminResource getInstance() {
        if (adminResource == null)
            adminResource = new AdminResource();
        return adminResource;
    }
    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void addRoom(List<IRoom> rooms) {
        for (IRoom room : rooms) {
            reservationService.addRoom(room);
        }
    }

    public Collection<IRoom> getAllRooms() {
        return reservationService.getAllRooms();
    }

    public Collection<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    public void displayAllReservations() {
        reservationService.printAllReservation();
    }
}
