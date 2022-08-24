package model;

import java.util.Date;

public class Reservation {
    private Customer customer;
    private IRoom room;
    private Date checkInDate;
    private Date checkOutDate;

    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public IRoom getRoom() {
        return room;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public Double getTotalPrice() {
        int days = daysBetween(this.checkInDate, this.checkOutDate);
        return this.room.getRoomPrice() * days;
    }

    private int daysBetween(Date d1, Date d2) {
        return (int)(d2.getTime() - d1.getTime()) / (24 * 60 * 60 * 1000);
    }

    @Override
    public String toString() {
        return "Customer: " + customer +
                "\n Room: " + room +
                "\n Date: " + checkInDate + '-' + checkOutDate +
                "\n Total Price: " + this.getTotalPrice();
    }
}
