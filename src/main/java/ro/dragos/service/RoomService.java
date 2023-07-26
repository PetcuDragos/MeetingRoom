package ro.dragos.service;

import org.springframework.stereotype.Service;
import ro.dragos.dto.RoomDto;
import ro.dragos.dto.SeatDto;
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

    public Room addRoom(RoomDto roomDto) {
        if (roomDto == null) {
            throw new IllegalArgumentException(StringConstants.ROOM_NULL);
        }

        roomDto.setId(null);
        Room room = roomMapper.toEntity(roomDto);

        return roomRepository.save(room);
    }

    public Room updateRoom(Long roomId, RoomDto roomDto) {
        if (roomId == null || roomDto == null) {
            throw new IllegalArgumentException(StringConstants.ROOM_NULL);
        }
        roomDto.setId(roomId);
        Room room = roomMapper.toEntity(roomDto);

        Optional<Room> roomBeforeUpdate = roomRepository.findById(roomId);

        if (roomBeforeUpdate.isEmpty()) {
            throw new NotFoundException(StringConstants.ROOM_ID_NOT_FOUND);
        }

        return roomRepository.save(room);
    }

    public void deleteRoom(Long roomId) {
        if (roomId == null) {
            throw new IllegalArgumentException(StringConstants.ROOM_NULL);
        }
        Optional<Room> optionalRoom = roomRepository.findById(roomId);
        if (optionalRoom.isEmpty()) {
            throw new NotFoundException(StringConstants.ROOM_ID_NOT_FOUND);
        }

        roomRepository.deleteById(roomId);
    }


    public List<SeatDto> getAvailableSeatsForRoom(Long roomId) {
        Optional<Room> roomFound = roomRepository.findById(roomId);
        List<Seat> seats = roomFound.map(room -> room.getSeats().stream().filter(Seat::getAvailable).toList())
                .orElseGet(ArrayList::new);
        return seats.stream().map(seatMapper::toDto).toList();
    }

    public Seat addSeat(Long roomId, SeatDto seatDto) {
        if (roomId == null || seatDto == null) {
            throw new IllegalArgumentException(StringConstants.SEAT_NULL);
        }
        Optional<Room> optionalRoom = roomRepository.findById(roomId);

        if (optionalRoom.isEmpty()) {
            throw new NotFoundException(StringConstants.ROOM_ID_NOT_FOUND);
        }

        seatDto.setId(null);
        Seat seat = seatMapper.toEntity(seatDto, optionalRoom.get());
        return seatRepository.save(seat);
    }

    public Seat updateSeat(Long seatId, SeatDto seatDto) {
        if (seatId == null || seatDto == null) {
            throw new IllegalArgumentException(StringConstants.SEAT_NULL);
        }

        Optional<Seat> optionalSeat = seatRepository.findById(seatId);

        if (optionalSeat.isEmpty()) {
            throw new NotFoundException(StringConstants.SEAT_ID_NOT_FOUND);
        }

        seatDto.setId(seatId);
        Seat seat = seatMapper.toEntity(seatDto, optionalSeat.get().getRoom());

        return seatRepository.save(seat);
    }

    public void deleteSeat(Long seatId) {
        if (seatId == null) {
            throw new IllegalArgumentException(StringConstants.SEAT_NULL);
        }

        Optional<Seat> optionalSeat = seatRepository.findById(seatId);

        if (optionalSeat.isEmpty()) {
            throw new NotFoundException(StringConstants.SEAT_ID_NOT_FOUND);
        }

        seatRepository.deleteById(seatId);
    }
}
