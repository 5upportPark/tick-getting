package com.pjw.tickgettinig.config;

import com.pjw.tickgettinig.config.properties.OAuth2Properties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @date 2026.01.08
 */
@Configuration
@EnableConfigurationProperties({OAuth2Properties.class})
public class PropertiesConfig {
}
