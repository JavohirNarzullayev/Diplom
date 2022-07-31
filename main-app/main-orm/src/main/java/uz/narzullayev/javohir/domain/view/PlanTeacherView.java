package uz.narzullayev.javohir.domain.view;/* 
 @author: Javohir
  Date: 7/3/2022
  Time: 4:48 PM*/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.WhereJoinTable;
import uz.narzullayev.javohir.domain.FileEntity;
import uz.narzullayev.javohir.domain.Science;
import uz.narzullayev.javohir.domain.audit.BaseAuditingEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "plan_teacher_view")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Immutable
public class PlanTeacherView extends BaseAuditingEntity implements Serializable {
    @Column(name = "theme", nullable = false, columnDefinition = "TEXT")
    private String theme;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false, orphanRemoval = true)
    @JoinColumn(name = "file_entity_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @WhereJoinTable(clause = "deleted = false")
    @ToString.Exclude
    private FileEntity fileEntity;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "science_id", referencedColumnName = "id")
    @WhereJoinTable(clause = "deleted = false")
    private Science science;

    @Column(name = "unique_valid", unique = true)
    private Integer uniqueValid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PlanTeacherView that = (PlanTeacherView) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

