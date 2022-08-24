package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

public class ReservationService {
    public static final Map<String, IRoom> roomMap = new HashMap<>();//key: roomNumber
    public static final Set<Reservation> reservationSet = new HashSet<>();

    public void addRoom(IRoom room) {
        roomMap.put(room.getRoomNumber(), room);
    }

    public IRoom getARoom(String roomId) {
        return roomMap.get(roomId);
    }

    public Reservation reserveARoom(Customer customer,
                                    IRoom room,
                                    Date checkInDate,
                                    Date checkOutDate) {
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservationSet.add(reservation);
        return reservation;
    }

    public Collection<Reservation> getCustomerReservation(Customer customer) {
        List<Reservation> customerReservation = new ArrayList<>();
        for (Reservation reservation : reservationSet) {
            if(reservation.getCustomer().equals(customer))
                customerReservation.add(reservation);
        }
        return customerReservation;
    }

    public void printAllReservation() {
        for (Reservation reservation : reservationSet) {
            System.out.println(reservation);
        }
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        List<IRoom> availableRooms = new ArrayList<IRoom>();
        boolean flag = true;
        for (IRoom room : roomMap.values()) {
            for (Reservation reservation : reservationSet){

                if (reservation.getRoom().getRoomNumber().equals(room.getRoomNumber())) {
                    if (checkInDate.after(reservation.getCheckOutDate()) && checkOutDate.after(checkInDate) ||
                            checkOutDate.before(reservation.getCheckInDate()) && checkInDate.before(checkOutDate)) {
                        availableRooms.add(room);
                    }
                    flag = false;
                }

            }
            if (flag) {
                availableRooms.add(room);
            }
            flag = true;
        }
        return availableRooms;
    }


    public Collection<IRoom> getAllRooms() {
        return roomMap.values();
    }

}
