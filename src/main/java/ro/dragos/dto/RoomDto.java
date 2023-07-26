package ro.dragos.dto;

import java.util.List;

public class RoomDto {

    private final Long id;
    private final String name;
    private final List<SeatDto> seats;

    public RoomDto(Long id, String name, List<SeatDto> seats) {
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

    public List<SeatDto> getSeats() {
        return seats;
    }
}
