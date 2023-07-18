package ro.dragos.repository;

import org.springframework.stereotype.Repository;
import ro.dragos.model.Seat;

@Repository()
public class SeatRepositoryInMemory extends CrudRepositoryInMemory<Seat, Long> implements SeatRepository {
}
