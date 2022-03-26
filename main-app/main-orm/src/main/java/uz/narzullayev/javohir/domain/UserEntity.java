package uz.narzullayev.javohir.domain;/*
 @author: Javohir
  Date: 1/9/2022
  Time: 8:34 PM*/

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.Hibernate;
import uz.narzullayev.javohir.constant.UserType;
import uz.narzullayev.javohir.domain.audit.BaseAuditingEntity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@ToString(callSuper = true)
@Entity
@Data
@Table(name = "users",
        indexes = {@Index(columnList = "username")}, uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"})
})
@JsonIgnoreProperties({"password", "hibernateLazyInitializer", "handler"})
public class UserEntity extends BaseAuditingEntity implements Serializable {
    @NotNull
    @NotEmpty
    private String username;
    @JsonIgnore
    private String password;
    private String fio;
    private String phone;
    private String email;


    @Column(nullable = false,name = "sys_role")
    @Enumerated(EnumType.STRING)
    private UserType role;

    private Boolean enabled;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastVisit;
}
