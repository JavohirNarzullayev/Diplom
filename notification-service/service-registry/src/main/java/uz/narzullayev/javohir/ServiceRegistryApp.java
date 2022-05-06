package uz.narzullayev.javohir;/* 
 @author: Javohir
  Date: 5/6/2022
  Time: 5:11 PM*/

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

@SpringBootApplication
@EnableEurekaServer
@Slf4j
public class ServiceRegistryApp {

    public static void main(String[] args) {
        var run = SpringApplication.run(ServiceRegistryApp.class, args);
        initApplication(run.getEnvironment());
    }

    private static void initApplication(Environment env) {
        String serverPort = Optional.ofNullable(env.getProperty("server.port")).orElse("8080");
        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }
        log.info(
                "\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running! Access URLs:\n\t" +
                        "Local: \t\thttp://localhost:{}\n\t" +
                        "External: \thttps://{}:{}\n\t" +
                        "Profile(s): \t{}\n----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                serverPort,
                hostAddress,
                serverPort,
                env.getActiveProfiles()
        );

    }
}
