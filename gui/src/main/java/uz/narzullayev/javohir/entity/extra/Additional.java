package uz.narzullayev.javohir.entity.extra;/*
 @author: Javohir
  Date: 1/10/2022
  Time: 1:03 PM*/

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.narzullayev.javohir.entity.UserEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Additional implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    protected Long id;

    @CreatedDate
    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime registeredAt;

    @LastModifiedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime updatedAt;

    @Version
    protected Long version;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "registered_by", updatable = false, insertable = false)
    @ToString.Exclude
    @JsonBackReference
    @EqualsAndHashCode.Exclude
    protected UserEntity registeredBy;
    @CreatedBy
    public Long registered_by;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by", insertable = false, updatable = false)
    @ToString.Exclude
    @JsonBackReference
    @EqualsAndHashCode.Exclude
    protected UserEntity updatedBy;
    @LastModifiedBy
    public Long updated_by;

    public Boolean deleted;

    @PrePersist
    void persist() {
        this.deleted = Boolean.FALSE;
    }
}
