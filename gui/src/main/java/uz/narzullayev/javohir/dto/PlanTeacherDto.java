package uz.narzullayev.javohir.dto;/* 
 @author: Javohir
  Date: 1/22/2022
  Time: 3:41 PM*/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import uz.narzullayev.javohir.entity.FileEntity;
import uz.narzullayev.javohir.entity.PlanTeacher;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.*;
import java.nio.file.Files;

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

    @NotNull(groups = {OnUpdate.class,OnCreate.class})
    private MultipartFile file;


    public PlanTeacherDto(PlanTeacher planTeacher) {
         this.id=planTeacher.getId();
         this.theme=planTeacher.getTheme();
         this.file=fileToMultipart(planTeacher);
    }

    @SneakyThrows
    private MultipartFile fileToMultipart(PlanTeacher planTeacher) {
        FileEntity fileEntity = planTeacher.getFileEntity();
        File file = new File(fileEntity.getPath());
        FileItem fileItem = new DiskFileItemFactory().createItem("file",
                Files.probeContentType(file.toPath()), false, file.getName());

        try (InputStream in = new FileInputStream(file);
             OutputStream out = fileItem.getOutputStream()) {
             in.transferTo(out);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid file: " + e, e);
        }

        return new CommonsMultipartFile(fileItem);
    }
}
