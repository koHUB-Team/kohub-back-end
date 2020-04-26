package kr.kohub.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@ComponentScan(basePackages = {"kr.kohub.dao","kr.kohub.service"})
@Import({DBconfig.class})
public class ApplicationConfig {

}
