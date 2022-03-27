package uz.narzullayev.javohir.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import uz.narzullayev.javohir.dto.LiteratureDto;
import uz.narzullayev.javohir.domain.Literature;
import uz.narzullayev.javohir.exception.RecordNotFoundException;
import uz.narzullayev.javohir.repository.LiteratureRepository;
import uz.narzullayev.javohir.service.FileEntityService;
import uz.narzullayev.javohir.service.LiteratureService;
import uz.narzullayev.javohir.specification.UserSpecification;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

import static uz.narzullayev.javohir.constant.FileType.LITERATURE;

@Service
@RequiredArgsConstructor
public class LiteratureServiceImpl implements LiteratureService {

    private final LiteratureRepository literatureRepository;
    private final FileEntityService fileEntityService;


    @Override
    @Transactional(readOnly = true)
    public DataTablesOutput<Literature> findAll(DataTablesInput input, LiteratureDto filterDto) {
        return literatureRepository.findAll(input, byFilterDto(filterDto));
    }

    @Override
    @SneakyThrows
    public Literature findById(Long id) {
        var planTeacher = literatureRepository.findById(id);
        return planTeacher.orElseThrow(() ->
                new RecordNotFoundException(String.format("Literature not found by id : {%s}", id), "Literature", "id")
        );

    }

    public Specification<Literature> byFilterDto(LiteratureDto filterDto) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new LinkedList<>();
            if (StringUtils.hasText(filterDto.getBookName()))
                predicates.add(
                        criteriaBuilder.like(criteriaBuilder.upper(root.get("bookName")),
                                "%" + filterDto.getBookName().toUpperCase() + "%"));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * 1.Get multipart file and theme
     * 2.Get PlnnTeacher by id
     * 3.Remove literature by id
     * 4.Additionally info save to planTeacher db
     *
     * @param literatureDto
     * @param userDetails
     */
    @SneakyThrows
    @Override
    public void update(@NotNull LiteratureDto literatureDto, Long userId) {
        var multipartFile = literatureDto.getFile();
        var bookName = literatureDto.getBookName();

        Assert.notNull(bookName, "book name is null");

        var literature = findById(literatureDto.getId());
        if (!multipartFile.isEmpty()) {
            fileEntityService.remove(literature.getFileEntity().getId());
            var fileEntity = fileEntityService.uploadFile(
                    literatureDto.getFile(),
                    userId,
                    literatureDto.getBookName(),
                    LITERATURE);
            literature.setFileEntity(fileEntity);
        }
        literature.setBookName(literatureDto.getBookName());
        literatureRepository.save(literature);
    }

    /**
     * 1.Multipart export file do local
     * 2.Save multipart to FileEntity
     * 3.Additionally info save to planTeacher table
     *
     * @param literatureDto
     * @param projectUserDetails
     */
    @Override
    public void save(@NotNull LiteratureDto literatureDto, Long user_id) {
        var multipartFile = literatureDto.getFile();
        var bookName = literatureDto.getBookName();
        Assert.notNull(multipartFile, "Multipart file is null");
        Assert.notNull(bookName, "book name is null");

        var fileEntity = fileEntityService.uploadFile(
                multipartFile,
                user_id,
                bookName,
                LITERATURE);

        var literature = new Literature();
        literature.setFileEntity(fileEntity);
        literature.setBookName(bookName);
        literatureRepository.save(literature);
    }

    @Override
    @Transactional
    public void remove(final Long id) {
        literatureRepository.deleteById(id);
    }
}
