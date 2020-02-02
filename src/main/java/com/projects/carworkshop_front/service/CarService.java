package com.projects.carworkshop_front.service;

import com.google.gson.Gson;
import com.projects.carworkshop_front.config.AppConfig;
import com.projects.carworkshop_front.domain.dto.CarDto;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

public class CarService {

    private RestTemplate restTemplate = new RestTemplate();
    private AppConfig appConfig = AppConfig.getInstance();
    private JsonBuilder<CarDto> jsonBuilder = new JsonBuilder<>();

    private List<CarDto> carDtos;
    private static CarService carservice;

    private CarService () {
    }

    public static CarService getInstance() {
        if (carservice == null) {
            carservice = new CarService();
        }
        return carservice;
    }

    public Set<CarDto> getCarDtos() {
        return new HashSet<>(carDtos);
    }

    public void fetchAll() {
        URI url = UriComponentsBuilder.fromHttpUrl(appConfig.getBackendEndpoint()+"cars")
                .encode()
                .build()
                .toUri();
        Optional<CarDto[]> cars = Optional.ofNullable(restTemplate.getForObject(url,CarDto[].class));
        carDtos = new ArrayList<>(cars
                .map(Arrays::asList)
                .orElse(new ArrayList<>()));
    }


    public List<CarDto> filterByPlateNumber (String filterString) {
        return carDtos.stream()
                .filter(c->c.getPlateNumber().contains(filterString))
                .collect(Collectors.toList());
    }

    public List<CarDto> filterByVinNumber (String filterString) {
        return carDtos.stream()
                .filter(c->c.getVinNumber().contains(filterString))
                .collect(Collectors.toList());
    }

    public List<CarDto> filterByCustomerId (String id) {
        return carDtos.stream()
                .filter(c->c.getCustomerId().equals(id))
                .collect(Collectors.toList());
    }

    public void save(CarDto carDto) {
        String url = appConfig.getBackendEndpoint()+"cars";
        restTemplate.postForObject(url,(carDto),Void.class);
    }

    public void update(CarDto carDto) {
        String url = appConfig.getBackendEndpoint()+"cars";
        restTemplate.put(url,jsonBuilder.prepareJson(carDto));
    }

    public void delete(long id) {
        URI url = UriComponentsBuilder.fromHttpUrl(appConfig.getBackendEndpoint()+"cars/"+id)
                .encode()
                .build()
                .toUri();
        restTemplate.delete(url);
    }
}
