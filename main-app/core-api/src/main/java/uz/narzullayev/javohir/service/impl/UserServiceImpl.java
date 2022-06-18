package uz.narzullayev.javohir.service.impl;/* 
 @author: Javohir
  Date: 1/7/2022
  Time: 10:25 PM*/

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import uz.narzullayev.javohir.constant.UserType;
import uz.narzullayev.javohir.domain.UserEntity;
import uz.narzullayev.javohir.dto.UserDto;
import uz.narzullayev.javohir.dto.UserFilterDto;
import uz.narzullayev.javohir.repository.UserRepository;
import uz.narzullayev.javohir.service.UserService;
import uz.narzullayev.javohir.specification.UserSpecification;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    @Validated(UserDto.Create.class)
    public UserEntity save(@Valid UserDto user) {
        if (user.getUserType().equals(UserType.TEACHER)) {
            user.setEnabled(false);
        }
        return userRepository.save(user.merge(new UserEntity()));
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Validated(UserDto.Update.class)
    public void update(@Valid UserDto user) {
        var merge = user.merge(findById(user.getId()));
        userRepository.saveAndFlush(merge);
    }


    @Override
    public boolean isUserAlreadyPresent(String username) {
        return username != null ? !userRepository.existsByUsername(username) : !Boolean.FALSE;
    }

    @Override
    public boolean existByEmail(String email) {
        return email != null ? !userRepository.existsByEmail(email) : !Boolean.FALSE;
    }

    @Override
    public DataTablesOutput<UserEntity> findAllBySpecific(DataTablesInput input, UserFilterDto filterDto) {
        return userRepository.findAll(input, UserSpecification.find(filterDto));
    }

    @Override
    public void userBlockOrUnblockById(Long id) {
        var userEntity = userRepository.findById(id);
        if (userEntity.isPresent()) {
            var user = userEntity.get();
            user.setEnabled(!user.getEnabled());
            userRepository.saveAndFlush(user);
        }
    }

    @SneakyThrows
    @Override
    public UserEntity findById(Long id) {
        if (id == null) throw new IllegalAccessException("User id is null");
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<UserEntity> findAllTeachers() {
        return userRepository.findByRole(UserType.TEACHER);
    }

    @Override
    public Set<UserEntity> findByIdIn(Set<Long> teachers) {
        return userRepository.findByIdIn(teachers);
    }
}
