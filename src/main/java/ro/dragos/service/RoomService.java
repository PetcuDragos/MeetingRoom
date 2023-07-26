package ro.dragos.service;

import org.springframework.stereotype.Service;
import ro.dragos.dto.RoomDto;
import ro.dragos.dto.SeatDto;
import ro.dragos.exceptions.IdUsedException;
import ro.dragos.exceptions.NotFoundException;
import ro.dragos.mappers.RoomMapper;
import ro.dragos.mappers.SeatMapper;
import ro.dragos.model.Room;
import ro.dragos.model.Seat;
import ro.dragos.repository.RoomRepository;
import ro.dragos.repository.SeatRepository;
import ro.dragos.utils.StringConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final SeatRepository seatRepository;
    private final SeatMapper seatMapper;
    private final RoomMapper roomMapper;

    public RoomService(RoomRepository roomRepository, SeatRepository seatRepository, SeatMapper seatMapper, RoomMapper roomMapper) {
        this.roomRepository = roomRepository;
        this.seatRepository = seatRepository;
        this.seatMapper = seatMapper;
        this.roomMapper = roomMapper;
    }

    public List<RoomDto> getRooms() {
        return roomRepository.findAll().stream().map(roomMapper::toDto).toList();
    }

    public boolean addRoom(RoomDto roomDto) {
        if (roomDto == null) {
            throw new IllegalArgumentException(StringConstants.ROOM_NULL);
        }

        Room room = roomMapper.toEntity(roomDto);

        Optional<Room> roomWithSameId = roomRepository.findById(room.getId());
        if (roomWithSameId.isPresent()) {
            throw new IdUsedException(StringConstants.ROOM_ID_USED);
        }

        try {
            roomRepository.save(room);
            return true;
        } catch (Exception e) {
            roomRepository.delete(room);
            throw e;
        }
    }

    public boolean updateRoom(Long roomId, RoomDto roomDto) {
        if (roomId == null || roomDto == null) {
            throw new IllegalArgumentException(StringConstants.ROOM_NULL);
        }
        roomDto.setId(roomId);
        Room room = roomMapper.toEntity(roomDto);

        Optional<Room> roomBeforeUpdate = roomRepository.findById(roomId);

        if (roomBeforeUpdate.isEmpty()) {
            throw new NotFoundException(StringConstants.ROOM_ID_NOT_FOUND);
        }

        Room oldRoom = roomBeforeUpdate.get();

        try {
            roomRepository.delete(oldRoom);
            roomRepository.save(room);
            return true;
        } catch (Exception e) {
            roomRepository.delete(room);
            roomRepository.save(oldRoom);
            throw e;
        }
    }

    public boolean deleteRoom(Long roomId) {
        if (roomId == null) {
            throw new IllegalArgumentException(StringConstants.ROOM_NULL);
        }
        Optional<Room> optionalRoom = roomRepository.findById(roomId);
        if (optionalRoom.isEmpty()) {
            throw new NotFoundException(StringConstants.ROOM_ID_NOT_FOUND);
        }

        roomRepository.deleteById(roomId);
        return true;
    }


    public List<SeatDto> getAvailableSeatsForRoom(Long roomId) {
        Optional<Room> roomFound = roomRepository.findById(roomId);
        List<Seat> seats = roomFound.map(room -> room.getSeats().stream().filter(Seat::getAvailable).toList())
                .orElseGet(ArrayList::new);
        return seats.stream().map(seatMapper::toDto).toList();
    }
}
