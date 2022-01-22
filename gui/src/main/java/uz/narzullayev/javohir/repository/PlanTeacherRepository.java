package uz.narzullayev.javohir.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.narzullayev.javohir.entity.PlanTeacher;

@Repository
public interface PlanTeacherRepository extends JpaRepository<PlanTeacher, Long> {
}