package ro.dragos.controller;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Assert;
import ro.dragos.model.Room;
import ro.dragos.model.Seat;
import ro.dragos.repository.RoomRepository;
import ro.dragos.repository.SeatRepository;
import ro.dragos.utils.ApiUrlUtil;
import ro.dragos.utils.StringConstants;

import java.util.ArrayList;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTest {

    private final MockMvc mockMvc;
    private final RoomRepository roomRepository;
    private final SeatRepository seatRepository;

    @Autowired
    public MainControllerTest(MockMvc mockMvc, RoomRepository roomRepository, SeatRepository seatRepository) {
        this.mockMvc = mockMvc;
        this.roomRepository = roomRepository;
        this.seatRepository = seatRepository;
    }

    @BeforeEach
    public void cleanUp() {
        roomRepository.deleteAll();
        seatRepository.deleteAll();
    }

    @Test
    public void getHelloMessageTest() throws Exception {
        mockMvc.perform(get("/start"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(StringConstants.HELLO_MESSAGE)));
    }

    @Test
    public void getRoomsTest() throws Exception {

        roomRepository.save(new Room(null, "Room1", new ArrayList<>()));

        mockMvc.perform(get("/room"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Room1")));
    }

    @Test
    public void getAvailableSeats_ShouldBeOk_WhenInsertingRoomAndSeatsSeparately() throws Exception {

        Room room = roomRepository.save(new Room(null, "Room1", new ArrayList<>()));
        seatRepository.save(new Seat(null, true, room));
        seatRepository.save(new Seat(null, true, room));
        seatRepository.save(new Seat(null, false, room));

        mockMvc.perform(get("/room/{id}/seats/available", room.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].available", is(true)))
                .andExpect(jsonPath("$[1].available", is(true)));
    }

    @Test
    public void addRoom_ShouldBeInserted_WhenGivenValidValueWithoutSeats() throws Exception {

        String requestBody = "{\"name\": \"Room1\"}";

        long initialItemCount = roomRepository.count();

        mockMvc.perform(post(ApiUrlUtil.ADD_ROOM)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().is2xxSuccessful());

        long newItemCount = roomRepository.count();

        Room room = roomRepository.findAll().stream().filter(r -> r.getName().equals("Room1")).findAny().orElse(null);

        Assert.isTrue(initialItemCount + 1 == newItemCount, "Size of roomList not changed properly after add");
        Assert.notNull(room, "Added room was not found");
        Assert.isTrue(0 == room.getSeats().size(), "Added room with no seats has seats inserted.");
    }

    @Test
    public void addRoom_ShouldBeInserted_WhenGivenValidValueWithSeats() throws Exception {

        String requestBody = "{\"name\": \"Room1\", \"seats\": [{\"available\" : true}, {\"available\" : false}]}";

        long initialItemCount = roomRepository.count();

        mockMvc.perform(post(ApiUrlUtil.ADD_ROOM)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().is2xxSuccessful());

        long newItemCount = roomRepository.count();

        Room room = roomRepository.findAll().stream().filter(r -> r.getName().equals("Room1")).findAny().orElse(null);

        Assert.isTrue(initialItemCount + 1 == newItemCount, "Size of roomList not changed properly after add");
        Assert.notNull(room, "Added room was not found");
        Assert.isTrue(2 == room.getSeats().size(), "Added room with no seats has seats inserted.");
        Assert.isTrue(1 == room.getSeats().stream().filter(Seat::getAvailable).count(),
                "Should be one seat available, but found different number.");
        Assert.isTrue(1 == room.getSeats().stream().filter(s -> !s.getAvailable()).count(),
                "Should be one seat not available, but found different number.");
    }

    @Test
    public void addRoom_ShouldThrowException_WhenGivenNullName() throws Exception {

        String requestBody = "{}";

        long initialItemCount = roomRepository.count();

        mockMvc.perform(post(ApiUrlUtil.ADD_ROOM)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isConflict())
                .andExpect(content().string(containsString(StringConstants.ROOM_NAME_NULL)));

        long newItemCount = roomRepository.count();

        Assert.isTrue(initialItemCount == newItemCount, "Size of roomList changed properly after incorrect add");
    }

    @Test
    public void updateRoom_ShouldUpdateRoomName_WhenGivenRoomWithExistingIdAndDifferentName() throws Exception {

        String requestBody = "{\"name\":\"Room2\"}";

        Room room = roomRepository.save(new Room(null, "Room1", null));

        long initialItemCount = roomRepository.count();

        mockMvc.perform(put(ApiUrlUtil.UPDATE_ROOM, room.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());

        long newItemCount = roomRepository.count();

        Room updatedRoom = roomRepository.findById(room.getId()).orElse(null);

        Assert.isTrue(initialItemCount == newItemCount, "Size of roomList has changed after update");
        Assert.notNull(updatedRoom, "Updated room was not found");
        Assert.isTrue(0 == updatedRoom.getSeats().size(), "Added room with no seats has seats inserted.");
        Assert.isTrue(updatedRoom.getName().equals("Room2"), "The room name was not updated");
    }

    @Test
    public void updateRoom_ShouldThrowException_WhenGivenRoomWithExistingIdAndNullName() throws Exception {

        String requestBody = "{}";

        Room room = roomRepository.save(new Room(null, "Room1", null));

        long initialItemCount = roomRepository.count();

        mockMvc.perform(put(ApiUrlUtil.UPDATE_ROOM, room.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isConflict())
                .andExpect(content().string(containsString(StringConstants.ROOM_NAME_NULL)));

        long newItemCount = roomRepository.count();

        Room updatedRoom = roomRepository.findById(room.getId()).orElse(null);

        Assert.isTrue(initialItemCount == newItemCount, "Size of roomList has changed after update");
        Assert.notNull(updatedRoom, "Updated room was not found");
        Assert.isTrue(0 == updatedRoom.getSeats().size(), "Added room with no seats has seats inserted.");
        Assert.isTrue(updatedRoom.getName().equals("Room1"), "The room name was updated when it shouldn't.");
    }

    @Test
    public void updateRoom_ShouldThrowException_WhenGivenRoomWithNonExistingId() throws Exception {

        String requestBody = "{\"name\":\"Room2\"}";

        Room room = roomRepository.save(new Room(null, "Room1", null));

        long initialItemCount = roomRepository.count();
        long newId = room.getId() + 1;

        mockMvc.perform(put(ApiUrlUtil.UPDATE_ROOM, newId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isConflict())
                .andExpect(content().string(containsString(StringConstants.ROOM_ID_NOT_FOUND)));

        long newItemCount = roomRepository.count();

        Room updatedRoom = roomRepository.findById(newId).orElse(null);

        Assert.isTrue(initialItemCount == newItemCount, "Size of roomList has changed after update");
        Assert.isNull(updatedRoom, "A room was found with the updated id");
    }


    @Test
    public void deleteRoom_ShouldRemove_WhenGivenAnExistingRoomId() throws Exception {

        Room room = roomRepository.save(new Room(null, "Room1", null));

        long initialItemCount = roomRepository.count();

        mockMvc.perform(delete(ApiUrlUtil.DELETE_ROOM, room.getId()))
                .andExpect(status().isOk());

        long newItemCount = roomRepository.count();

        Room updatedRoom = roomRepository.findById(room.getId()).orElse(null);

        Assert.isTrue(initialItemCount - 1 == newItemCount, "Size of roomList is not as expected");
        Assert.isNull(updatedRoom, "A room was found with the deleted id");
    }

    @Test
    public void deleteRoom_ShouldThrowException_WhenGivenAnIdFromANonExistingRoom() throws Exception {

        Room room = roomRepository.save(new Room(null, "Room1", null));

        long initialItemCount = roomRepository.count();
        long newId = room.getId() + 1;

        mockMvc.perform(delete(ApiUrlUtil.DELETE_ROOM, newId))
                .andExpect(status().isConflict())
                .andExpect(content().string(containsString(StringConstants.ROOM_ID_NOT_FOUND)));

        long newItemCount = roomRepository.count();

        Assert.isTrue(initialItemCount == newItemCount, "Size of roomList is not as expected");
    }
}
