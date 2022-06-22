package uz.narzullayev.javohir.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import uz.narzullayev.javohir.domain.PlanTeacher;
import uz.narzullayev.javohir.validation.anontation.NotEmptyMultipart;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, ignoreUnknown = true)
public class PlanTeacherDto implements Serializable {
    private static final long serialVersionUID = 7993247371386533518L;
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
