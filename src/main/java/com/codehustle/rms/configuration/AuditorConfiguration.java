package com.codehustle.rms.configuration;

import com.codehustle.rms.service.impl.AuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AuditorConfiguration {

    @Bean
    AuditorAware<String> auditorAware(){
        return new AuditorAwareImpl();
    }
}
