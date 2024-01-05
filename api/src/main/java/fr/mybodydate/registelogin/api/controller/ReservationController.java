package fr.mybodydate.registelogin.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.mybodydate.registelogin.api.model.Event;
import fr.mybodydate.registelogin.api.model.Reservation;
import fr.mybodydate.registelogin.api.model.User;
import fr.mybodydate.registelogin.api.services.ReservationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/user")
    public ResponseEntity<List<Reservation>> getUserReservation(@AuthenticationPrincipal User user) {
        List<Reservation> reservations = reservationService.getUserReservations(user);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @PostMapping("/makeReservation/{eventId}")
    public ResponseEntity<String> makeReservation(@AuthenticationPrincipal User user,
            @PathVariable Long eventId,
            @RequestBody String additionalInfo) {

        Event event = reservationService.getEventById(eventId);

        if (event == null) {
            return new ResponseEntity<>("L'événement spécifié n'existe pas", HttpStatus.OK);
        }

        if (reservationService.hasUserReservedEvent(user, event)) {
            return new ResponseEntity<>("Vous avez déjà réservé cet événement", HttpStatus.OK);
        }

        reservationService.makeReservation(user, event, additionalInfo);
        return new ResponseEntity<>("Vous avez déjà réservé cet événement", HttpStatus.OK);

    }

}
