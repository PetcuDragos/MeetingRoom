package ro.dragos.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ro.dragos.dto.RoomDto;
import ro.dragos.model.Room;
import ro.dragos.model.Seat;
import ro.dragos.service.RoomService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTest {

    private final MockMvc mockMvc;

    @MockBean
    private RoomService roomService;

    public MainControllerTest(@Autowired MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    public void getHelloMessageTest() throws Exception {
        mockMvc.perform(get("/start"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("hello!")));
    }

    @Test
    public void getRoomsTest() throws Exception {

        when(roomService.getRooms()).thenReturn(List.of(new Room(1L, "Room1", new ArrayList<>())));

        mockMvc.perform(get("/room"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Room1")));
    }

    @Test
    public void addRoomTest() throws Exception {

        RoomDto roomDto = new RoomDto(1L, "Room1", new ArrayList<>());

        when(roomService.addRoom(Mockito.any(Room.class))).thenReturn(true);

        mockMvc.perform(post("/room")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(roomDto)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("true")));
    }

    @Test
    public void updateRoomTest() throws Exception {

        RoomDto roomDto = new RoomDto(1L, "Room1", new ArrayList<>());

        when(roomService.updateRoom(Mockito.any(Long.class), Mockito.any(Room.class))).thenReturn(true);

        mockMvc.perform(put("/room/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(roomDto)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("true")));
    }

    @Test
    public void deleteRoomTest() throws Exception {

        when(roomService.deleteRoom(1L)).thenReturn(true);
        when(roomService.deleteRoom(Mockito.longThat((longValue) -> longValue != 1L))).thenReturn(false);

        mockMvc.perform(delete("/room/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("true")));


        mockMvc.perform(delete("/room/{id}", 2))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("false")));
    }

    @Test
    public void getAvailableSeatsForRoomTest() throws Exception {

        when(roomService.getAvailableSeatsForRoom(1L))
                .thenReturn(List.of(new Seat(1L, true), new Seat(2L, true)));

        when(roomService.getAvailableSeatsForRoom(Mockito.longThat(longValue -> !longValue.equals(1L))))
                .thenReturn(new ArrayList<>());

        mockMvc.perform(get("/room/{id}/seats/available", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].available", is(true)))
                .andExpect(jsonPath("$[1].available", is(true)));

        mockMvc.perform(get("/room/{id}/seats/available", 2L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    public String toJson(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
