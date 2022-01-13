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
import org.hibernate.annotations.GenericGenerator;
import uz.narzullayev.javohir.constant.UserType;
import uz.narzullayev.javohir.entity.info.ExtraInfo;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
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
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
            name = "sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "user_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "4"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    private Long id;

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
