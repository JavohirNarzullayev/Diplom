package uz.narzullayev.javohir.service.impl;/* 
 @author: Javohir
  Date: 1/7/2022
  Time: 10:25 PM*/

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.narzullayev.javohir.dto.UserDto;
import uz.narzullayev.javohir.entity.UserEntity;
import uz.narzullayev.javohir.repository.UserRepository;
import uz.narzullayev.javohir.service.UserService;

import javax.validation.constraints.NotNull;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserEntity save(@NotNull UserDto user) {
        return userRepository.save(user.merge());
    }

    @Override
    public boolean isUserAlreadyPresent(String username) {
        return username!=null ? userRepository.existsByUsername(username) : Boolean.FALSE;
    }

    @Override
    public boolean existByEmail(String email) {
        return email!=null ? userRepository.existsByEmail(email) : Boolean.FALSE;
    }
}
