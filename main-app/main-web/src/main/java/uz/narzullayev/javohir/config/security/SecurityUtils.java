package uz.narzullayev.javohir.config.security;


import lombok.experimental.UtilityClass;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.narzullayev.javohir.config.auth.ProjectUserDetails;

@UtilityClass
public class SecurityUtils {
    public static Long getCurrentUserId() {
        ProjectUserDetails user = getCurrentUser();
        if (user == null) {
            return null;
        }
        return user.getUserId();
    }

    public static ProjectUserDetails getCurrentUser() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication authentication = ctx.getAuthentication();
        return getAppUserDetails(authentication);
    }

    private static ProjectUserDetails getAppUserDetails(Authentication authentication) {
        try {
            if (authentication.getPrincipal() instanceof ProjectUserDetails) {
                return (ProjectUserDetails) authentication.getPrincipal();
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication == null || authentication instanceof AnonymousAuthenticationToken);
    }

    public static boolean isCurrentUserHasRole(String authority) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authority::equals);
    }
}
