package fr.mybodydate.registelogin.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledTasks {

    @Autowired
    private ChatCleanupService chatCleanupService;

    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    public void cleanupOldChats() {
        chatCleanupService.cleanOldchats();
    }
}
