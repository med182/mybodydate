package fr.mybodydate.registelogin.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.mybodydate.registelogin.api.model.Event;
import fr.mybodydate.registelogin.api.model.Reservation;
import fr.mybodydate.registelogin.api.model.User;

public interface IReservationRepository extends JpaRepository<Reservation, Long>

{
    List<Reservation> findByUser(User user);

    boolean existsByUserAndEvent(User user, Event event);

}
