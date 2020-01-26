package com.projects.carworkshop_front.service;

import com.google.gson.Gson;
import com.projects.carworkshop_front.domain.dto.RepairDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class RepairService {

    RestTemplate restTemplate = new RestTemplate();

    private static RepairService repairService;
    private List<RepairDto> repairDtos;
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
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/carworkshop/api/repairs")
                .encode()
                .build()
                .toUri();
        Optional<RepairDto[]> repairs = Optional.ofNullable(restTemplate.getForObject(url,RepairDto[].class));
        repairDtos =  new ArrayList(repairs
                    .map(Arrays::asList)
                    .orElse(new ArrayList<>()));
    }

    public void save(RepairDto repairDto) {
        Gson gson = new Gson();
        String jsonContent = gson.toJson(repairDto);
        String url = "http://localhost:8080/v1/carworkshop/api/repairs";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonContent,headers);
        restTemplate.put(url,httpEntity);

    }

    public List<RepairDto> filterByCarId(long carId) {
        return repairDtos.stream()
                .filter(repair-> Long.valueOf(repair.getCarId()).equals(carId))
                .collect(Collectors.toList());
    }

    public void delete(long id) {
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/carworkshop/api/repairs/"+id)
                .encode()
                .build()
                .toUri();
        restTemplate.delete(url);
    }

}
