package fr.mybodydate.registelogin.api.webSocketChat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import fr.mybodydate.registelogin.api.model.Chat;

import fr.mybodydate.registelogin.api.services.ChatCleanupService;

@Controller
public class WebSocketChatController {

    private ChatCleanupService chatCleanupService;

    @Autowired
    private SimpMessagingTemplate chatTemplate;

    @MessageMapping("/sendMessage")
    public void sendMessage(@Payload Chat chat) {
        chatTemplate.convertAndSend("/topic/messages", chat);

        chatCleanupService.cleanOldchats();
    }

}
