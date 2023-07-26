package ro.dragos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.dragos.dto.RoomDto;
import ro.dragos.dto.SeatDto;
import ro.dragos.service.RoomService;
import ro.dragos.utils.StringConstants;

import java.util.List;

@RestController
public class MainController {

    private final RoomService roomService;

    public MainController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping(path = "/start")
    public ResponseEntity<String> getHelloMessage() {
        return ResponseEntity.ok(StringConstants.HELLO_MESSAGE);
    }

    @GetMapping("/room")
    public ResponseEntity<List<RoomDto>> getRooms() {
        return ResponseEntity.ok(this.roomService.getRooms());
    }

    @PostMapping(path = "/room")
    public ResponseEntity<String> addRoom(@RequestBody RoomDto roomDto) {
        this.roomService.addRoom(roomDto);
        return ResponseEntity.ok(StringConstants.ROOM_ADD_OK);
    }

    @PutMapping(path = "/room/{id}")
    public ResponseEntity<String> updateRoom(@PathVariable("id") Long roomId, @RequestBody RoomDto roomDto) {
        this.roomService.updateRoom(roomId, roomDto);
        return ResponseEntity.ok(StringConstants.ROOM_UPDATE_OK);
    }

    @DeleteMapping(path = "/room/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable("id") Long roomId) {
        this.roomService.deleteRoom(roomId);
        return ResponseEntity.ok(StringConstants.ROOM_DELETE_OK);
    }

    @GetMapping(path = "/room/{id}/seats/available")
    public ResponseEntity<List<SeatDto>> getAvailableSeatsForRoom(@PathVariable("id") Long roomId) {
        return ResponseEntity.ok(this.roomService.getAvailableSeatsForRoom(roomId));
    }

    @PostMapping(path = "/room/{id}/seats")
    public ResponseEntity<String> addSeat(@PathVariable("id") Long roomId, @RequestBody SeatDto seatDto) {
        this.roomService.addSeat(roomId, seatDto);
        return ResponseEntity.ok(StringConstants.SEAT_ADD_OK);
    }

    @PutMapping(path = "/room/seats/{id}")
    public ResponseEntity<String> updateSeat(@PathVariable("id") Long seatId, @RequestBody SeatDto seatDto) {
        this.roomService.updateSeat(seatId, seatDto);
        return ResponseEntity.ok(StringConstants.SEAT_UPDATE_OK);
    }

    @DeleteMapping(path = "/room/seats/{id}")
    public ResponseEntity<String> deleteSeat(@PathVariable("id") Long seatId) {
        this.roomService.deleteSeat(seatId);
        return ResponseEntity.ok(StringConstants.SEAT_DELETE_OK);
    }


}
