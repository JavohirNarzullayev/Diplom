package uz.narzullayev.javohir.config.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.narzullayev.javohir.constant.UserType;
import uz.narzullayev.javohir.domain.UserEntity;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class ProjectUserDetails implements UserDetails {

    private UserEntity userEntity;
    private Long userId;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userEntity.getRole().name()));
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return userEntity.getEnabled();
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public Long getUserId() {
        return userId;
    }

    @JsonIgnore
    public boolean isSystemAdmin() {
        return getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(s -> s.equals(UserType.ADMIN.name()));
    }


}
