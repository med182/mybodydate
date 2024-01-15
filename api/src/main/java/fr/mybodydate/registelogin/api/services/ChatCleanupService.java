package fr.mybodydate.registelogin.api.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import fr.mybodydate.registelogin.api.model.Chat;
import fr.mybodydate.registelogin.api.repository.IChatRepository;

@Service
public class ChatCleanupService {

    private SimpMessagingTemplate chatTemplate;

    @Autowired
    private IChatRepository chatRepository;

    public void cleanOldchats() {
        LocalDateTime threshold = LocalDateTime.now().minusDays(60);
        List<Chat> oldChats = chatRepository.findByTimestampBefore(threshold);

        for (Chat chat : oldChats) {
            chatTemplate.convertAndSendToUser(chat.getSender(), "/queue/notification",
                    "Votre message a été supprimé après 60 jours.");
        }

        chatRepository.deleteAll(oldChats);

    }

}
