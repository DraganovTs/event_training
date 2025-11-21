package org.homemade.user.profile.service.repository;

import org.homemade.user.profile.service.model.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, UUID> {
    Optional<UserProfile> findUserProfileByUsernameAndEmail(String username, String email);

    Optional<UserProfile> findUserProfileByUsername(String username);
}
