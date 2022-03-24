package uz.narzullayev.javohir.repository;/*
 @author: Javohir
  Date: 3/24/2022
  Time: 3:29 PM*/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;
import uz.narzullayev.javohir.domain.audit.BaseEntity;

import javax.annotation.Nullable;


@NoRepositoryBean
public interface SoftDeleteCrudRepository<T extends BaseEntity, ID extends Long> extends JpaRepository<T, ID> {

    @Override
    @Query("update #{#entityName} e set e.deleted=true where e.id = ?1")
    @Transactional
    @Modifying
    void deleteById(@Nullable Long id);

    @Override
    @Transactional
    default void deleteAllInBatch(Iterable<T> entities) {
        entities.forEach(entity -> deleteById(entity.getId()));
    }


    @Override
    @Query("update #{#entityName} e set e.deleted=false")
    @Transactional
    @Modifying
    void deleteAll();
}
