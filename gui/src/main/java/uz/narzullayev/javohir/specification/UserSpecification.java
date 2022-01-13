package uz.narzullayev.javohir.specification;/* 
 @author: Javohir
  Date: 1/11/2022
  Time: 5:52 PM*/

import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import uz.narzullayev.javohir.dto.UserFilterDto;
import uz.narzullayev.javohir.entity.UserEntity;

import javax.persistence.criteria.Predicate;
import java.util.LinkedList;
import java.util.List;

@UtilityClass
public class UserSpecification {

    public static Specification<UserEntity> find(UserFilterDto filterDto) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new LinkedList<>();
            if (filterDto.getRole() != null)
                predicates.add(criteriaBuilder.in(root.get("role")).value(List.of(filterDto.getRole())));

            if (StringUtils.hasText(filterDto.getUsername()))
                predicates.add(criteriaBuilder.like(root.get("username"), "%" + filterDto.getUsername() + "%"));

            if (StringUtils.hasText(filterDto.getFio()))
                predicates.add(criteriaBuilder.like(root.get("fio"), "%" + filterDto.getFio() + "%"));

            if (filterDto.getStatus() != null){
                predicates.add(criteriaBuilder.equal(root.get("enabled"), filterDto.getStatus()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
