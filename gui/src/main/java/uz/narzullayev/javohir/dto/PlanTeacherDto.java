package uz.narzullayev.javohir.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import uz.narzullayev.javohir.entity.PlanTeacher;
import uz.narzullayev.javohir.validation.anontation.NotEmptyMultipart;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PlanTeacherDto implements Serializable {
    public interface OnCreate{}
    public interface OnUpdate{}

    @NotNull(groups = OnUpdate.class)
    private Long id;
    @NotNull(groups = {OnUpdate.class,OnCreate.class})
    @NotBlank(groups = {OnUpdate.class,OnCreate.class})
    private String theme;

    @NotEmptyMultipart(groups = {LiteratureDto.OnCreate.class})
    private MultipartFile file;


    public PlanTeacherDto(PlanTeacher planTeacher) {
        this.id = planTeacher.getId();
        this.theme = planTeacher.getTheme();
    }
}
