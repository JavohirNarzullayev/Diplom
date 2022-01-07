package uz.narzullayev.javohir.service.impl;/* 
 @author: Javohir
  Date: 1/7/2022
  Time: 10:25 PM*/

import org.springframework.stereotype.Service;
import uz.narzullayev.javohir.dto.UserDto;
import uz.narzullayev.javohir.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public void save(UserDto user) {

    }

    @Override
    public boolean isUserAlreadyPresent(String username) {
        return false;
    }

    @Override
    public boolean existByEmail(String email) {
        return false;
    }
}
