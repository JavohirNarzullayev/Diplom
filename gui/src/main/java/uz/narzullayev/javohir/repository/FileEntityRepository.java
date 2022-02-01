package uz.narzullayev.javohir.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.narzullayev.javohir.entity.FileEntity;
import uz.narzullayev.javohir.entity.UserEntity;

import java.io.File;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FileEntityRepository extends JpaRepository<FileEntity, Long> {
    Optional<FileEntity> findByIdAndDeletedFalse(Long id);

    Optional<FileEntity> findByUuidAndDeletedFalse(UUID uuid);

    Optional<FileEntity> findByIdAndRegisteredByAndDeletedFalse(Long id, UserEntity registeredBy);
}