package com.projects.carworkshop_front.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RentRequestDto {
    @JsonProperty
    private long id;
    @JsonProperty
    private String customerName;
    @JsonProperty
    private String reqRentStartDate;
    @JsonProperty
    private String reqRentEndDate;
}

