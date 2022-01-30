package uz.narzullayev.javohir.entity;/* 
 @author: Javohir
  Date: 1/21/2022
  Time: 10:05 AM*/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import uz.narzullayev.javohir.entity.info.ExtraInfo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "plan_teacher")
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PlanTeacher extends ExtraInfo implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
            name = "sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "plan_teacher_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    private Long id;

    @Column(name = "theme",nullable = false,columnDefinition = "TEXT")
    private String theme;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY, optional = false, orphanRemoval = true)
    @JoinColumn(name = "file_entity_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private FileEntity fileEntity;


    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + getId() + ", " +
                "theme = " + getTheme() + ", " +
                "registeredAt = " + getRegisteredAt() + ", " +
                "updatedAt = " + getUpdatedAt() + ", " +
                "version = " + getVersion() + ", " +
                "registeredBy = " + getRegisteredBy() + ", " +
                "updatedBy = " + getUpdatedBy() + ", " +
                "deleted = " + getDeleted() + ")";
    }
}
