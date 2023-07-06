package ro.dragos.dto;

public class RoomDto {

    private final Long id;
    private final String name;
    private final Integer numberOfSeats;

    public RoomDto(Long id, String name, Integer numberOfSeats) {
        this.id = id;
        this.name = name;
        this.numberOfSeats = numberOfSeats;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }
}
