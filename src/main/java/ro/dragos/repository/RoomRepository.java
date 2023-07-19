package ro.dragos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.dragos.model.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
}
