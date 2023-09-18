package web.mvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import web.mvc.encryption.BCryptImpl;
import web.mvc.encryption.EncryptHelper;

@Configuration
public class EncryptConfig {
    @Bean
    public EncryptHelper encryptConfigure() {
        return new BCryptImpl();
    }
}
