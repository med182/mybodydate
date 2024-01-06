package fr.mybodydate.registelogin.api.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fr.mybodydate.registelogin.api.model.Subscription;
import fr.mybodydate.registelogin.api.model.User;
import fr.mybodydate.registelogin.api.repository.IUserRepository;

@Service
public class UserService {

    @Autowired
    private IUserRepository userRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String startUserRegistration(String email, String phoneNumber) {
        String temporaryUserId = generateTemporaryUserId();

        User temporaryUser = new User();
        temporaryUser.setTemporaryUserId(temporaryUserId);
        temporaryUser.setEmail(email);
        temporaryUser.setPhoneNumber(phoneNumber);

        userRepository.save(temporaryUser);

        return temporaryUserId;
    }

    private String generateTemporaryUserId() {
        return UUID.randomUUID().toString();
    }

    public User getUserById(Integer userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUserSubscription(Integer userId, Subscription newSubscription) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setSubscription(newSubscription);
            return userRepository.save(user);
        }
        return null;
    }
}
