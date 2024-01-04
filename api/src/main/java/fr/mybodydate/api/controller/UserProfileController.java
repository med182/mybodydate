package fr.mybodydate.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.mybodydate.api.model.UserProfile;
import fr.mybodydate.api.repository.UserProfileRepository;

@RestController
@RequestMapping("/api")
public class UserProfileController {

    @Autowired
    private UserProfileRepository UserProfileRepository;

    @PostMapping("/saveUserProfile")
    public UserProfile saveUserProfile(@RequestBody UserProfile userProfile) {
        return UserProfileRepository.save(userProfile);
    }
}
