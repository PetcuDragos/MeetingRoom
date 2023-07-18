package ro.dragos.model;

public class Seat {

    private final Long id;
    private final Boolean available;

    private final Long roomId;

    public Seat(Long id, Boolean available, Long roomId) {
        this.id = id;
        this.available = available;
        this.roomId = roomId;
    }

    public Long getId() {
        return id;
    }

    public Boolean getAvailable() {
        return available;
    }

    public Long getRoomId() {
        return roomId;
    }
}
