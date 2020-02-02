package com.projects.carworkshop_front.config;

import com.vaadin.flow.spring.annotation.SpringComponent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SpringComponent
public class AppConfig {

    private String backendEndpoint = "http://localhost:8080/v1/carworkshop/api/";
}
