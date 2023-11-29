package fr.mybodydaye.registelogin.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.mybodydaye.registelogin.api.like.Like;
import fr.mybodydaye.registelogin.api.like.LikeRepository;

@Service
public class LikeServiceImpl implements ILikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Override
    public List<Like> getReceivedLikes(Long userId) {

        return likeRepository.findByReceiverUserId(userId);

    }

    @Override
    public void sendLike(Like like) {
        likeRepository.save(like);
    }

}
