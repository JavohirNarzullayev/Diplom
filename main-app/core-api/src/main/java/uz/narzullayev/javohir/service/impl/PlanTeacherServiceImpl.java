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
import uz.narzullayev.javohir.dto.PlanTeacherDto;
import uz.narzullayev.javohir.domain.PlanTeacher;
import uz.narzullayev.javohir.exception.RecordNotFoundException;
import uz.narzullayev.javohir.repository.PlanTeacherRepository;
import uz.narzullayev.javohir.service.FileEntityService;
import uz.narzullayev.javohir.service.PlanTeacherService;

import javax.persistence.criteria.Predicate;
import javax.validation.constraints.NotNull;

import java.util.LinkedList;
import java.util.List;

import static uz.narzullayev.javohir.constant.FileType.PLAN_TEACHER;

@Service
@RequiredArgsConstructor
public class PlanTeacherServiceImpl implements PlanTeacherService {

    private final PlanTeacherRepository planTeacherRepository;
    private final FileEntityService fileEntityService;

    @SneakyThrows
    @Override
    public PlanTeacher findById(Long id)  {
        var planTeacher = planTeacherRepository.findById(id);
        if (planTeacher.isPresent()) {
            return planTeacher.get();
        }
        throw new RecordNotFoundException(String.format("PlanTeacher not found by id : %s ", id), "PlanTeacher", "id");
    }

    @Override
    public DataTablesOutput<PlanTeacher> findAll(DataTablesInput input, PlanTeacherDto filterDto) {
        return planTeacherRepository.findAll(input, byFilterDto(filterDto));
    }

    public Specification<PlanTeacher> byFilterDto(PlanTeacherDto filterDto) {
        return (root, query, criteriaBuilder) -> {
            Assert.notNull(filterDto, "FilterDto is null");
            List<Predicate> predicates = new LinkedList<>();
            if (StringUtils.hasText(filterDto.getTheme())) {
                predicates.add(
                        criteriaBuilder.like(criteriaBuilder.upper(root.get("theme")),
                                "%" + filterDto.getTheme().toUpperCase() + "%"));

            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * 1.Get multipart file and theme
     * 2.Get PlanTeacher by id
     * 3.Remove planTeacher by id
     * 4.Additionally info save to planTeacher db
     *
     * @param planTeacherDto
     * @param userId
     */
    @SneakyThrows
    @Override
    public void update(@NotNull PlanTeacherDto planTeacherDto, Long userId) {
        var multipartFile = planTeacherDto.getFile();
        var theme = planTeacherDto.getTheme();
        Assert.notNull(theme, "Theme is null");

        var planTeacher = findById(planTeacherDto.getId());
        if (!multipartFile.isEmpty()) {
            fileEntityService.remove(planTeacher.getFileEntity().getId());
            var fileEntity = fileEntityService.uploadFile(
                    multipartFile,
                    userId,
                    theme,
                    PLAN_TEACHER);
            planTeacher.setFileEntity(fileEntity);
        }
        planTeacher.setTheme(theme);
        planTeacherRepository.save(planTeacher);
    }


    /**
     * 1.Multipart export file do local
     * 2.Save multipart to FileEntity
     * 3.Additionally info save to planTeacher db
     *
     * @param planTeacherDto
     * @param userId
     */
    @Override
    @Transactional
    public void save(@NotNull PlanTeacherDto planTeacherDto, Long userId) {
        var multipartFile = planTeacherDto.getFile();
        var theme = planTeacherDto.getTheme();
        Assert.notNull(multipartFile, "Multipart file is null");
        Assert.notNull(theme, "Theme is null");
        var fileEntity = fileEntityService.uploadFile(multipartFile, userId, theme, PLAN_TEACHER);

        var planTeacher = new PlanTeacher();
        planTeacher.setFileEntity(fileEntity);
        planTeacher.setTheme(theme);
        planTeacherRepository.save(planTeacher);
    }

    @Override
    public void remove(Long id) {
        planTeacherRepository.deleteById(id);
    }
}
