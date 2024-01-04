package fr.mybodydate.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.mybodydate.api.model.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    List<Like> findByReceiverUserId(Long userId);

}
