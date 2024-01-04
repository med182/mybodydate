package fr.mybodydate.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.mybodydate.api.model.Subscription;
import fr.mybodydate.api.model.User;
import fr.mybodydate.api.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUserSubscription(Long userId, Subscription newSubscription) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setSubscription(newSubscription);
            return userRepository.save(user);
        }
        return null;
    }
}
