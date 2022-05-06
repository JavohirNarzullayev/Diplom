package uz.narzullayev.javohir;/* 
 @author: Javohir
  Date: 5/6/2022
  Time: 1:55 PM*/


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class TelegramApp {
    public static void main(String[] args) {
        SpringApplication.run(TelegramApp.class, args);
    }
}
