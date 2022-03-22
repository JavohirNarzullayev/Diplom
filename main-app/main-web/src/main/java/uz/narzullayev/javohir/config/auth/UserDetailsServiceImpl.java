package uz.narzullayev.javohir.config.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.narzullayev.javohir.domain.UserEntity;
import uz.narzullayev.javohir.repository.UserRepository;

import java.time.LocalDateTime;


@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null || username.isEmpty()) {
            log.info(" Username cannot be empty ");
            throw new UsernameNotFoundException(" Username cannot be empty ");
        }

        var userEntity = userRepository.findByUsername(username);
        if (userEntity == null) {
            log.info(" User [" + username + "] not found ");
            throw new UsernameNotFoundException(" User [" + username + "] not found ");
        }
        if (userEntity.getEnabled() != Boolean.TRUE) {
            log.info(" Your account have been blocked  ");
            throw new UsernameNotFoundException(" Your account have been blocked ");
        }

        var userDetails = new ProjectUserDetails(userEntity, userEntity.getId());
        log.info("Logged in at : " + LocalDateTime.now() + " | " + userEntity);
        userEntity.setLastVisit(LocalDateTime.now());
        userRepository.save(userEntity);

        return userDetails;
    }

}
