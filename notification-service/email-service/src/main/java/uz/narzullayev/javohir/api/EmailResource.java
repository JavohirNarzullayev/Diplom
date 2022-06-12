package uz.narzullayev.javohir.api;/* 
 @author: Javohir
  Date: 5/6/2022
  Time: 4:11 PM*/

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.narzullayev.javohir.service.EmailService;

@Slf4j
@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
public class EmailResource {

    private final EmailService emailService;


    @GetMapping(value = "/send", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public String sendEmail() {
        emailService.send("narzullayevj999@gmail.com", "Blyyaaaaaaa");
        return "Email sent";
    }

}
