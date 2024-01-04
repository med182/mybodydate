package fr.mybodydate.api.services;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import fr.mybodydate.api.model.Match;
import fr.mybodydate.api.model.User;
import fr.mybodydate.api.repository.MatchRepository;
import fr.mybodydate.api.repository.UserRepository;

@Service
public class MatchService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MatchRepository matchRepository;

    public ResponseEntity<?> requestMatch(Long userId, Long targetUserId) {
        try {
            User user = userRepository.findById(userId).orElse(null);
            User targetUser = userRepository.findById(targetUserId).orElse(null);

            if (user == null || targetUser == null) {
                return new ResponseEntity<>("Utilisateur non trouvé.", HttpStatus.NOT_FOUND);
            }

            // Check if the users are trying to match with themselves
            if (user.getId().equals(targetUser.getId())) {
                return new ResponseEntity<>("Impossible de se matcher avec soi-même.", HttpStatus.BAD_REQUEST);
            }

            // Check if the users are already matched
            Set<User> existingMatches = matchRepository.findUsersByMatchId(user.getId());
            if (existingMatches.contains(targetUser)) {
                return new ResponseEntity<>("Les utilisateurs sont déjà matchés.", HttpStatus.BAD_REQUEST);
            }

            // Create a match between users
            Match match = new Match();
            match.getUsers().add(user);
            match.getUsers().add(targetUser);

            matchRepository.save(match);

            return new ResponseEntity<>("Match créé avec succès.", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Erreur lors de la création du match.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getMatchById(Long matchId) {
        Optional<Match> match = matchRepository.findById(matchId);
        return match.<ResponseEntity<?>>map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElse(new ResponseEntity<>("Match non trouvé.", HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<?> getMatchesByUserId(Long userId) {
        Set<User> users = matchRepository.findUsersByMatchId(userId);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    public ResponseEntity<?> updateMatch(Long matchId, Long userId, Long targetUserId) {
        Optional<Match> optionalMatch = matchRepository.findById(matchId);

        if (optionalMatch.isPresent()) {
            Match match = optionalMatch.get();

            // Update users in the match
            match.getUsers().clear();
            User user = userRepository.findById(userId).orElse(null);
            User targetUser = userRepository.findById(targetUserId).orElse(null);

            if (user == null || targetUser == null) {
                return new ResponseEntity<>("Utilisateur non trouvé.", HttpStatus.NOT_FOUND);
            }

            // Check if the users are trying to match with themselves
            if (user.getId().equals(targetUser.getId())) {
                return new ResponseEntity<>("Impossible de se matcher avec soi-même.", HttpStatus.BAD_REQUEST);
            }

            // Check if the users are already matched
            Set<User> existingMatches = matchRepository.findUsersByMatchId(user.getId());
            if (existingMatches.contains(targetUser)) {
                return new ResponseEntity<>("Les utilisateurs sont déjà matchés.", HttpStatus.BAD_REQUEST);
            }

            match.getUsers().add(user);
            match.getUsers().add(targetUser);

            matchRepository.save(match);

            return new ResponseEntity<>("Match mis à jour avec succès.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Match non trouvé.", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> deleteMatch(Long matchId) {
        Optional<Match> match = matchRepository.findById(matchId);
        if (match.isPresent()) {
            matchRepository.delete(match.get());
            return new ResponseEntity<>("Match supprimé avec succès.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Match non trouvé.", HttpStatus.NOT_FOUND);
        }
    }

    // Add other match-related methods if needed

}
