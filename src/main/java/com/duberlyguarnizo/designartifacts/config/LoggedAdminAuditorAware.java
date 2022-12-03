
/*
 * Created/Modified: 2022. This code was written or modified by Duberly Guarnizo.
 * Free distribution is allowed under the terms of the MIT licence.
 * ¿Comments? Write to duberlygfr@gmail.com.
 */

package com.duberlyguarnizo.designartifacts.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class LoggedAdminAuditorAware implements AuditorAware<String> {
    private static final Logger logger = LoggerFactory.getLogger(LoggedAdminAuditorAware.class);

    @Override
    public Optional<String> getCurrentAuditor() {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Object principal = auth.getPrincipal();
            AdminUserDetail userDetails = (AdminUserDetail) principal;
            logger.warn("Auditor: " + userDetails.getName());
            return Optional.of(userDetails.getName());
        } else {
            logger.warn(" Unable to get Auditor");
            return Optional.of("Desconocido");
        }
    }
}
