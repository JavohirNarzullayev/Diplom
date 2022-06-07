package uz.narzullayev.javohir.repository;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.narzullayev.javohir.domain.PlanTeacher;

import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
public interface PlanTeacherRepository extends JpaRepository<PlanTeacher, Long>, DataTablesRepository<PlanTeacher, Long> {
    List<PlanTeacher> findAllByRegisteredById(@NotNull Long created_id);
}