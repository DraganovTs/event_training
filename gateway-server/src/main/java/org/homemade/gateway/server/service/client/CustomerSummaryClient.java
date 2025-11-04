package org.homemade.gateway.server.service.client;

import org.homemade.gateway.server.model.dto.ProductDTO;
import org.homemade.gateway.server.model.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerSummaryClient {


    @GetExchange(value = "/user/{userId}",accept = "application/json")
    Mono<ResponseEntity<UserDTO>> getUserDetails(@RequestParam String userId);


    @GetExchange(value = "/user/{userId}",accept = "application/json")
    Flux<ResponseEntity<ProductDTO>> getUserProducts(@RequestParam String userId);
}
