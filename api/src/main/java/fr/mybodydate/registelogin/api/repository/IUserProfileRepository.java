package fr.mybodydate.registelogin.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.mybodydate.registelogin.api.model.UserProfile;

public interface IUserProfileRepository extends JpaRepository<UserProfile, Long> {

}
