package ro.dragos.mappers;

import org.springframework.stereotype.Component;
import ro.dragos.dto.SeatDto;
import ro.dragos.model.Room;
import ro.dragos.model.Seat;

@Component
public class SeatMapper {

    public SeatDto toDto(Seat seat) {
        return new SeatDto(seat.getId(), seat.getAvailable(), seat.getRoom().getId());
    }

    public Seat toEntity(SeatDto seatDto, Room room) {
        return new Seat(seatDto.getId(), seatDto.getAvailable(), room);
    }
}
