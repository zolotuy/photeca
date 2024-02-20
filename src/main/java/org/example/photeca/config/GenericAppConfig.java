package org.example.photeca.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class GenericAppConfig {

    @Value("${app.maxUsers}")
    private int maxUsers;

    @Value("${app.percentChangeThreshold}")
    private double percentChangeThreshold;

}
