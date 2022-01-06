//package uz.narzullayev.javohir.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import uz.sspm.youth.cabinet.companent.CustomSuccessHandler;
//import uz.sspm.youth.cabinet.constant.sys.SysUrls;
//import uz.sspm.youth.core.companent.UserDetailsServiceImpl;
//
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    private static PasswordEncoder encoder;
//
//    private final CustomSuccessHandler customSuccessHandler;
//    private final UserDetailsServiceImpl userDetailsService;
//
//    @Autowired
//    public WebSecurityConfig(CustomSuccessHandler customSuccessHandler, UserDetailsServiceImpl userDetailsService) {
//        this.customSuccessHandler = customSuccessHandler;
//        this.userDetailsService = userDetailsService;
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .formLogin()
//                .loginPage("/login")
//                .failureUrl("/login?error")
//                .loginProcessingUrl("/login")
//                .permitAll()
//                .successHandler(customSuccessHandler)
//                .and()
//                .authorizeRequests()
////                .antMatchers("/admin/dashboard/**").hasAuthority(Permissions.ADMIN.name())
//                .antMatchers("/quiz/login*/**").permitAll()
//                .antMatchers("/quiz").permitAll()
//                .antMatchers("/repository/**").permitAll()
//                .antMatchers("/static*/**", "/map").permitAll()
//                .antMatchers("/admin/mahalla/by_sub_region").permitAll()
//                .antMatchers("/student/business_course/certificate_file_download_for_view").permitAll()
//                .antMatchers("/student/check_pnfl").permitAll()
//                .antMatchers("/student/get_pnfl").permitAll()
//
//                //api
//                .antMatchers("/api/**").permitAll()
//                .antMatchers(SysUrls.ErrorNotFound).permitAll()
//                .antMatchers(SysUrls.ErrorForbidden).permitAll()
//                .antMatchers(SysUrls.ErrorInternalServerError).permitAll()
//                .antMatchers("/dashboard/**").authenticated();
//
//
//        http.logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .logoutSuccessUrl("/login?logout");
//
//        http.csrf().ignoringAntMatchers("/login", "/api/**");
//
//
//
//        http.authorizeRequests().anyRequest().authenticated()
//                .and()
//                .rememberMe()
//                .key("remember-me")
//                .tokenValiditySeconds(86400);
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        if (encoder == null)
//            encoder = new BCryptPasswordEncoder();
//        return encoder;
//    }
//}
