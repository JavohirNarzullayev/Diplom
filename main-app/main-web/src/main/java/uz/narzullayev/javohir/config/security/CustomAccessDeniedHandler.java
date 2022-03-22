package uz.narzullayev.javohir.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import uz.narzullayev.javohir.config.auth.ProjectUserDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       AccessDeniedException e) throws IOException {
        ProjectUserDetails currentUser = SecurityUtils.getCurrentUser();
        if (currentUser != null) {
            log.warn("User: " + currentUser.getAuthorities() + " isAuthenticated-> " + SecurityUtils.isAuthenticated()
                    + " attempted to access the protected URL: "
                    + httpServletRequest.getRequestURI());
        }

        httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/error");
    }
}

