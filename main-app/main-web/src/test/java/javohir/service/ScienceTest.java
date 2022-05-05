/*
package javohir.service;*/
/*
 @author: Javohir
  Date: 4/12/2022
  Time: 1:43 PM*//*


import javohir.util.MockExtension;
import javohir.util.TestUtil;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import uz.narzullayev.javohir.constant.NameEntity;
import uz.narzullayev.javohir.domain.Science;
import uz.narzullayev.javohir.domain.UserEntity;
import uz.narzullayev.javohir.repository.ScienceRepository;
import uz.narzullayev.javohir.service.UserService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ScienceTest extends MockExtension {
    @Autowired
    private ScienceRepository repository;
    @Autowired
    private UserService userService;
    private NameEntity name;
    private Set<UserEntity> userEntities;

    @BeforeEach
    void testSetup() {
        userEntities = TestUtil.userList()
                .stream()
                .map(userService::save)
                .collect(Collectors.toSet());
        name = new NameEntity();
        name.setOz("JavaOz");
        name.setUz("JavaUz");
        var science = new Science(name, name, userEntities);
        science = repository.save(science);
        */
/* assertThat(science).hasFieldOrPropertyWithValue("name", name);*//*

    }

    @Test
    @Order(1)
    public void should_find_no_tutorials_if_repository_is_empty() {
        List<Science> all = repository.findAll();
        assertThat(all).hasSize(1);
    }


}
*/
