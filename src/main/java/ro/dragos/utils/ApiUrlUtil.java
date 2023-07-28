package ro.dragos.utils;

public class ApiUrlUtil {

    public static final String GET_HELLO_MESSAGE = "/start";
    public static final String ADD_ROOM = "/room";
    public static final String UPDATE_ROOM = "/room/{id}";
    public static final String DELETE_ROOM = "/room/{id}";
    public static final String GET_ROOMS = "/room";
    public static final String ADD_SEAT = "/room/{id}/seats";
    public static final String UPDATE_SEAT = "/room/seats/{id}";
    public static final String DELETE_SEAT = "/room/seats/{id}";
    public static final String GET_SEATS = "/room/seats";
    public static final String GET_AVAILABLE_SEATS = "/room/{id}/seats/available";

}
