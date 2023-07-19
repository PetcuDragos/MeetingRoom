package ro.dragos.convertors;

import ro.dragos.dto.SeatDto;
import ro.dragos.model.Seat;

public class SeatConverter {

    public static SeatDto convertSeatToSeatDto(Seat seat) {
        return new SeatDto(seat.getId(), seat.getAvailable(), seat.getRoom().getId());
    }
}
