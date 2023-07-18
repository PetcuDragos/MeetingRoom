package ro.dragos.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ro.dragos.model.Seat;

@NoRepositoryBean
public interface SeatRepository extends CrudRepository<Seat, Long> {
}
