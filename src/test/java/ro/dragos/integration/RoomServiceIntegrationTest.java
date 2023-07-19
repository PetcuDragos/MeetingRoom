package ro.dragos.integration;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
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
        List<Room> rooms = new ArrayList<>(roomService.getRooms());
        rooms.forEach(room -> roomService.deleteRoom(room.getId()));
    }


    @Test
    public void addRoom_ShouldBeInserted_WhenGivenCorrectValue() {
        int numberOfRoomsBefore = roomService.getRooms().size();
        boolean roomWasAdded = roomService.addRoom(new Room(1L, "Room1", new ArrayList<>()));
        Assert.isTrue(roomWasAdded,
                "Room was not added");
        Assert.isTrue(roomService.getRooms().size() == numberOfRoomsBefore + 1,
                "Size of roomList not changed properly after add");
        Assert.notNull(roomService.getRooms().stream().filter(room -> room.getId().equals(1L)).findAny().orElse(null),
                "Added room was not found with the same id");
    }

    @Test
    public void addRoom_ShouldNotBeInserted_WhenGivenAlreadyUsedId() {
        boolean firstRoomWasAdded = roomService.addRoom(new Room(1L, "Room1", new ArrayList<>()));
        int numberOfRoomsBefore = roomService.getRooms().size();
        Assert.isTrue(firstRoomWasAdded,
                "First room was not added");

        boolean secondRoomWasAdded = roomService.addRoom(new Room(1L, "Room2", new ArrayList<>()));

        Assert.isTrue(!secondRoomWasAdded,
                "The second room was added with duplicated ID");
        Assert.isTrue(roomService.getRooms().size() == numberOfRoomsBefore,
                "Size of roomList changed after add");
        Optional<Room> optionalRoom = roomService.getRooms().stream().filter(room -> room.getId().equals(1L)).findAny();
        Assert.isTrue(optionalRoom.isPresent() && optionalRoom.get().getName().equals("Room1"),
                "The name of the room changed after failed add");
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
        boolean roomWasAdded = roomService.addRoom(new Room(1L, "Room1", new ArrayList<>()));
        Assert.isTrue(roomWasAdded, "Room was not inserted");
        int numberOfRooms = roomService.getRooms().size();

        boolean roomWasUpdated = roomService.updateRoom(1L, new Room(1L, "Room2", new ArrayList<>()));

        Assert.isTrue(roomWasUpdated, "Room update has returned false");
        Assert.isTrue(numberOfRooms == roomService.getRooms().size(), "The number of rooms has changed");

        Optional<Room> optionalRoom = roomService.getRooms().stream().filter(room -> room.getId().equals(1L)).findAny();

        Assert.isTrue(optionalRoom.isPresent(), "Room was not found with the inserted id");
        Assert.isTrue(optionalRoom.get().getName().equals("Room2"), "The room values were not updated");
    }

    @Test
    public void updateRoom_ShouldNotUpdate_WhenGivenRoomWithNonExistingId() {
        int numberOfRooms = roomService.getRooms().size();

        boolean roomWasUpdated = roomService.updateRoom(1L, new Room(1L, "Room1", new ArrayList<>()));

        Assert.isTrue(!roomWasUpdated, "Room update returned true when it should've returned false");
        Assert.isTrue(numberOfRooms == roomService.getRooms().size(), "The number of rooms has changed");

        Optional<Room> optionalRoom = roomService.getRooms().stream().filter(room -> room.getId().equals(1L)).findAny();

        Assert.isTrue(optionalRoom.isEmpty(), "A room was found with the updated id");
    }

    @Test
    public void updateRoom_ShouldThrowException_WhenGivenIdIsNull() {
        try {
            roomService.updateRoom(null, new Room(1L, "Room1", new ArrayList<>()));
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
        boolean roomWasAdded = roomService.addRoom(new Room(1L, "Room1", new ArrayList<>()));
        Assert.isTrue(roomWasAdded, "Room was not inserted");
        int numberOfRooms = roomService.getRooms().size();

        roomService.deleteRoom(1L);

        Assert.isTrue(numberOfRooms == roomService.getRooms().size() + 1,
                "The number of rooms has not decreased by 1");

        Optional<Room> optionalRoom = roomService.getRooms().stream().filter(room -> room.getId().equals(1L)).findAny();

        Assert.isTrue(optionalRoom.isEmpty(), "Found a room with an id equal to the one deleted");
    }

    @Test
    public void deleteRoom_ShouldNotFail_WhenGivenAnIdFromANonExistingRoom() {
        int numberOfRooms = roomService.getRooms().size();

        roomService.deleteRoom(1L);

        Assert.isTrue(numberOfRooms == roomService.getRooms().size(),
                "The number of rooms has changed");
    }
}
