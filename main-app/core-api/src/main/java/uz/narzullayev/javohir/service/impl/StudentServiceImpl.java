package uz.narzullayev.javohir.service.impl;/* 
 @author: Javohir
  Date: 6/4/2022
  Time: 12:25 AM*/

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.narzullayev.javohir.domain.Quiz;
import uz.narzullayev.javohir.repository.QuizRepository;
import uz.narzullayev.javohir.service.QuizService;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentServiceImpl implements QuizService {

    private final QuizRepository quizRepository;

    @Override
    public DataTablesOutput<Quiz> list(DataTablesInput input, Long subject_id) {
        return quizRepository.findAll(input, (root, query, builder) -> builder.equal(root.get("science").get("id"), subject_id));
    }

    @Override
    public List<Quiz> list(Long subject_id) {
        return quizRepository.findAll((root, query, builder) ->
                builder.equal(root.get("science").get("id"), subject_id));
    }

    @Override
    public void save(Quiz quiz) {
        quizRepository.save(quiz);
    }

    @Override
    public Optional<Quiz> getById(Long id) {
        return quizRepository.findById(id);
    }

    @Override
    public void remove(@NotNull Long id) {
        quizRepository.deleteById(id);
    }

    @Override
    public Long count(@NotNull Long science_id) {
        return quizRepository.count((root, query, builder) -> builder.and(builder.equal(root.get("science").get("id"), science_id)));
    }
}
