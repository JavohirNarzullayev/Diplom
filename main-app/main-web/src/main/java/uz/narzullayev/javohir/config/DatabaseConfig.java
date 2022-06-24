package uz.narzullayev.javohir.config;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import uz.narzullayev.javohir.repository.softdeletes.CustomJpaRepositoryFactoryBean;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = "uz.narzullayev.javohir.repository",
        repositoryFactoryBeanClass = CustomJpaRepositoryFactoryBean.class)
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableTransactionManagement
public class DatabaseConfig {
    @Bean
    public Hibernate5Module hibernate5Module() {
        return new Hibernate5Module();
    }

    @Bean(name = "flyway", initMethod = "migrate")
    @Profile({"!test"})
    public Flyway flywayNotADestroyer(DataSource dataSource) {
        return Flyway.configure()
                .dataSource(dataSource)
                .baselineOnMigrate(true)
                .baselineVersion("10000")
                .outOfOrder(true)
                .load();
    }

}
