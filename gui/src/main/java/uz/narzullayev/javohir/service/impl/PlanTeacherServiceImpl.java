package uz.narzullayev.javohir.service.impl;/* 
 @author: Javohir
  Date: 1/22/2022
  Time: 1:20 PM*/

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import uz.narzullayev.javohir.constant.FileType;
import uz.narzullayev.javohir.dto.PlanTeacherDto;
import uz.narzullayev.javohir.entity.PlanTeacher;
import uz.narzullayev.javohir.exception.RecordNotFoundException;
import uz.narzullayev.javohir.repository.PlanTeacherRepository;
import uz.narzullayev.javohir.service.FileEntityService;
import uz.narzullayev.javohir.service.PlanTeacherService;
import uz.narzullayev.javohir.util.AuthUtil;

import javax.validation.constraints.NotNull;

import static uz.narzullayev.javohir.constant.FileType.*;

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
        throw new RecordNotFoundException("PlanTeacher not found by id->"+id);
    }

    @Override
    @Transactional(readOnly = true)
    public DataTablesOutput<PlanTeacher> findAll(DataTablesInput input, PlanTeacherDto filterDto) {
        return planTeacherRepository.findAll(input);
    }

    /**
     * 1.Get multipart file and theme
     * 2.Get PlnnTeacher by id
     * 3.Remove planTeacher by id
     * 4.Additionally info save to planTeacher db
     * @param planTeacherDto
     */
    @SneakyThrows
    @Override
    public void update(@NotNull PlanTeacherDto planTeacherDto) {
        var multipartFile = planTeacherDto.getFile();
        var theme = planTeacherDto.getTheme();

        Assert.notNull(multipartFile,"Multipart file is null");
        Assert.notNull(theme,"Theme is null");

        var planTeacher = findById(planTeacherDto.getId());
        fileEntityService.remove(planTeacher.getFileEntity().getId());

        var fileEntity = fileEntityService.uploadFile(multipartFile, AuthUtil.getUserId().orElse(null), theme, PLAN_TEACHER );
        planTeacher.setTheme(planTeacher.getTheme());
        planTeacher.setFileEntity(fileEntity);
        planTeacherRepository.save(planTeacher);
    }


    /**
     * 1.Multipart export file do local
     * 2.Save multipart to FileEntity
     * 3.Additionally info save to planTeacher db
     * @param planTeacherDto
     */
    @Override
    public void save(@NotNull PlanTeacherDto planTeacherDto) {
        var multipartFile = planTeacherDto.getFile();
        var theme = planTeacherDto.getTheme();
        Assert.notNull(multipartFile,"Multipart file is null");
        Assert.notNull(theme,"Theme is null");
        var fileEntity = fileEntityService.uploadFile(multipartFile, AuthUtil.getUserId().orElse(null), theme, PLAN_TEACHER );

        var planTeacher=new PlanTeacher();
        planTeacher.setFileEntity(fileEntity);
        planTeacher.setTheme(theme);
        planTeacherRepository.save(planTeacher);
    }

    @Override
    public void remove(Long id) {
        planTeacherRepository.deleteById(id);
    }
}
