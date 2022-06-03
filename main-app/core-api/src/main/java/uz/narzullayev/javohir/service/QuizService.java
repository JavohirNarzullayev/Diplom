package uz.narzullayev.javohir.service;/* 
 @author: Javohir
  Date: 6/4/2022
  Time: 12:24 AM*/

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import uz.narzullayev.javohir.domain.Quiz;

import java.util.List;


public interface QuizService {
    DataTablesOutput<Quiz> list(DataTablesInput input, Long subject_id);

    List<Quiz> list(Long subject_id);

    void save(Quiz quiz);
}
