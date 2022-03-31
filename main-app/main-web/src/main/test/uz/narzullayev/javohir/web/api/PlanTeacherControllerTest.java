package uz.narzullayev.javohir.web.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import uz.narzullayev.javohir.GuiApplication;

import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(authorities = "ADMIN", username = "admin", password = "2242114")
@SpringBootTest(classes = GuiApplication.class)
@AutoConfigureMockMvc
public class PlanTeacherControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    @DisplayName("Test Good working api")
    void testingGoodWorking() throws Exception {
        String viewName = Objects.requireNonNull(
                        mockMvc.perform(get("/teacher_plan/list"))
                                .andExpect(status().isOk())
                                .andReturn().getModelAndView())
                .getViewName();
        Assertions.assertThat("teacher_plan/list").isEqualTo(viewName);

    }


}
