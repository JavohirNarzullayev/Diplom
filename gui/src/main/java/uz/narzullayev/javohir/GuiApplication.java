package uz.narzullayev.javohir;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.datatables.repository.DataTablesRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "uz.narzullayev.javohir.*")
@EnableJpaRepositories(
        basePackages = "uz.narzullayev.javohir.repository",
        repositoryFactoryBeanClass = DataTablesRepositoryFactoryBean.class)
@EnableCaching(proxyTargetClass = true)
public class GuiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GuiApplication.class, args);
    }

}
