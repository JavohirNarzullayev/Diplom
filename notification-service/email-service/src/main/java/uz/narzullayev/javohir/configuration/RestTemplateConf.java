package uz.narzullayev.javohir.configuration;/* 
 @author: Javohir
  Date: 5/8/2022
  Time: 2:06 PM*/

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestTemplateConf {

    @Bean
    @LoadBalanced
    public org.springframework.web.client.RestTemplate restTemplate() {
        return new org.springframework.web.client.RestTemplate();
    }

}
