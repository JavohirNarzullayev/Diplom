package uz.narzullayev.javohir.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.narzullayev.javohir.entity.FileEntity;

@Repository
public interface FileEntityRepository extends JpaRepository<FileEntity, Long> {
}