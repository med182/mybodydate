package fr.mybodydaye.registelogin.api.like;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    List<Like> findByReceiverUserId(Long userId);

}
