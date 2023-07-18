package ro.dragos.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ro.dragos.model.Room;

@NoRepositoryBean
public interface RoomRepository extends CrudRepository<Room, Long> {
}
