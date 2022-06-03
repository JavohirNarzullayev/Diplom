package uz.narzullayev.javohir.conf;/* 
 @author: Javohir
  Date: 5/9/2022
  Time: 10:23 AM*/

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutesConf {

    //Cloud Gateway Configuration for routes


    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("TELEGRAM-SERVICE",
                        r -> r.path("/api/v1/telegram/**")
                                .filters(gatewayFilterSpec -> gatewayFilterSpec.circuitBreaker(
                                        config -> config.setName("Hystrix").setFallbackUri("forward:/fallback-telegram")))
                                .uri("lb://TELEGRAM-SERVICE")

                )
                .route("EMAIL-SERVICE",
                        r -> r.path("/api/v1/email/**")
                                .filters(gatewayFilterSpec -> gatewayFilterSpec.circuitBreaker(
                                        config -> config.setName("Hystrix").setFallbackUri("forward:/fallback-email")))
                                .uri("lb://EMAIL-SERVICE"))
                .build();
    }
}
