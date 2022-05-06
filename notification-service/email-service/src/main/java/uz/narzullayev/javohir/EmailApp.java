package uz.narzullayev.javohir;/* 
 @author: Javohir
  Date: 5/6/2022
  Time: 1:53 PM*/

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EmailApp {

    public static void main(String[] args) {
        SpringApplication.run(EmailApp.class, args);
    }
}
