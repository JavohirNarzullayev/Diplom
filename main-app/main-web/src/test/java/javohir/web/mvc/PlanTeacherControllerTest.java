package javohir.web.mvc;

import javohir.config.TestWebSecurityConfig;
import javohir.util.TestUtil;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import uz.narzullayev.javohir.repository.PlanTeacherRepository;
import uz.narzullayev.javohir.service.PlanTeacherService;
import uz.narzullayev.javohir.web.mvc.PlanTeacherController;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {PlanTeacherController.class, PlanTeacherRepository.class})
@Import(TestWebSecurityConfig.class)
@AutoConfigureMockMvc
class PlanTeacherControllerTest {
    @Autowired
    private MockMvc mockMvc;
    //Todo  amaliy ishlar buyicha fan qushish kerak
    //Texnika Qodirov Fazliddin
    @MockBean
    private PlanTeacherRepository planTeacherRepository;
    @MockBean
    private PlanTeacherService planTeacherService;

    MultiValueMap<String, String> params;


    @BeforeEach
    void setUp() {
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
    }


    @TestFactory
    @WithUserDetails("admin")
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
    @WithUserDetails("admin")
    void listAjax() {
        long count = planTeacherRepository.count();

        MvcResult result = this.mockMvc.perform(get("/teacher_plan/list_ajax")
                        .params(params)
                        .secure(true)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        if (contentAsString.isEmpty()) return;
        DataTablesOutput<?> output = TestUtil.convertStringToObject(contentAsString, DataTablesOutput.class);

        assertThat(output.getDraw()).isEqualTo(1);
        assertThat(output.getData().size()).isGreaterThanOrEqualTo(0);
        assertThat(output.getRecordsTotal()).isGreaterThanOrEqualTo(count);
    }

    @Test
    @Order(3)
    @WithUserDetails("admin")
    void edit() throws Exception {
        //If not found
        mockMvc.perform(get("/plan_teacher/edit")
                .param("id", "-1")
                .secure(true)
        ).andExpect(status().isNotFound());
    }
}
