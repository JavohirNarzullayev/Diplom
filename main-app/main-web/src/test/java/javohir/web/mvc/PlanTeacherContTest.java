package javohir.web.mvc;/* 
 @author: Javohir
  Date: 5/13/2022
  Time: 2:00 AM*/

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.servlet.MockMvc;
import uz.narzullayev.javohir.service.PlanTeacherService;
import uz.narzullayev.javohir.web.mvc.PlanTeacherController;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@AutoConfigureMockMvc
@SpringBootTest(classes = PlanTeacherController.class)
public class PlanTeacherContTest {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private MockMvc mockMvc;


    @Test
    void check_contextStarts() {
        PlanTeacherService mock = Mockito.mock(PlanTeacherService.class);

        assertNotNull(mockMvc);
    }
}
