package ro.dragos.model;

public class Seat extends BaseModel<Long> {

    private final Boolean available;

    private final Long roomId;

    public Seat(Long id, Boolean available, Long roomId) {
        super(id);
        this.available = available;
        this.roomId = roomId;
    }

    public Boolean getAvailable() {
        return available;
    }

    public Long getRoomId() {
        return roomId;
    }
}
