package com.system.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.PostConstruct;
import java.util.Properties;

@Component
public class FreemarkerConfig {
    private FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
    @Autowired
    FreeMarkerProperties freeMarkerProperties;

    @PostConstruct
    public void setup() {
        configurer.setTemplateLoaderPaths(freeMarkerProperties.getTemplateLoaderPath());
        configurer.setDefaultEncoding("utf-8");
        Properties settings = new Properties();
        settings.putAll(freeMarkerProperties.getSettings());
        configurer.setFreemarkerSettings(settings);
    }

    public FreeMarkerConfigurer getConfigurer() {
        return configurer;
    }
}
