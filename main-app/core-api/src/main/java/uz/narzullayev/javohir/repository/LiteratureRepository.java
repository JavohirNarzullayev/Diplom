package uz.narzullayev.javohir.repository;/* 
 @author: Javohir
  Date: 1/30/2022
  Time: 11:54 AM*/

import org.springframework.stereotype.Repository;
import uz.narzullayev.javohir.domain.Literature;
import uz.narzullayev.javohir.repository.softdeletes.SoftDeletesRepository;

@Repository
public interface LiteratureRepository extends SoftDeletesRepository<Literature, Long> {

}
