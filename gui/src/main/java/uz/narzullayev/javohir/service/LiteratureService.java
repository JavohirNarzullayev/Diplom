package uz.narzullayev.javohir.service;/* 
 @author: Javohir
  Date: 1/30/2022
  Time: 11:52 AM*/


import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import uz.narzullayev.javohir.dto.LiteratureDto;
import uz.narzullayev.javohir.entity.Literature;

public interface LiteratureService {

    DataTablesOutput<Literature> findAll(DataTablesInput input, LiteratureDto filterDto);

    Literature findById(Long id);

    void update(LiteratureDto literatureDto);

    void save(LiteratureDto literatureDto);

}
