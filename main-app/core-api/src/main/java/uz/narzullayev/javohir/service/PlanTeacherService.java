package uz.narzullayev.javohir.service;/* 
 @author: Javohir
  Date: 1/22/2022
  Time: 1:20 PM*/

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import uz.narzullayev.javohir.dto.PlanTeacherDto;
import uz.narzullayev.javohir.domain.PlanTeacher;

public interface PlanTeacherService {

    PlanTeacher findById(Long id);

    DataTablesOutput<PlanTeacher> findAll(DataTablesInput input, PlanTeacherDto filterDto);

    void update(PlanTeacherDto planTeacherDto, Long userId);

    void save(PlanTeacherDto planTeacherDto, Long userId);

    void remove(Long id);
}
