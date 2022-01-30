package uz.narzullayev.javohir.service.impl;/* 
 @author: Javohir
  Date: 1/30/2022
  Time: 11:53 AM*/

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import uz.narzullayev.javohir.dto.LiteratureDto;
import uz.narzullayev.javohir.dto.PlanTeacherDto;
import uz.narzullayev.javohir.entity.Literature;
import uz.narzullayev.javohir.entity.PlanTeacher;
import uz.narzullayev.javohir.exception.RecordNotFoundException;
import uz.narzullayev.javohir.repository.LiteratureRepository;
import uz.narzullayev.javohir.service.FileEntityService;
import uz.narzullayev.javohir.service.LiteratureService;
import uz.narzullayev.javohir.util.AuthUtil;

import javax.validation.constraints.NotNull;

import static org.springframework.data.jpa.domain.Specification.where;
import static uz.narzullayev.javohir.constant.FileType.LITERATURE;
import static uz.narzullayev.javohir.constant.FileType.PLAN_TEACHER;

@Service
@RequiredArgsConstructor
public class LiteratureServiceImpl implements LiteratureService {

    private final LiteratureRepository literatureRepository;
    private final FileEntityService fileEntityService;

    @Override
    @Transactional(readOnly = true)
    public DataTablesOutput<Literature> findAll(DataTablesInput input, LiteratureDto filterDto) {
        return literatureRepository.findAll(input/*, where(byFilterDto(filterDto))*/);
    }

    @Override
    @SneakyThrows
    public Literature findById(Long id) {
        var planTeacher = literatureRepository.findById(id);
        if (planTeacher.isPresent()) {
            return planTeacher.get();
        }
        throw new RecordNotFoundException("Literature not found by id :" + id);
    }

    public Specification<Literature> byFilterDto(LiteratureDto filterDto) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), filterDto.getBookName());
    }

    /**
     * 1.Get multipart file and theme
     * 2.Get PlnnTeacher by id
     * 3.Remove literature by id
     * 4.Additionally info save to planTeacher db
     *
     * @param literatureDto
     */
    @SneakyThrows
    @Override
    public void update(@NotNull LiteratureDto literatureDto) {
        validateDto(literatureDto);
        var literature = findById(literatureDto.getId());
        fileEntityService.remove(literature.getFileEntity().getId());

        var fileEntity = fileEntityService.uploadFile(literatureDto.getFile(), AuthUtil.getUserId().orElse(null), literatureDto.getBookName(), LITERATURE);
        literature.setBookName(literature.getBookName());
        literature.setFileEntity(fileEntity);
        literatureRepository.save(literature);
    }

    private void validateDto(LiteratureDto literatureDto) {
        var multipartFile = literatureDto.getFile();
        var bookName = literatureDto.getBookName();

        Assert.notNull(multipartFile, "Multipart file is null");
        Assert.notNull(bookName, "book name is null");
    }


    /**
     * 1.Multipart export file do local
     * 2.Save multipart to FileEntity
     * 3.Additionally info save to planTeacher db
     *
     * @param literatureDto
     */
    @Override
    public void save(@NotNull LiteratureDto literatureDto) {
        validateDto(literatureDto);
        var fileEntity = fileEntityService.uploadFile(literatureDto.getFile(), AuthUtil.getUserId().orElse(null), literatureDto.getBookName(), LITERATURE);

        var literature = new Literature();
        literature.setFileEntity(fileEntity);
        literature.setBookName(literatureDto.getBookName());
        literatureRepository.save(literature);
    }
}
