package ro.dragos.dto;

public class SeatDto {

    private final Long id;
    private final Boolean available;

    public SeatDto(Long id, Boolean available) {
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
