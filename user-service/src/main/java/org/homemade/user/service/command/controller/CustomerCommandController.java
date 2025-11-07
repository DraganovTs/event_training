package org.homemade.user.service.command.controller;

import jakarta.validation.Valid;
import org.homemade.user.service.model.dto.ResponseDTO;
import org.homemade.user.service.model.dto.UserRequestDTO;
import org.homemade.user.service.service.UserCommandService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class CustomerCommandController {

    private final UserCommandService userCommandService;

    public CustomerCommandController(UserCommandService userCommandService) {
        this.userCommandService = userCommandService;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createUser(@Valid @RequestBody UserRequestDTO request) {

        userCommandService.createUser(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseDTO.builder()
                        .status("Created")
                        .message("User created successfully")
                        .build());
    }


    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateUser(@Valid @RequestBody UserRequestDTO request) {

        userCommandService.updateUser(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDTO.builder()
                        .status("Updated")
                        .message("User updated successfully")
                        .build());
    }


    @PatchMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteUser(@RequestParam("userId") UUID userId) {

        userCommandService.deleteUser(userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDTO.builder()
                        .status("Deleted")
                        .message("User deleted successfully")
                        .build());
    }
}
