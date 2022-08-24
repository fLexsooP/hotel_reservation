package model;

public class FreeRoom extends Room{
    public FreeRoom(String roomNumber, Double price, RoomType roomType) {
        super(roomNumber, (double) 0, roomType);
    }

    @Override
    public String toString() {
        return "Room Number: " + roomNumber +
                " Price: 0" +
                " Type: " + roomType;
    }
}
