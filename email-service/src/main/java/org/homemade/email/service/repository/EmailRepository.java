package org.homemade.email.service.repository;

import org.homemade.email.service.model.entity.EmailMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmailRepository extends JpaRepository<EmailMessage, UUID> {

    Optional<EmailMessage> findByRecipientAndSubject(String recipient, String subject);
}
