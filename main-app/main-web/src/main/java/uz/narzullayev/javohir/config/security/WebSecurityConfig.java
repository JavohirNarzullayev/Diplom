package uz.narzullayev.javohir.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import uz.narzullayev.javohir.config.auth.CustomSuccessHandler;
import uz.narzullayev.javohir.config.auth.UserDetailsServiceImpl;
import uz.narzullayev.javohir.constant.UserType;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true, securedEnabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomSuccessHandler customSuccessHandler;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error")
                .loginProcessingUrl("/login")
                .permitAll()
                .successHandler(customSuccessHandler)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(customAccessDeniedHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/static/**").permitAll()
                .antMatchers("/error").permitAll()
                .antMatchers("/403").permitAll()
                .antMatchers("/404").permitAll()
                .antMatchers("/500").permitAll()
                .antMatchers("/user/registration").permitAll()
                .antMatchers("/").permitAll()
                //teacher
                .antMatchers("/literature/*", "/literature/**", "/teacher_plan/*", "/teacher_plan/**").hasAnyAuthority(UserType.TEACHER.name(), UserType.ADMIN.name())
                //student
                .antMatchers("/student/*", "/student/**").hasAuthority(UserType.STUDENT.name())
                //admin
                .antMatchers("/user/**", "/user/*").hasAuthority(UserType.ADMIN.name())
                //api
                .antMatchers("/main/**").permitAll()
                .antMatchers("/dashboard/**").authenticated();


        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/");

        http.csrf().ignoringAntMatchers("/login", "/api/**");


        http.authorizeRequests().anyRequest().authenticated()
                .and()
                .rememberMe()
                .key("remember-me")
                .tokenValiditySeconds(86_400);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
