package uz.narzullayev.javohir.entity;/* 
 @author: Javohir
  Date: 1/9/2022
  Time: 8:34 PM*/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import uz.narzullayev.javohir.constant.UserType;
import uz.narzullayev.javohir.entity.info.ExtraInfo;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

@ToString
@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "users", indexes = {@Index(columnList = "username")}, uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
@JsonIgnoreProperties({"password", "hibernateLazyInitializer", "handler"})
public class UserEntity extends ExtraInfo implements Serializable {

    @NotNull
    @NotEmpty
    private String username;
    private String password;
    private String fio;
    private String phone;
    private String email;

    @ElementCollection(targetClass = UserType.class)
    @JoinTable(name = "roles", joinColumns = @JoinColumn(name = "id"))
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Collection<UserType> role;

    private Boolean enabled;


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
