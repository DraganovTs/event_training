package org.homemade.gateway.server.service.client;

import org.homemade.common.model.dto.ProductResponseDTO;
import org.homemade.common.model.dto.UserResponseDTO;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerSummaryClient {


    @GetExchange(value = "/users",accept = "application/json")
    Mono<UserResponseDTO> getUserDetails(@RequestParam ("userId") String userId);


    @GetExchange(value = "/products",accept = "application/json")
    Flux<ProductResponseDTO> getUserProducts(@RequestParam ("userId") String userId);
}
