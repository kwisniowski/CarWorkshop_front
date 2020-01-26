package com.projects.carworkshop_front.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceDto {
    @JsonProperty
    private long id;
    @JsonProperty
    private long customerId;
    @JsonProperty
    private int paymentPeriod;
    @JsonProperty
    private LocalDate paymentLimitDate;
    @JsonProperty
    private boolean paid;
    @JsonProperty
    private Double totalCost;
    @JsonProperty
    private long repairId;
    @JsonProperty
    private List<InvoiceItemDto> invoiceItemDtos;
}
