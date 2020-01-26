package com.projects.carworkshop_front.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubjectDto {

    @JsonProperty
    String name;
    @JsonProperty
    String nip;
    @JsonProperty
    String statusVat;
    @JsonProperty
    String regon;
    @JsonProperty
    String pesel;
    @JsonProperty
    String krs;
    @JsonProperty
    String workingAddress;
    @JsonProperty
    String[] accountNumbers;
}
