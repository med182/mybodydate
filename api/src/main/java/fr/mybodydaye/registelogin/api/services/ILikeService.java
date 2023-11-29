package fr.mybodydaye.registelogin.api.services;

import java.util.List;

import fr.mybodydaye.registelogin.api.like.Like;

public interface ILikeService {
    List<Like> getReceivedLikes(Long userId);

    void sendLike(Like like);
}
