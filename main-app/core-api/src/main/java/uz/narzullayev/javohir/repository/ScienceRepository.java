package uz.narzullayev.javohir.repository;

import org.springframework.stereotype.Repository;
import uz.narzullayev.javohir.domain.Science;
import uz.narzullayev.javohir.repository.softdeletes.SoftDeletesRepository;

@Repository
public interface ScienceRepository extends SoftDeletesRepository<Science, Long> {
}