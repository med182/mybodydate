package fr.mybodydate.registelogin.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.mybodydate.registelogin.api.model.Subscription;
import fr.mybodydate.registelogin.api.services.SubscriptionService;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping
    private List<Subscription> getAllSubscriptions() {

        return subscriptionService.getAllSubscriptions();
    }

    @GetMapping("/{subscriptionId}")
    public Subscription getSubscriptionById(@PathVariable Long subscriptionId) {
        return subscriptionService.getSubscriptionById(subscriptionId);
    }

    @PostMapping
    public Subscription createSubscription(@RequestBody Subscription subscription) {
        return subscriptionService.createSubscription(subscription);
    }

}
