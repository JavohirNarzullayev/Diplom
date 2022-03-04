package uz.narzullayev.javohir.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.*;
import uz.narzullayev.javohir.entity.extra.Addional;

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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PlanTeacher extends Addional implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "theme", nullable = false, columnDefinition = "TEXT")
    private String theme;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false, orphanRemoval = true)
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
