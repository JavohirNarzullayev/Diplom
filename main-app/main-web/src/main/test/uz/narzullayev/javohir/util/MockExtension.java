package uz.narzullayev.javohir.util;/* 
 @author: Javohir
  Date: 3/31/2022
  Time: 10:38 PM*/

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import uz.narzullayev.javohir.GuiApplication;

@WithMockUser
@SpringBootTest(classes = GuiApplication.class)
@AutoConfigureMockMvc
public class MockExtension {
}

