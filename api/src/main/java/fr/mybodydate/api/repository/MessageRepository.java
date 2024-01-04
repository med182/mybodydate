package fr.mybodydate.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.mybodydate.api.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
