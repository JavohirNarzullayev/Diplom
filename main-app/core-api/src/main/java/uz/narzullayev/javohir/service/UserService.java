package uz.narzullayev.javohir.service;/* 
 @author: Javohir
  Date: 1/7/2022
  Time: 9:41 PM*/

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import uz.narzullayev.javohir.domain.UserEntity;
import uz.narzullayev.javohir.dto.UserDto;
import uz.narzullayev.javohir.dto.UserFilterDto;

public interface UserService {
    void update(UserDto user);

    UserEntity save(UserDto user);

    UserEntity findByUsername(String username);

    boolean isUserAlreadyPresent(String username);

    boolean existByEmail(String email);

    DataTablesOutput<UserEntity> findAllBySpecific(DataTablesInput input, UserFilterDto filterDto);

    void userBlockOrUnblockById(Long id);

    UserEntity findById(Long id);
}
