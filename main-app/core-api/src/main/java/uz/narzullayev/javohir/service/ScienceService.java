package uz.narzullayev.javohir.service;/* 
 @author: Javohir
  Date: 4/12/2022
  Time: 2:10 PM*/

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import uz.narzullayev.javohir.domain.Science;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface ScienceService {

    default void crudScience(@NotNull Science science) {
        if (science.getId() == null) {
            add(science);
        } else {
            update(science);
        }
    }

    void add(Science science);

    void update(Science science);

    void delete(Long id);

    Science findById(Long id);

    DataTablesOutput<Science> findAll(DataTablesInput input);

    List<Science> findAll();

    DataTablesOutput<Science> findAll(DataTablesInput input, String scienceName);
}
