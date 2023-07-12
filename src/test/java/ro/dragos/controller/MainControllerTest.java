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
import ro.dragos.repository.RoomRepository;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomRepository repository;

    @Test
    public void getHelloMessageTest() throws Exception {
        mockMvc.perform(get("/start"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("hello!")));
    }

    @Test
    public void getRoomsTest() throws Exception {

        when(repository.getRooms()).thenReturn(List.of(new Room(1L, "Room1", new ArrayList<>())));

        mockMvc.perform(get("/room"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Room1")));
    }

    @Test
    public void addRoomTest() throws Exception {

        RoomDto roomDto = new RoomDto(1L,"Room1", new ArrayList<>());

        when(repository.addRoom(Mockito.any(Room.class))).thenReturn(true);

        mockMvc.perform(post("/room")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(roomDto)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("true")));
    }

    @Test
    public void updateRoomTest() throws Exception {

        RoomDto roomDto = new RoomDto(1L,"Room1", new ArrayList<>());

        when(repository.updateRoom(Mockito.any(Long.class), Mockito.any(Room.class))).thenReturn(true);

        mockMvc.perform(put("/room/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(roomDto)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("true")));
    }

    @Test
    public void deleteRoomTest() throws Exception {

        when(repository.deleteRoom(1L)).thenReturn(true);
        when(repository.deleteRoom(Mockito.longThat((longValue)-> longValue != 1L))).thenReturn(false);

        mockMvc.perform(delete("/room/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("true")));


        mockMvc.perform(delete("/room/{id}", 2))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("false")));
    }

    public String toJson(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
