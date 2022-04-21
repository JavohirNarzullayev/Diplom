package uz.narzullayev.javohir.dto;/* 
 @author: Javohir
  Date: 4/21/2022
  Time: 10:07 PM*/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.narzullayev.javohir.constant.NameEntity;
import uz.narzullayev.javohir.domain.Science;
import uz.narzullayev.javohir.domain.UserEntity;

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
    private Set<UserEntity> teachers;

    @Builder
    public ScienceDto(Science science) {
        this.id = science.getId();
        this.nameUz = science.getName().getUz();
        this.nameOz = science.getName().getOz();
        this.descriptionUz = science.getDescription().getUz();
        this.descriptionOz = science.getDescription().getOz();
        this.teachers = science.getUserEntities();
    }

    public Science merge() {
        Science science = new Science();
        science.setId(this.id);
        science.setName(new NameEntity(this.nameUz, this.nameOz));
        science.setDescription(new NameEntity(this.descriptionUz, this.descriptionOz));
        science.setUserEntities(this.teachers);
        return science;
    }
}
