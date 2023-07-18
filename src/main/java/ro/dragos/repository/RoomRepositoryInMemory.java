package ro.dragos.repository;

import org.springframework.stereotype.Repository;
import ro.dragos.model.Room;

@Repository()
public class RoomRepositoryInMemory extends CrudRepositoryInMemory<Room, Long> implements RoomRepository {
}
