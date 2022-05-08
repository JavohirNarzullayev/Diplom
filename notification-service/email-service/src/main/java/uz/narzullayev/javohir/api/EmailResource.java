package uz.narzullayev.javohir.api;/* 
 @author: Javohir
  Date: 5/6/2022
  Time: 4:11 PM*/

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/v1/email")
@Slf4j
public class EmailResource {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/send")
    public String sendEmail() {
        String forObject = restTemplate.getForObject("http://TELEGRAM-SERVICE/api/v1/telegram/send", String.class);
        log.warn("Telegram sent:-> {}", forObject);
        return "Email sent";
    }

}
