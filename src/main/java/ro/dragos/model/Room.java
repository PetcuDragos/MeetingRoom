package ro.dragos.model;

public class Room {

    private final Long id;
    private final String name;
    private final Integer numberOfSeats;

    public Room(Long id, String name, Integer numberOfSeats) {
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
