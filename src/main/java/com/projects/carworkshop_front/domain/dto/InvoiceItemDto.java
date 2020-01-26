package com.projects.carworkshop_front.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceItemDto {

    @JsonProperty
    private long id;
    @JsonProperty
    private long sparePartId;
    @JsonProperty
    private int quantity;
    @JsonProperty
    private long invoiceId;
}