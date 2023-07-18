package ro.dragos.repository;

import org.springframework.stereotype.Repository;
import ro.dragos.model.Seat;

import java.util.List;

@Repository
public class SeatRepository {

    private List<Seat> seatList;

    public List<Seat> getSeats() {
        return seatList;
    }

    public boolean addSeat(Seat seat) {
        return seatList.add(seat);
    }

    public boolean deleteSeat(Long seatId) {
        return seatList.removeIf(seat -> seat.getId().equals(seatId));
    }

    /**
     * @param seatId - the id of the searched seat
     * @return the seat if found, null otherwise
     */
    public Seat getSeatById(Long seatId) {
        return seatList.stream().filter(seat -> seat.getId().equals(seatId)).findAny().orElse(null);
    }

}
