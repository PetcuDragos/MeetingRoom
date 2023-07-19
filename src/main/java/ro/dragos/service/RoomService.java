package ro.dragos.service;

import org.springframework.stereotype.Service;
import ro.dragos.model.Room;
import ro.dragos.model.Seat;
import ro.dragos.repository.RoomRepository;
import ro.dragos.repository.SeatRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final SeatRepository seatRepository;

    public RoomService(RoomRepository roomRepository, SeatRepository seatRepository) {
        this.roomRepository = roomRepository;
        this.seatRepository = seatRepository;
    }

    public List<Room> getRooms() {
        return roomRepository.findAll();
    }

    public boolean addRoom(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Room was null");
        }

        Optional<Room> roomWithSameId = roomRepository.findById(room.getId());
        if (roomWithSameId.isPresent()) {
            return false;
        }

        try {
            roomRepository.save(room);
            room.getSeats().forEach(seatRepository::save);
            return true;
        } catch (Exception e) {
            roomRepository.delete(room);
            room.getSeats().forEach(seatRepository::delete);
            throw e;
        }
    }

    public boolean updateRoom(Long roomId, Room room) {
        if (roomId == null || room == null) {
            throw new IllegalArgumentException("RoomId or room were null");
        }

        Optional<Room> roomBeforeUpdate = roomRepository.findById(roomId);

        if (roomBeforeUpdate.isEmpty()) {
            return false;
        }

        Room oldRoom = roomBeforeUpdate.get();

        try {
            roomRepository.save(room);
            room.getSeats().forEach(seatRepository::save);
            oldRoom.getSeats().stream()
                    .filter(oldSeat -> room.getSeats().stream().noneMatch(seat -> seat.getId().equals(oldSeat.getId())))
                    .forEach(seatRepository::delete);
            return true;
        } catch (Exception e) {
            roomRepository.save(oldRoom);
            oldRoom.getSeats().forEach(seatRepository::save);
            room.getSeats().stream()
                    .filter(seat -> oldRoom.getSeats().stream().noneMatch(oldSeat -> oldSeat.getId().equals(seat.getId())))
                    .forEach(seatRepository::delete);
            return false;
        }
    }

    public boolean deleteRoom(Long roomId) {
        if (roomId == null) {
            throw new IllegalArgumentException("roomId was null");
        }
        try {
            roomRepository.deleteById(roomId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public List<Seat> getAvailableSeatsForRoom(Long roomId) {
        Optional<Room> roomFound = roomRepository.findById(roomId);
        return roomFound.map(room -> room.getSeats().stream().filter(Seat::getAvailable).toList())
                .orElseGet(ArrayList::new);
    }
}
