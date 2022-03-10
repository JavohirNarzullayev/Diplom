package uz.narzullayev.javohir;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

import static org.springframework.boot.SpringApplication.run;

@Slf4j
@SpringBootApplication
public class GuiApplication {
    public static void main(String[] args) {
        var run = run(GuiApplication.class, args);
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
