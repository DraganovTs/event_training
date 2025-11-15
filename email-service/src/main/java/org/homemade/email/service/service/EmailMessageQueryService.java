package org.homemade.email.service.service;

import org.homemade.email.service.model.dto.EmailMessageResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailMessageQueryService {
    public EmailMessageResponseDTO findEmailMessageQuery(String recipient, String subject) {
        return null;
    }

    public List<EmailMessageResponseDTO> findAllEmailMessages() {
        return null;
    }
}
