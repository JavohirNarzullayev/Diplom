package uz.narzullayev.javohir;/* 
 @author: Javohir
  Date: 5/8/2022
  Time: 7:51 PM*/

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
public class APIGatewayApp {

    public static void main(String[] args) {
        SpringApplication.run(APIGatewayApp.class, args);
    }
}
