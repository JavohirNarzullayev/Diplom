package uz.narzullayev.javohir.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.narzullayev.javohir.entity.UserEntity;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by Anvar Ruzmetov on 06.04.2019.
 */
@Slf4j
@RequiredArgsConstructor
@Data
@AllArgsConstructor
public class ProjectUserDetails implements UserDetails {

    private UserEntity userEntity;
    private UUID userId;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userEntity.getRole().parallelStream()
                .map(userType -> new SimpleGrantedAuthority(userType.name()))
                .collect(Collectors.toList());


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

    public UUID getUserId() {
        return userId;
    }



}
