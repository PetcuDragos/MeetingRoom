package ro.dragos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.dragos.convertors.RoomConverter;
import ro.dragos.dto.RoomDto;
import ro.dragos.repository.RoomRepository;

import java.util.List;

@RestController
public class MainController {

    @Autowired
    private RoomRepository roomRepository;

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


}
