package org.homemade.email.service.service;

import jakarta.validation.Valid;
import org.homemade.email.service.model.dto.EmailRequestDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmailMessageCommandService {


    public void createEmailMessage(@Valid EmailRequestDTO request) {

    }

    public void updateEmailMessage(@Valid EmailRequestDTO request) {

    }

    public void deleteEmailMessage(UUID emailMessageId) {

    }
}
