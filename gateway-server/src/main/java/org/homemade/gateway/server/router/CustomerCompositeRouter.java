package org.homemade.gateway.server.router;

import org.homemade.gateway.server.handler.CustomerCompositeHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;

@Configuration(proxyBeanMethods = false)
public class CustomerCompositeRouter {

    @Bean
    public RouterFunction<ServerResponse> route(CustomerCompositeHandler customerCompositeHandler) {
        return RouterFunctions.route(RequestPredicates.GET("/customers/summary")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON))
                        .and(RequestPredicates.queryParam("userId", param -> true)),
                customerCompositeHandler::getCustomerSummary);

    }
}
