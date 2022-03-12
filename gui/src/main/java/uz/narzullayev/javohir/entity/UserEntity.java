package uz.narzullayev.javohir.entity;/* 
 @author: Javohir
  Date: 1/9/2022
  Time: 8:34 PM*/

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import uz.narzullayev.javohir.constant.UserType;
import uz.narzullayev.javohir.entity.extra.Additional;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@ToString(callSuper = true)
@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "users", indexes = {@Index(columnList = "username")}, uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
@JsonIgnoreProperties({"password", "hibernateLazyInitializer", "handler"})
public class UserEntity extends Additional {
    private static final long serialVersionUID = 1L;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserEntity that = (UserEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
