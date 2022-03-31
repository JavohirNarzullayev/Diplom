package uz.narzullayev.javohir.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.narzullayev.javohir.constant.NameEntity;
import uz.narzullayev.javohir.domain.Science;

import java.util.List;

public interface ScienceRepository extends JpaRepository<Science, Long> {
    @Query("select s from Science s where s.description = ?1")
    List<Science> findByDescription(NameEntity description);
}