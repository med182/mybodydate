package fr.mybodydate.registelogin.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.mybodydate.registelogin.api.repository.ISubscriptionRepository;
import fr.mybodydate.registelogin.api.model.Subscription;

@Service
public class SubscriptionService {

    @Autowired
    private ISubscriptionRepository subscriptionRepository;

    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    public Subscription getSubscriptionById(Long subscriptionId) {
        return subscriptionRepository.findById(subscriptionId).orElse(null);
    }

    public Subscription createSubscription(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }

}
