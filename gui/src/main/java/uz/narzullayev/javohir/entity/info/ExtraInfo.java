package uz.narzullayev.javohir.entity.info;/* 
 @author: Javohir
  Date: 1/10/2022
  Time: 1:03 PM*/

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.narzullayev.javohir.entity.UserEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public  class ExtraInfo {


    @CreatedDate
    @Column( updatable = false)
    private LocalDateTime registeredAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Version
    private Long version;

    @OneToOne(cascade = {CascadeType.DETACH,CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JoinColumn(name = "registered_by", updatable = false,insertable = false)
    private UserEntity registeredBy;
    @CreatedBy
    private Long registered_by;

    @OneToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by",insertable = false,updatable = false)
    private UserEntity updatedBy;
    @LastModifiedBy
    private Long updated_by;

    private Boolean deleted;




    @PrePersist
    void persist(){
        this.deleted=Boolean.FALSE;
    }

}
