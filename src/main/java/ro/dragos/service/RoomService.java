package ro.dragos.service;

import ro.dragos.model.Room;
import ro.dragos.model.Seat;
import ro.dragos.repository.RoomRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> getRooms() {
        return roomRepository.getRooms();
    }

    public boolean addRoom(Room room) {
        return roomRepository.addRoom(room);
    }

    public boolean updateRoom(Long roomId, Room room) {
        return roomRepository.updateRoom(roomId, room);
    }

    public boolean deleteRoom(Long roomId) {
        return roomRepository.deleteRoom(roomId);
    }


    public List<Seat> getAvailableSeatsForRoom(Long roomId) {
        Optional<Room> roomFound = roomRepository.getRooms().stream().filter(room -> room.getId().equals(roomId)).findAny();
        return roomFound.map(room -> room.getSeats().stream().filter(Seat::getAvailable).toList())
                .orElseGet(ArrayList::new);
    }
}
