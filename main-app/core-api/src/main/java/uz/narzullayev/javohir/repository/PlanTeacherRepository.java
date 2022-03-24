package uz.narzullayev.javohir.repository;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;
import uz.narzullayev.javohir.domain.PlanTeacher;

@Repository
public interface PlanTeacherRepository extends SoftDeleteCrudRepository<PlanTeacher, Long>, DataTablesRepository<PlanTeacher, Long> {
}