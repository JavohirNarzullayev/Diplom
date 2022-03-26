package uz.narzullayev.javohir.domain;

import lombok.Data;
import org.hibernate.annotations.*;
import uz.narzullayev.javohir.domain.audit.BaseAuditingEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "literature")
@Data
@SQLDelete(sql = "update literature set deleted=true where id=?")
@Where(clause = "deleted = false")
public class Literature extends BaseAuditingEntity implements Serializable {
    private String bookName;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false, orphanRemoval = true)
    @JoinColumn(name = "file_entity_id")
    @WhereJoinTable(clause = "deleted = false")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private FileEntity fileEntity;


}
