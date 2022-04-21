package uz.narzullayev.javohir.domain;

import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import uz.narzullayev.javohir.constant.NameEntity;
import uz.narzullayev.javohir.domain.audit.BaseEntity;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@TypeDefs({
        @TypeDef(name = "string-array", typeClass = StringArrayType.class),
        @TypeDef(name = "int-array", typeClass = IntArrayType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Data
public class Science extends BaseEntity {

    @Type(type = "jsonb")
    @Column(name = "name", columnDefinition = "jsonb", nullable = false)
    private NameEntity name;

    @Type(type = "jsonb")
    @Column(name = "description", columnDefinition = "jsonb")
    private NameEntity description;

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinTable(name = "science_teacher",
            joinColumns = @JoinColumn(name = "science_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @ToString.Exclude
    private Set<UserEntity> userEntities = new LinkedHashSet<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Science science = (Science) o;
        return id != null && Objects.equals(id, science.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
