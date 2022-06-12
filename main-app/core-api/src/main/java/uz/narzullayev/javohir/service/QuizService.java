package uz.narzullayev.javohir.service;/* 
 @author: Javohir
  Date: 6/4/2022
  Time: 12:24 AM*/

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import uz.narzullayev.javohir.domain.Quiz;

import java.util.List;
import java.util.Optional;


public interface QuizService {
    DataTablesOutput<Quiz> list(DataTablesInput input, Long subject_id);

    List<Quiz> list(Long subject_id);

    void save(Quiz quiz);

    Optional<Quiz> getById(Long id);

    void remove(Long id);

    Long count(Long science_id);
}
