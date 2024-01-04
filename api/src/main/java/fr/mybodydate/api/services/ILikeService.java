package fr.mybodydate.api.services;

import java.util.List;

import fr.mybodydate.api.model.Like;

public interface ILikeService {
    List<Like> getReceivedLikes(Long userId);

    void sendLike(Like like);
}
