package org.homemade.email.service.repository;

import org.homemade.email.service.model.entity.EmailUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmailUserRepository extends JpaRepository<EmailUser, UUID> {

    Optional<EmailUser> findByOwnerNameAndOwnerEmail(String ownerName, String ownerEmail);
}
