package ro.dragos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.dragos.dto.RoomDto;
import ro.dragos.model.Room;
import ro.dragos.repository.RoomRepository;

import java.net.http.HttpResponse;
import java.util.List;

@RestController
public class MainController {

    @Autowired
    private RoomRepository roomRepository;

    @GetMapping(path = "/start")
    public String getHelloMessage() {
        return "hello!";
    }

    @PostMapping(path = "/room")
    public boolean addRoom(@RequestBody RoomDto roomDto) {
        return this.roomRepository
                .addRoom(new Room(roomDto.getId(), roomDto.getName(), roomDto.getNumberOfSeats()));
    }

    @GetMapping("/room")
    public List<RoomDto> getRooms() {
        return this.roomRepository.getRooms().stream()
                .map(room -> new RoomDto(room.getId(), room.getName(), room.getNumberOfSeats())).toList();
    }

}
