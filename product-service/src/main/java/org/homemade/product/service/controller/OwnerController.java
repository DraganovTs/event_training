package org.homemade.product.service.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/owners", produces = MediaType.APPLICATION_JSON_VALUE)
public class OwnerController {
}
