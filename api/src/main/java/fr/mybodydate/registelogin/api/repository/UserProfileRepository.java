package fr.mybodydate.registelogin.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.mybodydate.registelogin.api.model.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

}
