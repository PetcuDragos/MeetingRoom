package ro.dragos.mappers;

import org.springframework.stereotype.Component;
import ro.dragos.dto.RoomDto;
import ro.dragos.model.Room;
import ro.dragos.model.Seat;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoomMapper {

    private final SeatMapper seatMapper;

    public RoomMapper(SeatMapper seatMapper) {
        this.seatMapper = seatMapper;
    }

    public RoomDto toDto(Room room) {
        return new RoomDto(room.getId(), room.getName(), room.getSeats().stream().map(seatMapper::toDto).toList());
    }

    public Room toEntity(RoomDto roomDto) {
        Room room = new Room(roomDto.getId(), roomDto.getName(), new ArrayList<>());
        List<Seat> seats = (roomDto.getSeats() == null ? new ArrayList<>() :
                roomDto.getSeats().stream().map(seat -> seatMapper.toEntity(seat, room)).toList());
        room.getSeats().addAll(seats);
        return room;
    }
}
