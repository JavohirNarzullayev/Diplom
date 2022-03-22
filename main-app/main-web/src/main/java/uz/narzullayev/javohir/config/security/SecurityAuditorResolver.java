package uz.narzullayev.javohir.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import uz.narzullayev.javohir.domain.UserEntity;

import javax.persistence.EntityManager;
import java.util.Optional;

@Component("auditorAware")
@RequiredArgsConstructor
public class SecurityAuditorResolver implements AuditorAware<UserEntity> {
    private final EntityManager entityManager;

    @Override
    public Optional<UserEntity> getCurrentAuditor() {
        Long userId = SecurityUtils.getCurrentUserId();
        if (userId == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(entityManager.getReference(UserEntity.class, userId));
    }


}
