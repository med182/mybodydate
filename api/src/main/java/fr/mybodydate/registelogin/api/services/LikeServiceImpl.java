package fr.mybodydate.registelogin.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.mybodydate.registelogin.api.model.Like;
import fr.mybodydate.registelogin.api.repository.ILikeRepository;

@Service
public class LikeServiceImpl implements ILikeService {

    @Autowired
    private ILikeRepository likeRepository;

    @Override
    public List<Like> getReceivedLikes(Long userId) {

        return likeRepository.findByReceiverUserId(userId);

    }

    @Override
    public void sendLike(Like like) {
        likeRepository.save(like);
    }

}
