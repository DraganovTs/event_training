package org.homemade.gateway.server.handler;

import lombok.RequiredArgsConstructor;
import org.homemade.gateway.server.model.dto.ProductDTO;
import org.homemade.gateway.server.model.dto.SummaryDTO;
import org.homemade.gateway.server.model.dto.UserDTO;
import org.homemade.gateway.server.service.client.CustomerSummaryClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomerCompositeHandler {

    private final CustomerSummaryClient customerSummaryClient;

    public Mono<ServerResponse> getCustomerSummary(ServerRequest serverRequest) {
        String userId = serverRequest.queryParam("userId").orElseThrow(() -> new IllegalArgumentException("userId is required"));

        Mono<ResponseEntity<UserDTO>> userDetails = customerSummaryClient.getUserDetails(userId);
        Flux<ResponseEntity<ProductDTO>> userProductsFlux = customerSummaryClient.getUserProducts(userId);

        Mono<List<ProductDTO>> userProducts = userProductsFlux
                .map(ResponseEntity::getBody)
                .collectList();

        return Mono.zip(userDetails, userProducts)
                .flatMap(tuple -> {
                    UserDTO userDTO = tuple.getT1().getBody();
                    List<ProductDTO> products = tuple.getT2();
                    SummaryDTO summaryDTO = new SummaryDTO(userDTO, products);
                    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(summaryDTO);
                });
    }
}
