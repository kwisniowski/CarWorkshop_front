package com.projects.carworkshop_front.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerDto {
        @JsonProperty
        private long id;
        @JsonProperty
        private String firstname;
        @JsonProperty
        private String lastname;
        @JsonProperty
        private String company;
        @JsonProperty
        private String nipNumber;
        @JsonProperty
        private String accountNumber;
        @JsonProperty
        private String regonNumber;
        @JsonProperty
        private String emailAddress;
        @JsonProperty
        private String phoneNumber;
        @JsonProperty
        private boolean vipCustomer;
        @JsonProperty
        private boolean companyCustomer;
        @JsonProperty
        private List<CarDto> carDtos;
        @JsonProperty
        private List<InvoiceDto> invoiceDtos;

        public CustomerDto() {
                this.carDtos = new ArrayList<>();
                this.invoiceDtos = new ArrayList<>();
        }
}
