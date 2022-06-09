package uz.narzullayev.javohir.repository;/* 
 @author: Javohir
  Date: 6/4/2022
  Time: 12:58 AM*/

import org.springframework.stereotype.Repository;
import uz.narzullayev.javohir.domain.Quiz;
import uz.narzullayev.javohir.repository.softdeletes.SoftDeletesRepository;

@Repository
public interface QuizRepository extends SoftDeletesRepository<Quiz, Long> {
}
