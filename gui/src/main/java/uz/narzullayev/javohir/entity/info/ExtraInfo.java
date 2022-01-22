package uz.narzullayev.javohir.entity.info;/* 
 @author: Javohir
  Date: 1/10/2022
  Time: 1:03 PM*/

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter
@MappedSuperclass
public class ExtraInfo {


    @CreatedDate
    @Column( updatable = false)
    private LocalDateTime registeredAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Version
    private Long version;

    @CreatedBy
    protected  UUID registeredBy;

    @LastModifiedBy
    protected  UUID updatedBy;

    private Boolean deleted;




    @PrePersist
    void persist(){
        this.deleted=Boolean.FALSE;
    }

}
