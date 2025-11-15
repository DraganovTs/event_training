package org.homemade.email.service.command.controller;

import jakarta.validation.Valid;
import org.homemade.common.model.dto.ResponseDTO;
import org.homemade.email.service.model.dto.EmailRequestDTO;
import org.homemade.email.service.service.EmailMessageCommandService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping( value = "/emails" , produces = MediaType.APPLICATION_JSON_VALUE)
public class EmailMessageCommandController {

    private final EmailMessageCommandService emailMessageCommandService;

    public EmailMessageCommandController(EmailMessageCommandService emailMessageCommandService) {
        this.emailMessageCommandService = emailMessageCommandService;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createEmailMessage(@Valid @RequestBody EmailRequestDTO request) {

        emailMessageCommandService.createEmailMessage(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseDTO.builder()
                        .status("Created")
                        .message("Email message created successfully")
                        .build());
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateEmailMessage(@Valid @RequestBody EmailRequestDTO request){

        emailMessageCommandService.updateEmailMessage(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDTO.builder()
                        .status("Updated")
                        .message("Email message updated successfully")
                        .build());
    }

    @PatchMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteEmailMessage(@RequestParam("emailMessageId")UUID emailMessageId){
        emailMessageCommandService.deleteEmailMessage(emailMessageId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDTO.builder()
                        .status("Deleted")
                        .message("Email message deleted successfully")
                        .build());
    }
}
