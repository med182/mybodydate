package fr.mybodydate.registelogin.api.like;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.mybodydate.registelogin.api.services.LikeServiceImpl;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    @Autowired
    private LikeServiceImpl likeService;

    @GetMapping("/received/{userId}")
    public ResponseEntity<List<Like>> getLikesByUserId(@PathVariable Long userId) {
        List<Like> receivedLikes = likeService.getReceivedLikes(userId);
        return ResponseEntity.ok(receivedLikes);
    }

    @PostMapping("/send")
    public ResponseEntity<Void> sendLike(@RequestBody Like like) {
        likeService.sendLike(like);
        return ResponseEntity.ok().build();
    }

}
