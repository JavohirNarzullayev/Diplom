package uz.narzullayev.javohir.config.security;

import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import uz.narzullayev.javohir.config.CurrentUser;
import uz.narzullayev.javohir.config.auth.ProjectUserDetails;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.util.Objects;

public class CurrentUserArgResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(@Nullable MethodParameter parameter) {
        assert parameter != null;
        return findMethodAnnotation(CurrentUser.class, parameter) != null;
    }

    @Override
    public ProjectUserDetails resolveArgument(@Nullable MethodParameter parameter, ModelAndViewContainer mavContainer,
                                              @Nullable NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        return SecurityUtils.getCurrentUser();
    }

    private <T extends Annotation> T findMethodAnnotation(@Nullable Class<T> annotationClass, MethodParameter parameter) {
        T annotation = parameter.getParameterAnnotation(Objects.requireNonNull(annotationClass));
        if (annotation != null) {
            return annotation;
        }
        Annotation[] annotationsToSearch = parameter.getParameterAnnotations();
        for (Annotation toSearch : annotationsToSearch) {
            annotation = AnnotationUtils.findAnnotation(toSearch.annotationType(), annotationClass);
            if (annotation != null) {
                return annotation;
            }
        }
        return null;
    }
}
