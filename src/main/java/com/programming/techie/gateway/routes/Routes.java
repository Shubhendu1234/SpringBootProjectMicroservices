package com.programming.techie.gateway.routes;

import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;

@Configuration
public class Routes {

    @Bean
    public RouterFunction<ServerResponse> productServiceRoutes() {
        return route("product_service")
                .route(RequestPredicates.path("/api/product"), HandlerFunctions.http(
                        "http://localhost:8080"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("productServiceCircuitBreaker",
                        URI.create("forward:/fallBackRoute")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> productServiceSwaggerRoutes() {
        return route("product_service_swagger")
                .route(RequestPredicates.path("/aggregate/product-service/v3/api-docs"), HandlerFunctions.http(
                        "http://localhost:8080"))
                .filter(setPath("/api-docs"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("productServiceCircuitBreaker",
                        URI.create("forward:/fallBackRoute")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> orderServiceRoutes() {
        return route("order_service")
                .route(RequestPredicates.path("/api/order"), HandlerFunctions.http(
                        "http://localhost:8081"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("orderServiceCircuitBreaker",
                        URI.create("forward:/fallBackRoute")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> orderServiceSwaggerRoutes() {
        return route("order_service_swagger")
                .route(RequestPredicates.path("/aggregate/order-service/v3/api-docs"), HandlerFunctions.http(
                        "http://localhost:8081"))
                .filter(setPath("/api-docs"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("orderServiceCircuitBreaker",
                        URI.create("forward:/fallBackRoute")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> inventoryServiceRoutes() {
        return route("inventory_service")
                .route(RequestPredicates.path("/api/inventory"), HandlerFunctions.http(
                        "http://localhost:8082"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("inventoryServiceCircuitBreaker",
                        URI.create("forward:/fallBackRoute")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> inventoryServiceSwaggerRoutes() {
        return route("inventory_service_swagger")
                .route(RequestPredicates.path("/aggregate/inventory-service/v3/api-docs"), HandlerFunctions.http(
                        "http://localhost:8082"))
                .filter(setPath("/api-docs"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("inventoryServiceCircuitBreaker",
                        URI.create("forward:/fallBackRoute")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> fallbackRoutes() {
        return route(RequestPredicates.GET("/fallBackRoute"), request -> {
            System.out.println("Fallback route triggered");
            return ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(Mono.just("Service Unavailable, please try again"));
        });
    }
}
