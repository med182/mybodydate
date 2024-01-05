package fr.mybodydate.registelogin.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.mybodydate.registelogin.api.model.Event;
import fr.mybodydate.registelogin.api.model.Reservation;
import fr.mybodydate.registelogin.api.model.User;
import fr.mybodydate.registelogin.api.repository.IEventRepository;
import fr.mybodydate.registelogin.api.repository.IReservationRepository;

@Service
public class ReservationService {

    private IReservationRepository reservationRepository;
    private IEventRepository eventRepository;

    public ReservationService(IReservationRepository reservationRepository, IEventRepository eventRepository) {
        this.reservationRepository = reservationRepository;
        this.eventRepository = eventRepository;
    }

    @Transactional(readOnly = true)
    public Event getEventById(Long eventId) {
        return eventRepository.findById(eventId).orElse(null);
    }

    @Transactional(readOnly = true)
    public java.util.List<Reservation> getUserReservations(User user) {
        return reservationRepository.findByUser(user);
    }

    @Transactional(readOnly = true)
    public boolean hasUserReservedEvent(User user, Event event) {
        return reservationRepository.existsByUserAndEvent(user, event);
    }

    @Transactional
    public void makeReservation(User user, Event event, String additionalInfo) {

        if (user == null || event == null || additionalInfo == null) {
            throw new IllegalArgumentException(
                    "L'utilisateur, l'evenement et les informations supplémentaires ne peuvent pas etre null ");
        }

        Reservation reservation = new Reservation(user, event, additionalInfo);
        reservationRepository.save(reservation);
    }

}
