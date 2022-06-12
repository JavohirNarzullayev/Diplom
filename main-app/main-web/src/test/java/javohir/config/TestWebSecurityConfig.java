package javohir.config;/* 
 @author: Javohir
  Date: 6/12/2022
  Time: 4:32 PM*/

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import uz.narzullayev.javohir.config.auth.ProjectUserDetails;
import uz.narzullayev.javohir.constant.UserType;
import uz.narzullayev.javohir.domain.UserEntity;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@TestConfiguration
public class TestWebSecurityConfig {

    @Bean
    public UserEntity testUser() {
        UserEntity admin = new UserEntity();
        admin.setId(1L);
        admin.setEmail("narzullayev@gmail.com");
        admin.setUsername("admin");
        admin.setPassword("password");
        admin.setLastVisit(LocalDateTime.now());
        admin.setRole(UserType.ADMIN);
        admin.setFio("fio");
        admin.setEnabled(true);
        admin.setPhone("+998900167596");
        admin.setDeleted(false);
        admin.setLastModifiedDate(Instant.now());
        return admin;
    }

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {

        UserEntity userEntity = testUser();
        ProjectUserDetails userDetails = new ProjectUserDetails(userEntity, userEntity.getId());

        return new UserDetailsManager() {

            private final InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager(List.of(userDetails));


            @Override
            public void createUser(UserDetails userDetails) {
                this.inMemoryUserDetailsManager.createUser(userDetails);
            }

            @Override
            public void updateUser(UserDetails userDetails) {
                this.inMemoryUserDetailsManager.updateUser(userDetails);
            }

            @Override
            public void deleteUser(String s) {
                this.inMemoryUserDetailsManager.deleteUser(s);
            }

            @Override
            public void changePassword(String s, String s1) {
                this.inMemoryUserDetailsManager.changePassword(s, s1);
            }

            @Override
            public boolean userExists(String s) {
                return this.inMemoryUserDetailsManager.userExists(s);
            }

            @Override
            public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
                return userDetails;
            }
        };
    }

}
