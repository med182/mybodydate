package fr.mybodydate.registelogin.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.mybodydate.registelogin.api.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmailAndPassword(String email, String password);

    User findByPhoneNumberAndPassword(String phoneNumber, String password);

    User findByEmailOrPhoneNumber(String email, String phoneNumber); // Ajout de deux paramètres distincts

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);
}
