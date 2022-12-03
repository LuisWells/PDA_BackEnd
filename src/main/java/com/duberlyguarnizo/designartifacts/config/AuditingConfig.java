
/*
 * Created/Modified: 2022. This code was written or modified by Duberly Guarnizo.
 * Free distribution is allowed under the terms of the MIT licence.
 * ¿Comments? Write to duberlygfr@gmail.com.
 */

package com.duberlyguarnizo.designartifacts.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class AuditingConfig {
    @Bean
    public AuditorAware<String> auditorProvider() {
        return new LoggedAdminAuditorAware();
    }
}