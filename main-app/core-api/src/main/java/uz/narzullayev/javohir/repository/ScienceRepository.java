package uz.narzullayev.javohir.repository;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.narzullayev.javohir.domain.Science;

@Repository
public interface ScienceRepository extends DataTablesRepository<Science, Long>, JpaRepository<Science, Long> {
}