package fr.mybodydate.api.repository;

import fr.mybodydate.api.model.Match;
import fr.mybodydate.api.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface MatchRepository extends JpaRepository<Match, Long> {

    @Query("SELECT u FROM User u JOIN u.matches m WHERE m.id = :matchId")
    Set<User> findUsersByMatchId(@Param("matchId") Long matchId);
}