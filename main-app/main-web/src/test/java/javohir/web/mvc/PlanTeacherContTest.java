package javohir.web.mvc;/* 
 @author: Javohir
  Date: 5/13/2022
  Time: 2:00 AM*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc

public class PlanTeacherContTest {


    @Autowired
    private MockMvc mockMvc;


  /*  @Test
    void check_contextStarts() {
        PlanTeacherService mock = Mockito.mock(PlanTeacherService.class);

        assertNotNull(mockMvc);
    }*/
}
