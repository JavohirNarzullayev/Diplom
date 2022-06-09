package uz.narzullayev.javohir.repository.softdeletes;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.datatables.SpecificationBuilder;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.datatables.mapping.SearchPanes;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;

@SuppressWarnings("java:S119")
public class SoftDeletesRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID>
        implements SoftDeletesRepository<T, ID>, DataTablesRepository<T, ID> {

    private final JpaEntityInformation<T, ?> entityInformation;
    private final EntityManager em;
    private final Class<T> domainClass;
    private static final String DELETED_FIELD = "deleted";
    private static final String ID_MUST_NOT_BE_NULL = "The given id must not be null!";
    ;

    public SoftDeletesRepositoryImpl(JpaEntityInformation<T, ?> entityInformation,
                                     Class<T> domainClass,
                                     EntityManager entityManager) {

        super(entityInformation, entityManager);
        this.em = entityManager;
        this.domainClass = domainClass;
        this.entityInformation = JpaEntityInformationSupport.getEntityInformation(domainClass, em);
    }

    public SoftDeletesRepositoryImpl(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
        this.em = em;
        this.domainClass = domainClass;
        this.entityInformation = JpaEntityInformationSupport.getEntityInformation(domainClass, em);
    }

    @Override
    public List<T> findAll() {
        if (isFieldDeletedExist()) return super.findAll(notDeleted());
        return super.findAll();
    }

    @Override
    public List<T> findAll(@Nullable Sort sort) {
        Assert.notNull(sort, "Sort must be not null");
        if (isFieldDeletedExist()) return super.findAll(notDeleted(), sort);
        return super.findAll(sort);
    }

    @Override
    public List<T> findAll(@Nullable Specification<T> spec) {
        Assert.notNull(spec, "Specification must be not null");
        if (isFieldDeletedExist()) return super.findAll(spec.and(notDeleted()));
        return super.findAll(spec);
    }

    @Override
    public Page<T> findAll(@Nullable Specification<T> spec, Pageable pageable) {
        Assert.notNull(spec, "Specification must be not null");
        Assert.notNull(pageable, "Pageable must be not null");
        if (isFieldDeletedExist()) return super.findAll(spec.and(notDeleted()), pageable);
        return super.findAll(spec, pageable);
    }

    @Override
    public List<T> findAll(@Nullable Specification<T> spec, Sort sort) {
        Assert.notNull(spec, "Specification must be not null");
        Assert.notNull(sort, "Sort must be not null");
        if (isFieldDeletedExist()) return super.findAll(spec.and(notDeleted()), sort);
        return super.findAll(spec, sort);
    }

    @Override
    public Page<T> findAll(@Nullable Pageable page) {
        Assert.notNull(page, "Pageable must be not null");
        if (isFieldDeletedExist()) return super.findAll(notDeleted(), page);
        return super.findAll(page);
    }

    @Override
    public Optional<T> findOne(ID id) {
        if (isFieldDeletedExist())
            return super.findOne(Specification.where(new ByIdSpecification<>(entityInformation, id)).and(notDeleted()));
        return super.findOne(Specification.where(new ByIdSpecification<>(entityInformation, id)));
    }

    @Override
    @Transactional
    public void delete(ID id) {
        softDelete(id);
    }

    @Override
    @Transactional
    public void delete(T entity) {
        softDelete(entity);
    }


    private boolean isFieldDeletedExist() {
        Field superField = null;
        Field localField = null;
        try {
            superField = domainClass.getSuperclass().getDeclaredField(DELETED_FIELD);
            localField = domainClass.getDeclaredField(DELETED_FIELD);
        } catch (NoSuchFieldException e) {

        }
        return superField != null || localField != null;

    }

    private void softDelete(ID id) {
        Assert.notNull(id, "The given id must not be null!");

        Optional<T> entity = findOne(id);

        if (entity.isEmpty())
            throw new EmptyResultDataAccessException(
                    String.format("No %s entity with id %s exists!", entityInformation.getJavaType(), id), 1);

        softDelete(entity.get());
    }

    private void softDelete(T entity) {
        Assert.notNull(entity, "The entity must not be null!");
        if (!isFieldDeletedExist()) {
            super.delete(entity);
            return;
        }
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaUpdate<T> update = cb.createCriteriaUpdate(domainClass);

        Root<T> root = update.from(domainClass);

        update.set(DELETED_FIELD, Boolean.TRUE);

        update.where(
                cb.equal(
                        root.<ID>get(Objects.requireNonNull(entityInformation.getIdAttribute()).getName()),
                        entityInformation.getId(entity)
                )
        );

        em.createQuery(update).executeUpdate();
    }


    @Override
    public DataTablesOutput<T> findAll(DataTablesInput input) {
        return findAll(input, null, null, null);
    }

    @Override
    public DataTablesOutput<T> findAll(DataTablesInput input,
                                       Specification<T> additionalSpecification) {
        return findAll(input, additionalSpecification, null, null);
    }

    @Override
    public DataTablesOutput<T> findAll(DataTablesInput input,
                                       Specification<T> additionalSpecification, Specification<T> preFilteringSpecification) {
        return findAll(input, additionalSpecification, preFilteringSpecification, null);
    }

    @Override
    public <R> DataTablesOutput<R> findAll(DataTablesInput input, Function<T, R> converter) {
        return findAll(input, null, null, converter);
    }

    @Override
    public <R> DataTablesOutput<R> findAll(DataTablesInput input,
                                           Specification<T> additionalSpecification, Specification<T> preFilteringSpecification,
                                           Function<T, R> converter) {
        DataTablesOutput<R> output = new DataTablesOutput<>();
        output.setDraw(input.getDraw());
        if (input.getLength() == 0) {
            return output;
        }
        try {
            long recordsTotal =
                    preFilteringSpecification == null ? count() : count(preFilteringSpecification.and(notDeleted()));
            if (recordsTotal == 0) {
                return output;
            }

            //check deleted=false
            additionalSpecification = additionalSpecification.and(notDeleted());
            if (preFilteringSpecification != null) {
                preFilteringSpecification = preFilteringSpecification.and(notDeleted());
            }
            output.setRecordsTotal(recordsTotal);

            SpecificationBuilder<T> specificationBuilder = new SpecificationBuilder<>(input);
            Specification<T> specification = Specification.where(specificationBuilder.build())
                    .and(additionalSpecification)
                    .and(preFilteringSpecification);
            Page<T> data = findAll(specification, specificationBuilder.createPageable());

            @SuppressWarnings("unchecked")
            List<R> content =
                    converter == null ? (List<R>) data.getContent() : data.map(converter).getContent();
            output.setData(content);
            output.setRecordsFiltered(data.getTotalElements());

            if (input.getSearchPanes() != null) {
                output.setSearchPanes(computeSearchPanes(input, specification));
            }
        } catch (Exception e) {
            output.setError(e.toString());
        }

        return output;
    }

    private SearchPanes computeSearchPanes(DataTablesInput input, Specification<T> specification) {
        CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
        Map<String, List<SearchPanes.Item>> options = new HashMap<>();

        input.getSearchPanes().forEach((attribute, values) -> {
            CriteriaQuery<Object[]> query = criteriaBuilder.createQuery(Object[].class);
            Root<T> root = query.from(getDomainClass());
            query.multiselect(root.get(attribute), criteriaBuilder.count(root));
            query.groupBy(root.get(attribute));
            query.where(specification.toPredicate(root, query, criteriaBuilder));
            root.getFetches().clear();

            List<SearchPanes.Item> items = new ArrayList<>();

            this.em.createQuery(query).getResultList().forEach(objects -> {
                String value = String.valueOf(objects[0]);
                long count = (long) objects[1];
                items.add(new SearchPanes.Item(value, value, count, count));
            });

            options.put(attribute, items);
        });

        return new SearchPanes(options);
    }


    private static final class ByIdSpecification<T, ID> implements Specification<T> {

        private static final long serialVersionUID = 6523470832851906115L;
        private final transient JpaEntityInformation<T, ?> entityInformation;
        private final transient ID id;

        ByIdSpecification(JpaEntityInformation<T, ?> entityInformation, ID id) {
            this.entityInformation = entityInformation;
            this.id = id;
        }

        @Override
        public Predicate toPredicate(Root<T> root, @Nullable CriteriaQuery<?> query, CriteriaBuilder cb) {
            return cb.equal(root.<ID>get(Objects.requireNonNull(entityInformation.getIdAttribute()).getName()), id);
        }
    }

    private static final class DeletedIsNUll<T> implements Specification<T> {

        private static final long serialVersionUID = -940322276301888908L;

        @Override
        public Predicate toPredicate(Root<T> root, @Nullable CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            return criteriaBuilder.isFalse(root.get(DELETED_FIELD));
        }

    }

    private static <T> Specification<T> notDeleted() {
        return Specification.where(new DeletedIsNUll<>());
    }

}
