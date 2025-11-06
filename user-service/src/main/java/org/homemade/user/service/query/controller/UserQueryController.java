package org.homemade.user.service.query.controller;

import org.homemade.common.model.dto.UserResponseDTO;
import org.homemade.user.service.query.FindUserQuery;
import org.homemade.user.service.service.UserQueryService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class UserQueryController {

    private final UserQueryService userQueryService;

    public UserQueryController(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }


    @GetMapping()
    public ResponseEntity<UserResponseDTO> getUserByEmail(@RequestParam("email") String email) {

       UserResponseDTO result =  userQueryService.findUserQuery(email);

        return ResponseEntity.ok(result);
    }
}
