package uz.narzullayev.javohir.repository;/* 
 @author: Javohir
  Date: 1/9/2022
  Time: 8:34 PM*/

import org.springframework.stereotype.Repository;
import uz.narzullayev.javohir.constant.UserType;
import uz.narzullayev.javohir.domain.UserEntity;
import uz.narzullayev.javohir.repository.softdeletes.SoftDeletesRepository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends SoftDeletesRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    List<UserEntity> findByRole(UserType role);

    Set<UserEntity> findByIdIn(Set<Long> teachers);
}
