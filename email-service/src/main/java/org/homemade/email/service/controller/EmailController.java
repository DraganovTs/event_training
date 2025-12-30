package org.homemade.email.service.controller;

import org.homemade.common.model.dto.ResponseDTO;
import org.homemade.email.service.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/email",produces = MediaType.APPLICATION_JSON_VALUE)
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public ResponseEntity<ResponseDTO> sendEmail(@RequestBody Object emailRequest) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(ResponseDTO.builder()
                        .status("Sent")
                        .message("Email processing started")
                        .build());
    }

}
