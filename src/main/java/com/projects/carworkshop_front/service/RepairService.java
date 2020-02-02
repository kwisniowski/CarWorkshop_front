package com.projects.carworkshop_front.service;

import com.projects.carworkshop_front.config.AppConfig;
import com.projects.carworkshop_front.domain.dto.RepairDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

public class RepairService {

    RestTemplate restTemplate = new RestTemplate();
    private JsonBuilder<RepairDto> jsonBuilder = new JsonBuilder<>();
    private AppConfig appConfig = AppConfig.getInstance();
    private static RepairService repairService;
    private List<RepairDto> repairDtos;
    public enum repairCurrency {CHF, USD, EUR, GBP}

    private RepairService() {
    }

    public static RepairService getInstance() {
        if (repairService == null) {
            repairService = new RepairService();
        }
        return  repairService;
    }

    public Set<RepairDto> getRepairs() {
        return new HashSet<>(repairDtos);
    }

    public void fetchAll() {
        URI url = UriComponentsBuilder.fromHttpUrl(appConfig.getBackendEndpoint()+"repairs")
                .encode()
                .build()
                .toUri();
        Optional<RepairDto[]> repairs = Optional.ofNullable(restTemplate.getForObject(url,RepairDto[].class));
        repairDtos =  new ArrayList(repairs
                    .map(Arrays::asList)
                    .orElse(new ArrayList<>()));
    }

    public void save(RepairDto repairDto) {
        String url = appConfig.getBackendEndpoint()+"repairs";
        restTemplate.postForObject(url,jsonBuilder.prepareJson(repairDto), Void.class);
    }

    public void update(RepairDto repairDto) {
        String url = appConfig.getBackendEndpoint()+"repairs";
        restTemplate.put(url,jsonBuilder.prepareJson(repairDto));
    }

    public List<RepairDto> filterByCarId(long carId) {
        return repairDtos.stream()
                .filter(repair-> Long.valueOf(repair.getCarId()).equals(carId))
                .collect(Collectors.toList());
    }

    public void delete(long id) {
        URI url = UriComponentsBuilder.fromHttpUrl(appConfig.getBackendEndpoint()+"repairs/"+id)
                .encode()
                .build()
                .toUri();
        restTemplate.delete(url);
    }

    public double getCurrencyFactorFromNBP(String currencyCode) {
        URI url = UriComponentsBuilder.fromHttpUrl(appConfig.getBackendEndpoint()+"currency/"+currencyCode)
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
