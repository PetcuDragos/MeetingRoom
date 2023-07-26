package ro.dragos.controller;

import org.springframework.web.bind.annotation.*;
import ro.dragos.dto.RoomDto;
import ro.dragos.dto.SeatDto;
import ro.dragos.service.RoomService;

import java.util.List;

@RestController
public class MainController {

    private final RoomService roomService;

    public MainController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping(path = "/start")
    public String getHelloMessage() {
        return "hello!";
    }

    @GetMapping("/room")
    public List<RoomDto> getRooms() {
        return this.roomService.getRooms();
    }

    @PostMapping(path = "/room")
    public boolean addRoom(@RequestBody RoomDto roomDto) {
        return this.roomService.addRoom(roomDto);
    }

    @PutMapping(path = "/room/{id}")
    public boolean updateRoom(@PathVariable("id") Long roomId, @RequestBody RoomDto roomDto) {
        return this.roomService.updateRoom(roomId, roomDto);
    }

    @DeleteMapping(path = "/room/{id}")
    public boolean deleteRoom(@PathVariable("id") Long roomId) {
        return this.roomService.deleteRoom(roomId);
    }

    @GetMapping(path = "/room/{id}/seats/available")
    public List<SeatDto> getAvailableSeatsForRoom(@PathVariable("id") Long roomId) {
        return this.roomService.getAvailableSeatsForRoom(roomId);
    }


}
