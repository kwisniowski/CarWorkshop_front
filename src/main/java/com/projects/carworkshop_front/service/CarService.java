package com.projects.carworkshop_front.service;

import com.google.gson.Gson;
import com.projects.carworkshop_front.config.AppConfig;
import com.projects.carworkshop_front.domain.dto.CarDto;
import com.projects.carworkshop_front.domain.dto.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CarService {

    RestTemplate restTemplate = new RestTemplate();
    AppConfig appConfig = new AppConfig();

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

    public void addCar (CarDto carDto) {
        this.carDtos.add(carDto);
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
        Gson gson = new Gson();
        String jsonContent = gson.toJson(carDto);
        String url = "http://localhost:8080/v1/carworkshop/api/cars";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonContent,headers);
        restTemplate.put(url,httpEntity);
    }

    public void delete(long id) {
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/carworkshop/api/cars/"+id)
                .encode()
                .build()
                .toUri();
        restTemplate.delete(url);
    }

}
