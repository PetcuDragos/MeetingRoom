package ro.dragos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "rooms")
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class Room {

    @Id
    private Long id;
    private String name;

    @OneToMany(mappedBy = "room", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Seat> seats;
}
