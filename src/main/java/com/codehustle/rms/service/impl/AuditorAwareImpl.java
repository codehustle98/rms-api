package com.codehustle.rms.service.impl;

import com.codehustle.rms.security.SecurityUtils;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware {

    @Override
    public Optional getCurrentAuditor() {
        return Optional.ofNullable(SecurityUtils.getLoginUserEmail());
    }
}
