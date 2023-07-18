package ro.dragos.model;

import java.util.List;

public class Room extends BaseModel<Long> {


    private final String name;
    private final List<Seat> seats;

    public Room(Long id, String name, List<Seat> seats) {
        super(id);
        this.name = name;
        this.seats = seats;
    }

    public String getName() {
        return name;
    }

    public List<Seat> getSeats() {
        return seats;
    }
}
