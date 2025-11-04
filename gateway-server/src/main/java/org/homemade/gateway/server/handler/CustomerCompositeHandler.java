package org.homemade.gateway.server.handler;

import lombok.RequiredArgsConstructor;
import org.homemade.common.model.dto.ProductResponseDTO;
import org.homemade.common.model.dto.UserResponseDTO;
import org.homemade.gateway.server.model.dto.SummaryDTO;
import org.homemade.gateway.server.service.client.CustomerSummaryClient;
import org.springframework.http.MediaType;
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

        Mono<UserResponseDTO> userDetails = customerSummaryClient.getUserDetails(userId);
        Flux<ProductResponseDTO> userProductsFlux = customerSummaryClient.getUserProducts(userId);



        return Mono.zip(userDetails, userProductsFlux.collectList())
                .flatMap(tuple -> {
                    UserResponseDTO userDTO = tuple.getT1();
                    List<ProductResponseDTO> products = tuple.getT2();

                    SummaryDTO summaryDTO = new SummaryDTO(userDTO, products);

                    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(summaryDTO);
                });
    }
}
