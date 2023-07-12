package ro.dragos.repository;

import org.springframework.stereotype.Repository;
import ro.dragos.model.Room;
import ro.dragos.model.Seat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class RoomRepository {

    private final List<Room> roomList;

    public RoomRepository() {
        this.roomList = new ArrayList<>();
    }

    public boolean addRoom(Room room) {
        return this.roomList.add(room);
    }

    public List<Room> getRooms() {
        return this.roomList;
    }

    public boolean updateRoom(Long roomId, Room updatedRoom) {
        roomList.removeIf(room -> room.getId().equals(roomId));
        return roomList.add(updatedRoom);
    }

    public boolean deleteRoom(Long roomId) {
        return roomList.removeIf(room -> room.getId().equals(roomId));
    }

    public List<Seat> getAvailableSeatsForRoom(Long roomId) {
        Optional<Room> roomFound = this.roomList.stream().filter(room -> room.getId().equals(roomId)).findAny();
        return roomFound.map(room -> room.getSeats().stream().filter(Seat::getAvailable).toList())
                .orElseGet(ArrayList::new);
    }
}
