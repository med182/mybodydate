package fr.mybodydate.registelogin.api.services;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import fr.mybodydate.registelogin.api.model.Match;
import fr.mybodydate.registelogin.api.model.User;
import fr.mybodydate.registelogin.api.repository.IMatchRepository;
import fr.mybodydate.registelogin.api.repository.IUserRepository;

@Service
public class MatchService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IMatchRepository matchRepository;

    public ResponseEntity<?> requestMatch(Integer userId, Integer targetUserId) {
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

            if (user.getBlockedContacts().contains(targetUser)) {
                return new ResponseEntity<>("L'utilisateur est bloqué", HttpStatus.BAD_REQUEST);
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

    public ResponseEntity<?> getMatchesByUserId(Integer userId) {
        Set<User> users = matchRepository.findUsersByMatchId(userId);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    public ResponseEntity<?> updateMatch(Long matchId, Integer userId, Integer targetUserId) {
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

    public ResponseEntity<?> addToBlacklist(Integer userId, Long matchId) {
        User user = userRepository.findById(userId).orElse(null);
        Match match = matchRepository.findById(matchId).orElse(null);

        if (user != null && match != null) {
            if (!user.getBlacklistedMatches().contains(match)) {
                user.getBlacklistedMatches().contains(match);
                userRepository.save(user);
                return new ResponseEntity<>("Match ajouté à la liste noire avec succès. ", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Le match est déjà dans la liste noire.", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Utilisateur ou match non trouvé.", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> removeFromBlacklist(Integer userId, Long matchId) {
        User user = userRepository.findById(userId).orElse(null);
        Match match = matchRepository.findById(matchId).orElse(null);

        if (user != null && match != null) {
            if (user.getBlacklistedMatches().contains(match)) {
                user.getBlacklistedMatches().remove(match);
                return new ResponseEntity<>("Match supprimé de la liste noire avec succès.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Le match n'est pas dans la liste noire", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Utilisateur ou match non trouvé.", HttpStatus.NOT_FOUND);
        }
    }

}
