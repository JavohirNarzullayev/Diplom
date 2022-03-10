package uz.narzullayev.javohir;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import uz.narzullayev.javohir.entity.UserEntity;
import uz.narzullayev.javohir.repository.UserRepository;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Integrations tests for {@link UserDetailsService}.
 */
@SpringBootTest(classes = GuiApplication.class)
@Transactional
@ContextConfiguration(classes = UserRepository.class)
public class DomainUserDetailsServiceIT {
    private static final String USER_ONE_LOGIN = "test-user-one";
    private static final String USER_PASS = "123";
    private static final String USER_ONE_EMAIL = "test-user-one@localhost";
    private static final String USER_TWO_LOGIN = "test-user-two";
    private static final String USER_TWO_EMAIL = "test-user-two@localhost";
    private static final String USER_THREE_LOGIN = "test-user-three";
    private static final String USER_THREE_EMAIL = "test-user-three@localhost";

    @Autowired
    private UserRepository userRepository;

    @Test
    void test() {
        System.out.println();
    }

 /*   @Autowired
    private UserDetailsService domainUserDetailsService;*/
  /*  @Autowired
    private PasswordEncoder passwordEncoder;

    private UserEntity userOne;
    private UserEntity userTwo;
    private UserEntity userThree;

    @BeforeEach
    public void init() {
        userOne = new UserEntity();
        userOne.setUs@RunWith(SpringRunner.class)ername(USER_ONE_LOGIN);
        userOne.setPassword(passwordEncoder.encode(USER_PASS));
        userOne.setEnabled(true);
        userOne.setEmail(USER_ONE_EMAIL);
        userOne.setFio("userOne");
        userRepository.save(userOne);

        userTwo = new UserEntity();
        userTwo.setUsername(USER_TWO_LOGIN);
        userTwo.setPassword(passwordEncoder.encode(USER_PASS));
        userTwo.setEnabled(true);
        userTwo.setEmail(USER_TWO_EMAIL);
        userTwo.setFio("userTwo");
        userRepository.save(userTwo);

        userThree = new UserEntity();
        userThree.setUsername(USER_THREE_LOGIN);
        userThree.setPassword(passwordEncoder.encode(USER_PASS));
        userThree.setEnabled(false);
        userThree.setEmail(USER_THREE_EMAIL);
        userThree.setFio("userThree");
        userRepository.save(userThree);
    }*/

 /*   @Test
    @Transactional
    public void assertThatUserCanBeFoundByLogin() {
        UserDetails userDetails = domainUserDetailsService.loadUserByUsername(USER_ONE_LOGIN);
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(USER_ONE_LOGIN);
    }

    @Test
    @Transactional
    public void assertThatUserCanBeFoundByLoginIgnoreCase() {
        UserDetails userDetails = domainUserDetailsService.loadUserByUsername(USER_ONE_LOGIN.toUpperCase(Locale.ENGLISH));
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(USER_ONE_LOGIN);
    }

    @Test
    @Transactional
    public void assertThatUserCanBeFoundByEmail() {
        UserDetails userDetails = domainUserDetailsService.loadUserByUsername(USER_TWO_EMAIL);
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(USER_TWO_LOGIN);
    }

    @Test
    @Transactional
    public void assertThatUserCanBeFoundByEmailIgnoreCase() {
        UserDetails userDetails = domainUserDetailsService.loadUserByUsername(USER_TWO_EMAIL.toUpperCase(Locale.ENGLISH));
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(USER_TWO_LOGIN);
    }

    @Test
    @Transactional
    public void assertThatEmailIsPrioritizedOverLogin() {
        UserDetails userDetails = domainUserDetailsService.loadUserByUsername(USER_ONE_EMAIL);
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(USER_ONE_LOGIN);
    }*/
}
