package uz.narzullayev.javohir.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.*;
import uz.narzullayev.javohir.domain.audit.BaseAuditingEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "plan_teacher")
@Data
@Where(clause = "deleted = false")
@SQLDelete(sql = "update plan_teacher set deleted=true where id=? and version=?")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PlanTeacher extends BaseAuditingEntity implements Serializable {
    @Column(name = "theme", nullable = false, columnDefinition = "TEXT")
    private String theme;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false, orphanRemoval = true)
    @JoinColumn(name = "file_entity_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @WhereJoinTable(clause = "deleted = false")
    private FileEntity fileEntity;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "science_id", referencedColumnName = "id")
    @WhereJoinTable(clause = "deleted = false")
    private Science science;
}


