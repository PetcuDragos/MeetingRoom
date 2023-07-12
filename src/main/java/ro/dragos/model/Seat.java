package ro.dragos.model;

public class Seat {

    private final Long id;
    private final Boolean available;

    public Seat(Long id, Boolean available) {
        this.id = id;
        this.available = available;
    }

    public Long getId() {
        return id;
    }

    public Boolean getAvailable() {
        return available;
    }
}
