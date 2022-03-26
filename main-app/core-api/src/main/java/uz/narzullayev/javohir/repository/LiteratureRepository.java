package uz.narzullayev.javohir.repository;/* 
 @author: Javohir
  Date: 1/30/2022
  Time: 11:54 AM*/

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.narzullayev.javohir.domain.Literature;

@Repository
public interface LiteratureRepository extends JpaRepository<Literature, Long>, DataTablesRepository<Literature, Long> {

}
