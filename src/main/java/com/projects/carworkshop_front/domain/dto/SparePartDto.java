package com.projects.carworkshop_front.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.projects.carworkshop_front.domain.Car;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SparePartDto {
    @JsonProperty
    private long id;
    @JsonProperty
    private String carBrand;
    @JsonProperty
    private String model;
    @JsonProperty
    private String manufacturer;
    @JsonProperty
    private double price;
}
