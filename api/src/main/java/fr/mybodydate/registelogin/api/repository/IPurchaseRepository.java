package fr.mybodydate.registelogin.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.mybodydate.registelogin.api.model.Purchase;

public interface IPurchaseRepository extends JpaRepository<Purchase, Long> {

}
