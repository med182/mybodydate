package fr.mybodydate.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.mybodydate.api.model.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

}
