package uz.narzullayev.javohir.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.narzullayev.javohir.domain.FileEntity;
import uz.narzullayev.javohir.domain.UserEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FileEntityRepository extends JpaRepository<FileEntity, Long> {
    Optional<FileEntity> findByIdAndDeletedFalse(Long id);

    Optional<FileEntity> findByUuidAndDeletedFalse(UUID uuid);

    Optional<FileEntity> findByIdAndRegisteredByAndDeletedFalse(Long id, UserEntity registeredBy);
}