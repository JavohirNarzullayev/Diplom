package uz.narzullayev.javohir.repository;/* 
 @author: Javohir
  Date: 6/4/2022
  Time: 12:58 AM*/

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.narzullayev.javohir.domain.Quiz;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long>, DataTablesRepository<Quiz, Long> {
}
