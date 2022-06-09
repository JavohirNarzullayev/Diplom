package uz.narzullayev.javohir.repository;

import org.springframework.stereotype.Repository;
import uz.narzullayev.javohir.domain.PlanTeacher;
import uz.narzullayev.javohir.repository.softdeletes.SoftDeletesRepository;

import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
public interface PlanTeacherRepository extends SoftDeletesRepository<PlanTeacher, Long> {
    List<PlanTeacher> findAllByRegisteredById(@NotNull Long created_id);
}