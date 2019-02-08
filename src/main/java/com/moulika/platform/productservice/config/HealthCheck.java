package com.mapp.platform.productservice.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.core.SpringVersion;
import org.springframework.stereotype.Component;

/**
 * This class is used to check the Health of the Application without any authentication enabled.
 */
@Component
public class HealthCheck implements HealthIndicator {

  protected final Log log = LogFactory.getLog(HealthCheck.class);

  @Override
  public Health health() {
    this.log.info("Spring Framework version: " + SpringVersion.getVersion());
    return Health.up().build();
  }
}