package uz.narzullayev.javohir.domain.audit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.narzullayev.javohir.domain.UserEntity;

import javax.persistence.*;
import java.time.Instant;


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class BaseAuditingEntity extends BaseEntity {

    private static final long serialVersionUID = 4681401402666658611L;

    @CreationTimestamp
    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @UpdateTimestamp
    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @Version
    protected Long version;

    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registered_by", updatable = false)
    @JsonIgnore
    protected UserEntity registeredBy;


    @LastModifiedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    @JsonIgnore
    protected UserEntity updatedBy;
    public Boolean deleted;

    @PrePersist
    void persist() {
        this.deleted = Boolean.FALSE;
    }
}
