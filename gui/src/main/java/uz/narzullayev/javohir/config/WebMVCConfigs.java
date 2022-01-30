package uz.narzullayev.javohir.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import uz.narzullayev.javohir.util.AuthUtil;

import java.util.Locale;


@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "uz.narzullayev.javohir")
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
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
            /*    .setCachePeriod(24 * 60 * 60 * 1000);*/
    }

    @Bean
    public ViewResolver getViewResolver() {
        var resolver = new InternalResourceViewResolver();
        resolver.setPrefix("templates/");
        resolver.setSuffix(".html");
        return resolver;
    }

    @Bean(name = "messageSource")
    public MessageSource messageSource() {
        var messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
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
    public SpringResourceTemplateResolver defaultTemplateResolver() {
        var resolver = new SpringResourceTemplateResolver();
        resolver.setPrefix("classpath:/templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("html");
        resolver.setCharacterEncoding("UTF-8");
        resolver.setCacheable(true);
        return resolver;
    }


    //for createdBy,updatedBy
    @Bean
    public AuditorAware<Long> auditorAware(){
        return AuthUtil::getUserId;
    }


}
