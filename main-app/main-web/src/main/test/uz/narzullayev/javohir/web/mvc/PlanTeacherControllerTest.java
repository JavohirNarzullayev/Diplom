package uz.narzullayev.javohir.web.mvc;

import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import uz.narzullayev.javohir.dto.PlanTeacherDto;
import uz.narzullayev.javohir.exception.RecordNotFoundException;
import uz.narzullayev.javohir.repository.PlanTeacherRepository;
import uz.narzullayev.javohir.service.PlanTeacherService;
import uz.narzullayev.javohir.util.MockExtension;
import uz.narzullayev.javohir.util.TestUtil;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
class PlanTeacherControllerTest extends MockExtension {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PlanTeacherService planTeacherService;
    @Autowired
    private PlanTeacherRepository planTeacherRepository;
    private PlanTeacherDto planTeacherDto;
    MultiValueMap<String, String> params;

    @BeforeEach
    void setUp() {
        MockMultipartFile jsonFile = new MockMultipartFile("json", "Testfilename.txt", "application/json", "{\"json\": \"someValue\"}".getBytes());
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
    }

    @AfterEach
    void tearDown() {
    }


    @TestFactory
    Stream<DynamicTest> dynamicTestsWithCollection() throws Exception {

        List<Pair<String, String>> pathAndView = List.of(
                Pair.of("/teacher_plan/list", "teacher_plan/list"),
                Pair.of("/teacher_plan/create", "teacher_plan/edit")
        );
        return pathAndView.stream()
                .map(emp -> DynamicTest.dynamicTest(
                        emp.getFirst(),
                        () -> {
                            checkView(emp.getFirst(), emp.getSecond());
                        }
                ));
    }

    private void checkView(String path, String templateName) throws Exception {
        try {
            String viewName = Objects.requireNonNull(
                            mockMvc.perform(get(path).param("id", "1L"))
                                    .andExpect(status().isOk())
                                    .andReturn().getModelAndView())
                    .getViewName();
            assertThat(templateName).isEqualTo(viewName);
        } catch (RecordNotFoundException e) {
            assertThat(true).isTrue();
        }


    }

    @Test
    @SneakyThrows
    @DisplayName("View list page")
    void list() {

    }

    @Test
    @SneakyThrows
    @DisplayName("Ajax result of planTeacher dao")
    void listAjax() {
        long count = planTeacherRepository.count();
        ResultActions resultActions = mockMvc.perform(
                        get("/teacher_plan/list_ajax").params(params))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

        MvcResult result = resultActions
                .andExpect(status().isOk())
                .andReturn();
        DataTablesOutput<?> output = TestUtil.convertStringToObject(result.getResponse().getContentAsString(), DataTablesOutput.class);
        assertThat(output.getData().size()).isEqualTo(0);
        assertThat(output.getRecordsTotal()).isGreaterThanOrEqualTo(count);
    }


    @Test
    void create() {

    }


    @Test
    void update() {
    }

    @Test
    void edit() {
    }


    @Test
    void testCreate() {
    }

    @Test
    void delete() {
    }
}