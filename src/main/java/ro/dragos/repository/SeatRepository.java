package ro.dragos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.dragos.model.Seat;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
}
