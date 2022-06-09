package uz.narzullayev.javohir.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import uz.narzullayev.javohir.repository.softdeletes.CustomJpaRepositoryFactoryBean;

@Configuration
@EnableJpaRepositories(
        basePackages = "uz.narzullayev.javohir.repository",
        repositoryFactoryBeanClass = CustomJpaRepositoryFactoryBean.class)
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableTransactionManagement
public class DatabaseConfig {

}
