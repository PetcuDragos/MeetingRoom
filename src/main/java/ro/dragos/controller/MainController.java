package ro.dragos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.dragos.dto.RoomDto;
import ro.dragos.dto.SeatDto;
import ro.dragos.service.RoomService;
import ro.dragos.utils.ApiUrlUtil;
import ro.dragos.utils.StringConstants;

import java.util.List;
@CrossOrigin(origins = "https://editor.swagger.io/", maxAge = 3600)
@RestController
public class MainController {

    private final RoomService roomService;

    public MainController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping(ApiUrlUtil.GET_HELLO_MESSAGE)
    public ResponseEntity<String> getHelloMessage() {
        return ResponseEntity.ok(StringConstants.HELLO_MESSAGE);
    }

    @GetMapping(ApiUrlUtil.GET_ROOMS)
    public ResponseEntity<List<RoomDto>> getRooms() {
        return ResponseEntity.ok(this.roomService.getRooms());
    }

    @PostMapping(ApiUrlUtil.ADD_ROOM)
    public ResponseEntity<String> addRoom(@RequestBody RoomDto roomDto) {
        this.roomService.addRoom(roomDto);
        return ResponseEntity.ok(StringConstants.ROOM_ADD_OK);
    }

    @PutMapping(ApiUrlUtil.UPDATE_ROOM)
    public ResponseEntity<String> updateRoom(@PathVariable("id") Long roomId, @RequestBody RoomDto roomDto) {
        this.roomService.updateRoom(roomId, roomDto);
        return ResponseEntity.ok(StringConstants.ROOM_UPDATE_OK);
    }

    @DeleteMapping(ApiUrlUtil.DELETE_ROOM)
    public ResponseEntity<String> deleteRoom(@PathVariable("id") Long roomId) {
        this.roomService.deleteRoom(roomId);
        return ResponseEntity.ok(StringConstants.ROOM_DELETE_OK);
    }

    @GetMapping(ApiUrlUtil.GET_AVAILABLE_SEATS)
    public ResponseEntity<List<SeatDto>> getAvailableSeatsForRoom(@PathVariable("id") Long roomId) {
        return ResponseEntity.ok(this.roomService.getAvailableSeatsForRoom(roomId));
    }

    @PostMapping(ApiUrlUtil.ADD_SEAT)
    public ResponseEntity<String> addSeat(@PathVariable("id") Long roomId, @RequestBody SeatDto seatDto) {
        this.roomService.addSeat(roomId, seatDto);
        return ResponseEntity.ok(StringConstants.SEAT_ADD_OK);
    }

    @PutMapping(ApiUrlUtil.UPDATE_SEAT)
    public ResponseEntity<String> updateSeat(@PathVariable("id") Long seatId, @RequestBody SeatDto seatDto) {
        this.roomService.updateSeat(seatId, seatDto);
        return ResponseEntity.ok(StringConstants.SEAT_UPDATE_OK);
    }

    @DeleteMapping(ApiUrlUtil.DELETE_SEAT)
    public ResponseEntity<String> deleteSeat(@PathVariable("id") Long seatId) {
        this.roomService.deleteSeat(seatId);
        return ResponseEntity.ok(StringConstants.SEAT_DELETE_OK);
    }

    @GetMapping(ApiUrlUtil.GET_SEATS)
    public ResponseEntity<List<SeatDto>> getAllSeats() {
        return ResponseEntity.ok(this.roomService.getSeats());
    }
}
