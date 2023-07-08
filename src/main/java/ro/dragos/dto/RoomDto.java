package ro.dragos.dto;

import ro.dragos.model.Seat;

import java.util.List;

public class RoomDto {

    private final Long id;
    private final String name;
    private final List<Seat> seats;

    public RoomDto(Long id, String name, List<Seat> seats) {
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
