package uz.narzullayev.javohir.dto;/* 
 @author: Javohir
  Date: 4/21/2022
  Time: 10:07 PM*/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.narzullayev.javohir.domain.Science;
import uz.narzullayev.javohir.domain.UserEntity;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ScienceDto {
    public interface OnCreate {
    }

    public interface OnUpdate {
    }

    private Long id;
    private String nameUz;
    private String nameOz;
    private String descriptionUz;
    private String descriptionOz;
    private Set<Long> teachers = new HashSet<>();
    private Set<UserEntity> teachersEntity = new HashSet<>();

    @Builder
    public ScienceDto(Science science) {
        this.id = science.getId();
        this.nameUz = science.getName().getUz();
        this.nameOz = science.getName().getOz();
        this.descriptionUz = science.getDescription().getUz();
        this.descriptionOz = science.getDescription().getOz();
        this.teachers = science.getUserEntities()
                .stream()
                .map(UserEntity::getId)
                .collect(java.util.stream.Collectors.toSet());
        this.teachersEntity = science.getUserEntities();
    }
}
