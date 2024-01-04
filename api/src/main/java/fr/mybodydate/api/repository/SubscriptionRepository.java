package fr.mybodydate.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.mybodydate.api.model.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

}
