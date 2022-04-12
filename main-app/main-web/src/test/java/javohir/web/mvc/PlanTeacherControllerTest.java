package javohir.web.mvc;

import javohir.util.MockExtension;
import javohir.util.TestUtil;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import uz.narzullayev.javohir.constant.UserType;
import uz.narzullayev.javohir.dto.PlanTeacherDto;
import uz.narzullayev.javohir.dto.UserDto;
import uz.narzullayev.javohir.repository.PlanTeacherRepository;
import uz.narzullayev.javohir.service.PlanTeacherService;
import uz.narzullayev.javohir.service.UserService;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PlanTeacherControllerTest extends MockExtension {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PlanTeacherService planTeacherService;
    @Autowired
    private PlanTeacherRepository planTeacherRepository;
    private PlanTeacherDto planTeacherDto;
    MultiValueMap<String, String> params;
    @Autowired
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json", "{\"json\": \"someValue\"}".getBytes());
        planTeacherDto = PlanTeacherDto.builder()
                .theme("Test theme")
                .file(jsonFile)
                .build();
        params = new LinkedMultiValueMap<>();
        params.add("draw", "1");
        params.add("length", "2");
        params.add("columns[0].data", "id");
        params.add("columns[0].name", "id");
        params.add("columns[0].searchable", "true");
        params.add("columns[0].orderable", "true");
        params.add("columns[0].search.value", "");
        params.add("order[0].column", "0");
        params.add("order[0].dir", "asc");

        var userDto = new UserDto();
        userDto.setUsername("admin");
        userDto.setUserType(UserType.ADMIN);
        userDto.setPassword("12345");
        userDto.setConfirmPassword("12345");
        userDto.setFio("narzullayev");
        userDto.setEmail("narzullayevj@gmail.com");
        userDto.setId(1L);
        userDto.setEnabled(true);
        userDto.setPhone("+9998999999");
        userService.save(userDto);
    }

    @TestFactory
    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "userDetailsServiceImpl")
    @DisplayName("test view/templates")
    @Order(1)
    Stream<DynamicTest> dynamicTestsWithCollection() {

        List<Pair<String, String>> pathAndView = List.of(
                Pair.of("/teacher_plan/list", "teacher_plan/list"),
                Pair.of("/teacher_plan/create", "teacher_plan/edit")
        );
        return pathAndView.stream()
                .map(emp -> DynamicTest.dynamicTest(
                        emp.getFirst(),
                        () -> mockMvc.perform(get(emp.getFirst()).param("id", "1L"))
                                .andExpect(status().isOk())
                                .andExpect(view().name(emp.getSecond()))
                ));
    }

    @Test
    @SneakyThrows
    @DisplayName("Ajax result of planTeacher dao")
    @Order(2)
    void listAjax() {
        long count = planTeacherRepository.count();
        ResultActions resultActions = mockMvc.perform(
                        get("/teacher_plan/list_ajax").params(params))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

        MvcResult result = resultActions
                .andExpect(status().isOk())
                .andReturn();
        DataTablesOutput<?> output = TestUtil.convertStringToObject(result.getResponse().getContentAsString(), DataTablesOutput.class);
        assertThat(output.getData().size()).isGreaterThanOrEqualTo(0);
        assertThat(output.getRecordsTotal()).isGreaterThanOrEqualTo(count);
    }

    @Test
    @Order(3)
    void edit() throws Exception {
        //If not found
        mockMvc.perform(get("/plan_teacher/edit")
                .param("id", "-1")
                .secure(true)
        ).andExpect(status().isNotFound());
    }
}