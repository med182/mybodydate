package fr.mybodydate.registelogin.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.mybodydate.registelogin.api.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

}