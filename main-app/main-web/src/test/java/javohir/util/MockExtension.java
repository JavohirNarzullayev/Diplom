package javohir.util;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import uz.narzullayev.javohir.GuiApplication;

@WithMockUser(authorities = "ADMIN", username = "admin", password = "12345")
@SpringBootTest(classes = GuiApplication.class)
@AutoConfigureMockMvc
public class MockExtension {
}

