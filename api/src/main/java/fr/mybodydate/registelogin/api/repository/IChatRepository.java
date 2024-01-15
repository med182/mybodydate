package fr.mybodydate.registelogin.api.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.mybodydate.registelogin.api.model.Chat;

public interface IChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findByTimestampBefore(LocalDateTime timestamp);
}
