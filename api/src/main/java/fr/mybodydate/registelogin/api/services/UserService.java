package fr.mybodydate.registelogin.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.mybodydate.registelogin.api.model.Subscription;
import fr.mybodydate.registelogin.api.model.User;
import fr.mybodydate.registelogin.api.repository.IUserRepository;

@Service
public class UserService {

    @Autowired
    private IUserRepository userRepository;

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
