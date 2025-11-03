package org.homemade.gateway.server.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutesConfiguration {

    @Bean
    public RouteLocator eventTrainingRouteConfig(RouteLocatorBuilder routeLocatorBuilder){
        return routeLocatorBuilder.routes()
                .route("product-service", r -> r.path("/products/**")
                        .uri("lb://PRODUCT-SERVICE"))
                .route("user-service", r -> r.path("/users/**")
                        .uri("lb://USER-SERVICE"))
                .route("email-service", r -> r.path("/emails/**")
                        .uri("lb://EMAIL-SERVICE"))
                .build();
    }
}
