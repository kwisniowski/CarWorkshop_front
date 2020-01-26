package com.projects.carworkshop_front.service;

import com.projects.carworkshop_front.domain.dto.CustomerDto;
import com.projects.carworkshop_front.domain.dto.InvoiceDto;
import com.projects.carworkshop_front.domain.dto.NbpApiResponseDto;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.management.Notification;
import java.net.URI;
import java.security.cert.PKIXRevocationChecker;
import java.util.*;
import java.util.stream.Collectors;

public class InvoiceService {

    private List<InvoiceDto> invoiceDtos;
    private static InvoiceService invoiceService;
    private RestTemplate restTemplate = new RestTemplate();

    public enum invoicePaid {PAID, UNPAID, ALL};
    public enum invoiceCurrency {CHF, USD, EUR, GBP}

    private InvoiceService() {
    }

    public static InvoiceService getInstance() {
        if (invoiceService == null) {
            invoiceService = new InvoiceService();
        }
        return invoiceService;
    }

    public Set<InvoiceDto> getInvoices() {
        return new HashSet<>(invoiceDtos);
    }

    public void fetchAll() {
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/carworkshop/api/invoices")
                .encode()
                .build()
                .toUri();
        Optional<InvoiceDto[]> invoices = Optional.ofNullable(restTemplate.getForObject(url, InvoiceDto[].class));
        invoiceDtos = new ArrayList<>(invoices
                .map(Arrays::asList)
                .orElse(new ArrayList<>()));
    }

    public List<InvoiceDto> filerByPaymentCondition(Boolean condition) {
        if (condition) {
            return invoiceDtos.stream()
                    .filter(invoice->invoice.isPaid())
                    .collect(Collectors.toList());
        }
        else {
            return invoiceDtos.stream()
                    .filter(invoice-> !invoice.isPaid())
                    .collect(Collectors.toList());
        }
    }

    public double getCurrencyFactorFromNBP(String currencyCode) {
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/carworkshop/nbpapi/rates/"+currencyCode)
                .encode()
                .build()
                .toUri();
        try {
            return restTemplate.getForObject(url,Double.class);
        }
        catch (ServerErrorException e) {
            return 0.0;
        }

    }

}
