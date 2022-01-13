package uz.narzullayev.javohir.service.impl;/* 
 @author: Javohir
  Date: 1/7/2022
  Time: 10:25 PM*/

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.narzullayev.javohir.dto.UserDto;
import uz.narzullayev.javohir.dto.UserFilterDto;
import uz.narzullayev.javohir.entity.UserEntity;
import uz.narzullayev.javohir.repository.UserRepository;
import uz.narzullayev.javohir.service.UserService;
import uz.narzullayev.javohir.specification.UserSpecification;

import javax.validation.constraints.NotNull;
import java.util.Optional;

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

    @Override
    @Transactional(readOnly = true)
    public DataTablesOutput<UserEntity> findAllBySpecific(DataTablesInput input, UserFilterDto filterDto) {
        return userRepository.findAll(input, UserSpecification.find( filterDto ));
    }

    @Override
    public Boolean userBlockOrUnblockById(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if (userEntity.isPresent()){
            UserEntity user = userEntity.get();
            user.setEnabled(!user.getEnabled());
            userRepository.saveAndFlush(user);
            return true;
        }
        return Boolean.FALSE;
    }

    @Override
    public UserEntity findById(Long id) {
        return userRepository.findById(id).orElseThrow(IllegalAccessError::new);
    }
}
