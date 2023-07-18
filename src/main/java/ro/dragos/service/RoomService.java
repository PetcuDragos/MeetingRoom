package ro.dragos.service;

import ro.dragos.model.Room;
import ro.dragos.model.Seat;
import ro.dragos.repository.RoomRepository;
import ro.dragos.repository.SeatRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoomService {

    private final RoomRepository roomRepository;
    private final SeatRepository seatRepository;

    public RoomService(RoomRepository roomRepository, SeatRepository seatRepository) {
        this.roomRepository = roomRepository;
        this.seatRepository = seatRepository;
    }

    public List<Room> getRooms() {
        return roomRepository.getRooms();
    }

    public boolean addRoom(Room room) {
        boolean roomWasAdded = roomRepository.addRoom(room);
        if (roomWasAdded) {
            boolean seatFailedToAdd = room.getSeats().stream().anyMatch(seat -> !seatRepository.addSeat(seat));
            if (seatFailedToAdd) {
                roomRepository.deleteRoom(room.getId());
                room.getSeats().forEach(seat -> seatRepository.deleteSeat(seat.getId()));
            }
            return !seatFailedToAdd;
        }
        return false;
    }

    public boolean updateRoom(Long roomId, Room room) {
        Room roomBeforeUpdate = roomRepository.getRoomById(roomId);

        if (roomBeforeUpdate == null) {
            return false;
        }

        List<Seat> oldSeats = roomBeforeUpdate.getSeats();

        boolean roomWasUpdated = roomRepository.updateRoom(roomId, room);

        if (roomWasUpdated) {
            room.getSeats().stream().filter(seat -> !oldSeats.contains(seat)).forEach(seatRepository::addSeat);
            oldSeats.stream().filter(seat -> !room.getSeats().contains(seat))
                    .forEach(seat -> seatRepository.deleteSeat(seat.getId()));
        }

        return roomWasUpdated;
    }

    public boolean deleteRoom(Long roomId) {
        Room roomBeforeDelete = roomRepository.getRoomById(roomId);

        if (roomBeforeDelete == null) {
            return false;
        }

        List<Seat> oldSeats = roomBeforeDelete.getSeats();

        boolean roomWasDeleted = roomRepository.deleteRoom(roomId);

        if (roomWasDeleted) {
            oldSeats.forEach(seat -> seatRepository.deleteSeat(seat.getId()));
        }

        return roomWasDeleted;
    }


    public List<Seat> getAvailableSeatsForRoom(Long roomId) {
        Optional<Room> roomFound = roomRepository.getRooms().stream().filter(room -> room.getId().equals(roomId)).findAny();
        return roomFound.map(room -> room.getSeats().stream().filter(Seat::getAvailable).toList())
                .orElseGet(ArrayList::new);
    }
}
