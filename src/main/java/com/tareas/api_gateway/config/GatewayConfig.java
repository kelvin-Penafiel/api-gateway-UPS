package com.tareas.api_gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableHystrix
public class GatewayConfig {

    @Autowired
    private AuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder){
        return builder.routes()
                .route("auth-microservices", r -> r.path("/auth/login")
                        .filters(f -> f.filter(filter))
                        .uri("lb://AUTH-MICROSERVICES"))
                .route("user-microservices", r -> r.path("/api/usuarios/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://USER-MICROSERVICES"))
                .route("tareas-microservices", r -> r.path("/api/tareas/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://TAREAS-MICROSERVICES"))
                .build();

    }
}
