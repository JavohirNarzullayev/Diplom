package uz.narzullayev.javohir.repository.softdeletes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.NoRepositoryBean;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("java:S119")
@Transactional
@NoRepositoryBean
public interface SoftDeletesRepository<T, ID extends Serializable> extends JpaRepository<T, ID>, DataTablesRepository<T, ID> {

    @Override
    List<T> findAll();

    @Override
    List<T> findAll(Sort sort);

    @Override
    Page<T> findAll(Pageable page);

    Optional<T> findOne(ID id);

    @Override
    default Optional<T> findById(ID id) {
        return findOne(id);
    }

    @Modifying
    void delete(ID id);


    @Override
    default void deleteById(ID id) {
        delete(id);
    }

    @Override
    @Modifying
    void delete(T entity);
}
