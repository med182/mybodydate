package fr.mybodydate.registelogin.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.mybodydate.registelogin.api.model.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

}
