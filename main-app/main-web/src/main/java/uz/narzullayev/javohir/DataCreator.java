package uz.narzullayev.javohir;/* 
 @author: Javohir
  Date: 4/3/2022
  Time: 2:29 PM*/


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import uz.narzullayev.javohir.constant.UserType;
import uz.narzullayev.javohir.dto.UserDto;
import uz.narzullayev.javohir.service.UserService;

import java.io.File;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataCreator {
    private final UserService userService;
    private final AppProperties appProperties;

    @EventListener
    public void ctxRefreshed(ContextRefreshedEvent evt) {
        initData();
    }

    public void initData() {
        log.info("Context Refreshed !!, Initializing environment (db, folders etc)... ");

        File uploadFolder = new File(appProperties.getFileStorage().getUploadFolder());
        if (!uploadFolder.exists()) {
            if (uploadFolder.mkdirs()) log.info("Upload folder created successfully");
            else log.info("Failure to create upload folder");
        }

        String fromEmail = appProperties.getEmail().getAuthorNotificationsFromEmail();
        String fromName = appProperties.getEmail().getAuthorNotificationsFromName();
        if (!userService.isUserAlreadyPresent("admin")) {
            log.info("DB already initialized !!!");
            var admin = userService.findByUsername("admin");
            admin.setRole(UserType.ADMIN);
            userService.update(new UserDto(admin));
            return;
        }
        UserDto userDto = new UserDto();
        userDto.setUsername("admin");
        userDto.setUserType(UserType.ADMIN);
        userDto.setPassword("12345");
        userDto.setConfirmPassword("12345");
        userDto.setFio(fromName);
        userDto.setEmail(fromEmail);
        userDto.setId(1L);
        userDto.setEnabled(true);
        userDto.setPhone("+9998999999");
        userService.save(userDto);


    }
}


