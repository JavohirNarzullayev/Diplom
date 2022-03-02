package uz.narzullayev.javohir.entity.info;/* 
 @author: Javohir
  Date: 1/10/2022
  Time: 1:03 PM*/

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
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class ExtraInfo {


    @CreatedDate
    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime registeredAt;

    @LastModifiedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime updatedAt;

    @Version
    public Long version;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "registered_by", updatable = false, insertable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    public UserEntity registeredBy;
    @CreatedBy
    public Long registered_by;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by", insertable = false, updatable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    public UserEntity updatedBy;
    @LastModifiedBy
    public Long updated_by;

    public Boolean deleted;

    @PrePersist
    void persist() {
        this.deleted = Boolean.FALSE;
    }

    @PreRemove
    public void delete() {
        this.deleted = Boolean.TRUE;
    }


}
