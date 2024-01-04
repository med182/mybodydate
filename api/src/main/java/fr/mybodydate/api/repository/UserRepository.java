package fr.mybodydate.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.mybodydate.api.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmailAndPassword(String email, String password);

    User findByPhoneNumberAndPassword(String phoneNumber, String password);

    User findByEmail(String email);
    
    User findByPhoneNumber(String phoneNumber);
    
    User findByEmailOrPhoneNumber(String email, String phoneNumber);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);
}
