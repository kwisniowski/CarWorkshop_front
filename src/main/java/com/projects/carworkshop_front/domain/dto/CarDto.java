package com.projects.carworkshop_front.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.projects.carworkshop_front.domain.Car;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CarDto {
    @JsonProperty
    private long id;
    @JsonProperty
    private String brand;
    @JsonProperty
    private String model;
    @JsonProperty
    private String manufactureYear;
    @JsonProperty
    private String vinNumber;
    @JsonProperty
    private double engineSize;
    @JsonProperty
    private String plateNumber;
    @JsonProperty
    private String bodyType;
    @JsonProperty
    private String customerId;
    @JsonProperty
    private List<RepairDto> repairDtos;

    public CarDto() {
        this.repairDtos = new ArrayList<>();
    }
}
