package com.projects.carworkshop_front.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class RepairDto {
        @JsonProperty
        private long id;
        @JsonProperty
        private String carId;
        @JsonProperty
        private String startDate;
        @JsonProperty
        private String endDate;
        @JsonProperty
        private double totalCost;

}

