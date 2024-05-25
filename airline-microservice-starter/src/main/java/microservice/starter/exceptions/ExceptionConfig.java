package microservice.starter.exceptions;

import microservice.starter.exceptions.handlers.BusinessExceptionHandler;
import microservice.starter.exceptions.handlers.GeneralExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExceptionConfig {

    @Bean
    public BusinessExceptionHandler businessExceptionHandler() {
        return new BusinessExceptionHandler();
    }

    @Bean
    public GeneralExceptionHandler generalExceptionHandler() {
        return new GeneralExceptionHandler();
    }
}