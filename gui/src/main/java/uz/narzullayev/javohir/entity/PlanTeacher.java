package uz.narzullayev.javohir.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.*;
import uz.narzullayev.javohir.entity.info.ExtraInfo;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "plan_teacher")
@Getter
@Setter
@DynamicUpdate
@Where(clause = "deleted=false")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PlanTeacher that = (PlanTeacher) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
