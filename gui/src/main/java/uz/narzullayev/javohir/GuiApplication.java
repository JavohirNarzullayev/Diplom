package uz.narzullayev.javohir;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.datatables.repository.DataTablesRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(
        basePackages = {"uz.narzullayev.javohir.repository"},
        repositoryFactoryBeanClass = DataTablesRepositoryFactoryBean.class)
@EnableCaching(proxyTargetClass = true)
public class GuiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GuiApplication.class, args);
    }

}
