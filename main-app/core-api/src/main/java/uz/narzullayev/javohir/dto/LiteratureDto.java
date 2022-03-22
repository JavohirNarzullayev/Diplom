package uz.narzullayev.javohir.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import uz.narzullayev.javohir.domain.Literature;
import uz.narzullayev.javohir.validation.anontation.NotEmptyMultipart;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class LiteratureDto {
    public interface OnCreate {
    }

    public interface OnUpdate {
    }

    @NotNull(groups = OnUpdate.class)
    private Long id;

    @NotBlank(groups = {OnCreate.class, OnUpdate.class})
    private String bookName;

    @NotEmptyMultipart(groups = {OnCreate.class})
    private MultipartFile file;

    public LiteratureDto(Literature literature) {
        this.id = literature.getId();
        this.bookName = literature.getBookName();
    }
}
