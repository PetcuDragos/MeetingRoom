package ro.dragos.convertors;

import ro.dragos.dto.RoomDto;
import ro.dragos.model.Room;
import ro.dragos.model.Seat;

import java.util.ArrayList;
import java.util.List;

public class RoomConverter {

    public static RoomDto convertRoomToRoomDto(Room room) {
        return new RoomDto(room.getId(), room.getName(), room.getSeats());
    }

    public static Room convertRoomDtoToRoom(RoomDto roomDto) {
        List<Seat> seats = (roomDto.getSeats() == null ? new ArrayList<>() : roomDto.getSeats());
        return new Room(roomDto.getId(), roomDto.getName(), seats);
    }
}
