package uz.narzullayev.javohir.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import uz.narzullayev.javohir.domain.PlanTeacher;
import uz.narzullayev.javohir.dto.PlanTeacherDto;
import uz.narzullayev.javohir.exception.RecordNotFoundException;
import uz.narzullayev.javohir.repository.PlanTeacherRepository;
import uz.narzullayev.javohir.service.FileEntityService;
import uz.narzullayev.javohir.service.PlanTeacherService;
import uz.narzullayev.javohir.service.ScienceService;

import javax.persistence.criteria.Predicate;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

import static uz.narzullayev.javohir.constant.FileType.PLAN_TEACHER;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PlanTeacherServiceImpl implements PlanTeacherService {

    private final PlanTeacherRepository planTeacherRepository;
    private final FileEntityService fileEntityService;
    private final ScienceService scienceService;

    @SneakyThrows
    @Override
    public PlanTeacher findById(Long id) {
        log.info("PlanTeacher by  id: {}", id);
        return planTeacherRepository.findById(id)
                .orElseThrow(() ->
                        new RecordNotFoundException(String.format("PlanTeacher not found by id : %s ", id), "PlanTeacher", "id"));
    }

    @Override
    public DataTablesOutput<PlanTeacher> findAll(DataTablesInput input, @NotNull PlanTeacherDto filterDto, Long currentUserId) {
        return planTeacherRepository.findAll(input, byFilterDto(filterDto, currentUserId));
    }

    public Specification<PlanTeacher> byFilterDto(PlanTeacherDto filterDto, Long currentUserId) {
        return (root, query, criteriaBuilder) -> {
            Assert.notNull(filterDto, "FilterDto is null");
            List<Predicate> predicates = new LinkedList<>();
            if (StringUtils.hasText(filterDto.getTheme())) {
                predicates.add(
                        criteriaBuilder.like(criteriaBuilder.upper(root.get("theme")),
                                "%" + filterDto.getTheme().toUpperCase() + "%"));

            }
            if (currentUserId != null) {
                predicates.add(criteriaBuilder.equal(root.get("registeredBy").get("id"), currentUserId));
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
        log.info("Update plan_teacher: {}", planTeacherDto);
        var multipartFile = planTeacherDto.getFile();
        var theme = planTeacherDto.getTheme();
        var science = scienceService.getScienceByTeacherId(userId);
        var planTeacher = findById(planTeacherDto.getId());
        Assert.notNull(science, "Science is null");
        Assert.notNull(theme, "Theme is null");
        Assert.notNull(planTeacher, "Teacher not found");


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
        planTeacher.setScience(science);
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
    public void save(@NotNull PlanTeacherDto planTeacherDto, @NotNull Long userId) {
        log.info("PlanTeacher new : {}", planTeacherDto);
        var multipartFile = planTeacherDto.getFile();
        var theme = planTeacherDto.getTheme();
        log.info("Find science by teacher_id");
        var science = scienceService.getScienceByTeacherId(userId);
        Assert.notNull(multipartFile, "Multipart file is null");
        Assert.notNull(theme, "Theme is null");
        Assert.notNull(science, "Science is null");
        log.info("File upload ...");
        var fileEntity = fileEntityService.uploadFile(multipartFile, userId, theme, PLAN_TEACHER);
        var planTeacher = new PlanTeacher();
        planTeacher.setFileEntity(fileEntity);
        planTeacher.setTheme(theme);
        planTeacher.setScience(science);
        planTeacherRepository.save(planTeacher);
    }

    @Override
    public void remove(@NotNull Long id) {
        log.info("PlanTeacher delete by: {}", id);
        planTeacherRepository.deleteById(id);
    }

    @Override
    public List<PlanTeacher> findByCreatedId(@NotNull Long teacher_id) {
        log.info("Find plan_teacher by teacher id: {}", teacher_id);
        return planTeacherRepository.findAllByRegisteredById(teacher_id);
    }
}
