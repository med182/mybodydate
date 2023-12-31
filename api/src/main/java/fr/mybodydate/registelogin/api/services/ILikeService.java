package fr.mybodydate.registelogin.api.services;

import java.util.List;

import fr.mybodydate.registelogin.api.model.Like;

public interface ILikeService {
    List<Like> getReceivedLikes(Long userId);

    void sendLike(Like like);
}
