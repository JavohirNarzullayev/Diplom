package uz.narzullayev.javohir.api;/* 
 @author: Javohir
  Date: 5/6/2022
  Time: 4:13 PM*/

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/telegram")
public class TelegramResource {

    @GetMapping("/send")
    public String send() {
        return "Telegram Hello World";
    }

}
