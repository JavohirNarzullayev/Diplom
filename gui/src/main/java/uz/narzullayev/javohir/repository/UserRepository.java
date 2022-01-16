package uz.narzullayev.javohir.repository;/* 
 @author: Javohir
  Date: 1/9/2022
  Time: 8:34 PM*/

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.narzullayev.javohir.entity.UserEntity;

@Repository
public interface UserRepository  extends DataTablesRepository<UserEntity, Long>,  JpaRepository<UserEntity,Long> {

    UserEntity findByUsername(@Param("username") String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
