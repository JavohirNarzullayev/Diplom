package uz.narzullayev.javohir.resourse;/*
 @author: Javohir
  Date: 5/9/2022
  Time: 11:04 PM*/

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallBackController {

    @GetMapping("/telegram-fallback")
    public String fallback() {
        return "Telegram fallback";
    }

    @GetMapping("/email-fallback")
    public String fallback2() {
        return "Email fallback2";
    }

}
