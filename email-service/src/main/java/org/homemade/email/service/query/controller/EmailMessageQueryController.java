package org.homemade.email.service.query.controller;

import org.homemade.email.service.model.dto.EmailMessageResponseDTO;
import org.homemade.email.service.service.EmailMessageQueryService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/email",produces = MediaType.APPLICATION_JSON_VALUE)
public class EmailMessageQueryController {

    private final EmailMessageQueryService emailMessageQueryService;

    public EmailMessageQueryController(EmailMessageQueryService emailMessageQueryService) {
        this.emailMessageQueryService = emailMessageQueryService;
    }

    @GetMapping("/emailByRecipientAndSubject")
    public ResponseEntity<EmailMessageResponseDTO> getEmailMessageByRecipientAndSubject(@RequestParam String recipient,
                                                                                       @RequestParam String subject){
        EmailMessageResponseDTO response = emailMessageQueryService.findEmailMessageQuery(recipient, subject);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<EmailMessageResponseDTO>> getAllEmailMessages(){
        List<EmailMessageResponseDTO> response = emailMessageQueryService.findAllEmailMessages();

        return ResponseEntity.ok(response);
    }
}
