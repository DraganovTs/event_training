package org.homemade.product.service.controller;

import org.homemade.product.service.model.dto.OwnerDTO;
import org.homemade.product.service.service.OwnerService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/owners", produces = MediaType.APPLICATION_JSON_VALUE)
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }


    @PostMapping("/create")
    public ResponseEntity<OwnerDTO> createOwner(OwnerDTO request) {

        OwnerDTO response = ownerService.createOwner(request);
        return ResponseEntity.ok(response);
    }
}
