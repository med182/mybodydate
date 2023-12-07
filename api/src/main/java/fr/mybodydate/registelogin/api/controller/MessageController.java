package fr.mybodydate.registelogin.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.mybodydate.registelogin.api.model.Message;
import fr.mybodydate.registelogin.api.services.MessageService;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message savedMessage = messageService.saveMessage(message);
        return new ResponseEntity<>(savedMessage, HttpStatus.CREATED);
    }

}
