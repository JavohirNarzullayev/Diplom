package uz.narzullayev.javohir.service;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import uz.narzullayev.javohir.domain.PlanTeacher;
import uz.narzullayev.javohir.dto.PlanTeacherDto;

public interface PlanTeacherService {

    PlanTeacher findById(Long id);

    DataTablesOutput<PlanTeacher> findAll(DataTablesInput input, PlanTeacherDto filterDto, Long currentUserId);

    void update(PlanTeacherDto planTeacherDto, Long userId);

    void save(PlanTeacherDto planTeacherDto, Long userId);

    void remove(Long id);
}
