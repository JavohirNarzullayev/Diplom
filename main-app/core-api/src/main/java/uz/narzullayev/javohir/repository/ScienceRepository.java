package uz.narzullayev.javohir.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.narzullayev.javohir.domain.Science;
import uz.narzullayev.javohir.repository.softdeletes.SoftDeletesRepository;

@Repository
public interface ScienceRepository extends SoftDeletesRepository<Science, Long> {

    @Query(value = "select s.* from science s left join science_teacher st on s.id = st.science_id where user_id=:teacher_id", nativeQuery = true)
    Science findByTeacherId(@Param("teacher_id") Long teacher_id);
}