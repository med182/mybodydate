package fr.mybodydate.registelogin.api.controller;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.mybodydate.registelogin.api.services.MatchService;

@RestController
@RequestMapping("/api/matches")
public class MatchController {

    @Autowired
    private MatchService matchService;

    @PostMapping("/createMatch")
    public ResponseEntity<?> createMatch(@RequestBody Map<String, Integer> request) {

        Integer userId = request.get("userId");
        Integer targetUserId = request.get("targetUserId");
        return matchService.requestMatch(userId, targetUserId);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserMatches(@PathVariable Integer userId) {
        return matchService.getMatchesByUserId(userId);
    }

    @GetMapping("/{matchId}")
    public ResponseEntity<?> getMatchById(@PathVariable Long matchId) {
        return matchService.getMatchById(matchId);
    }

    @PutMapping("/update/{matchId}")
    public ResponseEntity<?> updateMatch(@PathVariable Long matchId, @RequestBody Map<String, Integer> request) {
        Integer userId = request.get("userId");
        Integer targetUserId = request.get("targetUserId");
        return matchService.updateMatch(matchId, userId, targetUserId);
    }

    @DeleteMapping("/delete/{matchId}")
    public ResponseEntity<?> deleteMatch(@PathVariable Long matchId) {
        return matchService.deleteMatch(matchId);
    }
}
