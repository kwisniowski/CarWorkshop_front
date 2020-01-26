package com.projects.carworkshop_front.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceCarRentDto {

    private long id;

    @JsonSerialize(using = ToStringSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonSerialize(using = ToStringSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
}
