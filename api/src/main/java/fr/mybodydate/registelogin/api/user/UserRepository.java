package fr.mybodydate.registelogin.api.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmailAndPassword(String email, String password);

    User findByPhoneNumberAndPassword(String phoneNumber, String password);

    User findByEmailOrPhoneNumber(String username);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);
}
