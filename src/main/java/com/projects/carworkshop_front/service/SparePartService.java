package com.projects.carworkshop_front.service;

import com.google.gson.Gson;
import com.projects.carworkshop_front.config.AppConfig;
import com.projects.carworkshop_front.domain.dto.SparePartDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

@Component
public class SparePartService {

    RestTemplate restTemplate = new RestTemplate();
    AppConfig appConfig = new AppConfig();
    private static SparePartService sparePartService;
    private List<SparePartDto> sparePartDtos;

    private SparePartService() {
    }

    public static SparePartService getInstance() {
        if (sparePartService==null) {
            sparePartService = new SparePartService();
        }
        return sparePartService;
    }

    public Set<SparePartDto> getSparePartDtos() {
        return new HashSet<>(sparePartDtos);
    }

    public void fetchAll() {
        URI url = UriComponentsBuilder.fromHttpUrl(appConfig.getBackendEndpoint()+"spares")
                .encode()
                .build()
                .toUri();
        Optional<SparePartDto[]> spareParts = Optional.ofNullable(restTemplate.getForObject(url,SparePartDto[].class));
        sparePartDtos = new ArrayList<>(spareParts
                    .map(Arrays::asList)
                    .orElse(new ArrayList<>()));
    }

    public void save(SparePartDto sparePartDto) {
        Gson gson = new Gson();
        String jsonContent = gson.toJson(sparePartDto);
        String url = appConfig.getBackendEndpoint()+"spares";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonContent,headers);
        restTemplate.postForObject(url,httpEntity, Void.class);
    }

    public void delete(long id) {
        URI url = UriComponentsBuilder.fromHttpUrl(appConfig.getBackendEndpoint()+"spares/"+id)
                .encode()
                .build()
                .toUri();
        restTemplate.delete(url);
    }

}
