package uz.narzullayev.javohir.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import uz.narzullayev.javohir.AppProperties;
import uz.narzullayev.javohir.config.security.CurrentUserArgResolver;

import java.util.List;
import java.util.Locale;


@Configuration
@EnableWebMvc
@EnableCaching(proxyTargetClass = true)
@EnableConfigurationProperties(value = AppProperties.class)
public class WebMVCConfigs implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/").setViewName("index");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/static/**", "/static/*")
                .addResourceLocations("classpath:/static/");
        /*  .setCachePeriod(24 * 60 * 60 * 1000);*/
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        var curUserResolver = new CurrentUserArgResolver();
        resolvers.add(curUserResolver);
    }

    @Bean
    public MessageSource messageSource() {
        var messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver() {
        var resolver = new CookieLocaleResolver();
        resolver.setDefaultLocale(new Locale("oz"));
        resolver.setCookieName("localeCookie");
        resolver.setCookieMaxAge(4800);
        return resolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        var interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        registry.addInterceptor(interceptor);
    }

    @Bean
    public FilterRegistrationBean<ReqLogFilter> loggingFilter() {
        var registrationBean = new FilterRegistrationBean<ReqLogFilter>();
        registrationBean.setFilter(new ReqLogFilter());
        registrationBean.setOrder((Ordered.HIGHEST_PRECEDENCE));
        return registrationBean;
    }
}
