package uz.narzullayev.javohir.service.impl;/* 
 @author: Javohir
  Date: 1/30/2022
  Time: 11:53 AM*/

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.datatables.SpecificationBuilder;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.narzullayev.javohir.dto.PlanTeacherDto;
import uz.narzullayev.javohir.entity.Literature;
import uz.narzullayev.javohir.repository.LiteratureRepository;
import uz.narzullayev.javohir.service.LiteratureService;

import static org.springframework.data.jpa.domain.Specification.*;

@Service
@RequiredArgsConstructor
public class LiteratureServiceImpl implements LiteratureService {

    private final LiteratureRepository literatureRepository;

    @Override
    public DataTablesOutput<Literature> findAll(DataTablesInput input, PlanTeacherDto filterDto) {
        return literatureRepository.findAll(input, where(byFilterDto(filterDto)));
    }

    public Specification<Literature> byFilterDto(Object ob){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"),ob);
    }
}
