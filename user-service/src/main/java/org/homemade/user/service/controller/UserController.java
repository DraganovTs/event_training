package org.homemade.user.service.controller;


import org.homemade.common.model.dto.UserResponseDTO;
import org.homemade.user.service.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }




//    @GetMapping()
//    public ResponseEntity<UserResponseDTO> getUser(@RequestParam("userId") UUID userId) {
//        UserResponseDTO result = userService.getUserById(userId);
//
//        return ResponseEntity.ok(result);
//    }
}
