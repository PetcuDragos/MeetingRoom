package ro.dragos.repository;

import org.springframework.stereotype.Repository;
import ro.dragos.model.Room;

import java.util.ArrayList;
import java.util.List;

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
}
