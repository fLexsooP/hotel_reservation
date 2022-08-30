package api;

import model.*;
import service.*;

import java.util.Collection;
import java.util.Date;

public class HotelResource {
    public static final CustomerService customerService = new CustomerService();
    public static final ReservationService reservationService = new ReservationService();

    private static HotelResource hotelResource = null;
    public static HotelResource getInstance() {
        if (hotelResource == null)
            hotelResource = new HotelResource();
        return hotelResource;
    }
    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void createACustomer(String email, String firstName, String lastName) {
        customerService.addCustomer(email, firstName, lastName);
    }

    public IRoom getRoom(String roomNumber) {
        return reservationService.getARoom(roomNumber);
    }

    public Reservation bookARoom(String customerEmail,
                                 IRoom room,
                                 Date checkInDate,
                                 Date checkOutDate) {
        return reservationService.reserveARoom(customerService.getCustomer(customerEmail), room, checkInDate, checkOutDate);
    }

    public Collection<Reservation> getCustomerReservations(String customerEmail) {
        return reservationService.getCustomerReservation(customerService.getCustomer(customerEmail));
    }

    public Collection<IRoom> findARoom(Date checkIn, Date checkOut) {
        return reservationService.findRooms(checkIn, checkOut);
    }
}
