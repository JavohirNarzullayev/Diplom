package uz.narzullayev.javohir.service.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.narzullayev.javohir.dto.ProjectUserDetails;
import uz.narzullayev.javohir.entity.UserEntity;
import uz.narzullayev.javohir.repository.UserRepository;

import java.time.LocalDateTime;


@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null || username.isEmpty()) {
            log.info(" Username cannot be empty ");
            throw new UsernameNotFoundException(" Username cannot be empty ");
        }

        UserEntity userEntity = userRepository.findByUsernameAndFetchRolesEarly(username);
        if (userEntity == null) {
            log.info(" User [" + username + "] not found ");
            throw new UsernameNotFoundException(" User [" + username + "] not found ");
        }
        if (userEntity.getEnabled() != Boolean.TRUE) {
            log.info(" Your account have been blocked  ");
            throw new UsernameNotFoundException(" Your account have been blocked ");
        }

        ProjectUserDetails userDetails = new ProjectUserDetails(userEntity, userEntity.getId());
        log.info("Logged in at : " + LocalDateTime.now() + " | " + userEntity);
        userRepository.save(userEntity);

        return userDetails;
    }

}
