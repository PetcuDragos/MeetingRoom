package ro.dragos.model;

import java.util.List;

public class Room {

    private final Long id;
    private final String name;
    private final List<Seat> seats;

    public Room(Long id, String name, List<Seat> seats) {
        this.id = id;
        this.name = name;
        this.seats = seats;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Seat> getSeats() {
        return seats;
    }
}
