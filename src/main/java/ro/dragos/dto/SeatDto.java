package ro.dragos.dto;

public class SeatDto {

    private Long id;
    private final Boolean available;

    private final Long roomId;

    public SeatDto(Long id, Boolean available, Long roomId) {
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

    public void setId(Long id) {
        this.id = id;
    }
}
