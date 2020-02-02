package com.projects.carworkshop_front.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
public class AppConfig {

    private static AppConfig appConfig;

    private AppConfig() {
    }

    public static AppConfig getInstance() {
        if (appConfig == null) {
            appConfig = new AppConfig();
        }
        return appConfig;
    }

    private String backendEndpoint = "http://localhost:8080/v1/carworkshop/api/";
}
