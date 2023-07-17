package ro.dragos.controller;

import org.springframework.web.bind.annotation.*;
import ro.dragos.convertors.RoomConverter;
import ro.dragos.dto.RoomDto;
import ro.dragos.dto.SeatDto;
import ro.dragos.repository.RoomRepository;

import java.util.List;

@RestController
public class MainController {

    private final RoomRepository roomRepository;

    public MainController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @GetMapping(path = "/start")
    public String getHelloMessage() {
        return "hello!";
    }

    @GetMapping("/room")
    public List<RoomDto> getRooms() {
        return this.roomRepository.getRooms().stream().map(RoomConverter::convertRoomToRoomDto).toList();
    }

    @PostMapping(path = "/room")
    public boolean addRoom(@RequestBody RoomDto roomDto) {
        return this.roomRepository.addRoom(RoomConverter.convertRoomDtoToRoom(roomDto));
    }

    @PutMapping(path = "/room/{id}")
    public boolean updateRoom(@PathVariable("id") Long roomId, @RequestBody RoomDto roomDto) {
        return this.roomRepository.updateRoom(roomId, RoomConverter.convertRoomDtoToRoom(roomDto));
    }

    @DeleteMapping(path = "/room/{id}")
    public boolean deleteRoom(@PathVariable("id") Long roomId) {
        return this.roomRepository.deleteRoom(roomId);
    }

    @GetMapping(path = "/room/{id}/seats/available")
    public List<SeatDto> getAvailableSeatsForRoom(@PathVariable("id") Long roomId) {
        return this.roomRepository.getAvailableSeatsForRoom(roomId).stream()
                .map(seat -> new SeatDto(seat.getId(), seat.getAvailable()))
                .toList();
    }


}
