package uz.narzullayev.javohir.service;/* 
 @author: Javohir
  Date: 1/7/2022
  Time: 9:41 PM*/

import uz.narzullayev.javohir.dto.UserDto;

public interface UserService {
    void save(UserDto user);

    boolean isUserAlreadyPresent(String username);

    boolean existByEmail(String email);
}
