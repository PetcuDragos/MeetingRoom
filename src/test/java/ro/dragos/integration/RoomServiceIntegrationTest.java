package ro.dragos.integration;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import ro.dragos.dto.RoomDto;
import ro.dragos.exceptions.NotFoundException;
import ro.dragos.model.Room;
import ro.dragos.service.RoomService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// use
// <method>_Should<expected>_When<condition>
// Deposit_ShouldIncreaseBalance_WhenGivenPositiveValue()
@SpringBootTest
public class RoomServiceIntegrationTest {

    private final RoomService roomService;

    public RoomServiceIntegrationTest(@Autowired RoomService roomService) {
        this.roomService = roomService;
    }

    @BeforeEach
    public void cleanUp() {
        List<RoomDto> rooms = roomService.getRooms();
        rooms.forEach(room -> roomService.deleteRoom(room.getId()));
    }


    @Test
    public void addRoom_ShouldBeInserted_WhenGivenCorrectValue() {
        int numberOfRoomsBefore = roomService.getRooms().size();
        roomService.addRoom(new RoomDto(1L, "Room1", new ArrayList<>()));

        Assert.isTrue(roomService.getRooms().size() == numberOfRoomsBefore + 1,
                "Size of roomList not changed properly after add");
        Assert.notNull(roomService.getRooms().stream().filter(room -> room.getId().equals(1L)).findAny().orElse(null),
                "Added room was not found with the same id");
    }

    @Test
    public void addRoom_ShouldThrowException_WhenGivenNullValue() {
        try {
            roomService.addRoom(null);
            assert false;
        } catch (Exception e) {
            Assert.isInstanceOf(IllegalArgumentException.class, e, "Different exception when added null room");
        }
    }

    @Test
    public void updateRoom_ShouldUpdateRoom_WhenGivenRoomWithExistingId() {
        Room room = roomService.addRoom(new RoomDto(1L, "Room1", new ArrayList<>()));
        int numberOfRooms = roomService.getRooms().size();

        roomService.updateRoom(room.getId(), new RoomDto(1L, "Room2", new ArrayList<>()));

        Assert.isTrue(numberOfRooms == roomService.getRooms().size(), "The number of rooms has changed");

        Optional<RoomDto> optionalRoom = roomService.getRooms().stream().filter(r -> r.getId().equals(room.getId())).findAny();

        Assert.isTrue(optionalRoom.isPresent(), "Room was not found with the inserted id");
        Assert.isTrue(optionalRoom.get().getName().equals("Room2"), "The room values were not updated");
    }

    @Test
    public void updateRoom_ShouldThrowException_WhenGivenRoomWithNonExistingId() {
        int numberOfRooms = roomService.getRooms().size();

        try {
            roomService.updateRoom(1L, new RoomDto(1L, "Room1", new ArrayList<>()));
        } catch (NotFoundException notFoundException) {
            assert true;
        } catch (RuntimeException e) {
            assert false;
        }
        Assert.isTrue(numberOfRooms == roomService.getRooms().size(), "The number of rooms has changed");

        Optional<RoomDto> optionalRoom = roomService.getRooms().stream().filter(room -> room.getId().equals(1L)).findAny();

        Assert.isTrue(optionalRoom.isEmpty(), "A room was found with the updated id");
    }

    @Test
    public void updateRoom_ShouldThrowException_WhenGivenIdIsNull() {
        try {
            roomService.updateRoom(null, new RoomDto(1L, "Room1", new ArrayList<>()));
            assert false;
        } catch (Exception e) {
            Assert.isInstanceOf(IllegalArgumentException.class, e, "The type of exception is different.");
        }
    }

    @Test
    public void updateRoom_ShouldThrowException_WhenGivenRoomIsNull() {
        try {
            roomService.updateRoom(1L, null);
            assert false;
        } catch (Exception e) {
            Assert.isInstanceOf(IllegalArgumentException.class, e, "The type of exception is different.");
        }
    }

    @Test
    public void deleteRoom_ShouldRemove_WhenGivenAnExistingRoom() {
        Room room = roomService.addRoom(new RoomDto(1L, "Room1", new ArrayList<>()));
        int numberOfRooms = roomService.getRooms().size();

        roomService.deleteRoom(room.getId());

        Assert.isTrue(numberOfRooms == roomService.getRooms().size() + 1,
                "The number of rooms has not decreased by 1");

        Optional<RoomDto> optionalRoom = roomService.getRooms().stream().filter(r -> r.getId().equals(room.getId())).findAny();

        Assert.isTrue(optionalRoom.isEmpty(), "Found a room with an id equal to the one deleted");
    }

    @Test
    public void deleteRoom_ShouldThrowException_WhenGivenAnIdFromANonExistingRoom() {
        try {
            roomService.deleteRoom(1L);
        } catch (NotFoundException notFoundException) {
            assert true;
        } catch (RuntimeException exception) {
            assert false;
        }

    }
}
