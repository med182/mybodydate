package fr.mybodydate.registelogin.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import fr.mybodydate.registelogin.api.model.Message;
import fr.mybodydate.registelogin.api.repository.MessageRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message saveMessage(Message message) {
        Message savedMessage = messageRepository.save(message);
        messagingTemplate.convertAndSend("/topic/messages", savedMessage);
        return savedMessage;
    }

}
