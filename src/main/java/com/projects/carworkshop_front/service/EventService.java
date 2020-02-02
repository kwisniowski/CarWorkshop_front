package com.projects.carworkshop_front.service;

import com.projects.carworkshop_front.domain.ApplicationEvent;
import com.projects.carworkshop_front.domain.dto.ApplicationEventDto;
import com.projects.carworkshop_front.domain.dto.CustomerDto;
import com.projects.carworkshop_front.domain.dto.RepairDto;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

public class EventService {

    RestTemplate restTemplate = new RestTemplate();
    private static EventService eventService;
    private List<ApplicationEventDto> eventDtos;

    private EventService() {
    }

    public static EventService getInstance() {
        if (eventService == null) {
            eventService = new EventService();
        }
        return eventService;
    }

    public Set<ApplicationEventDto> getEvents () {
        return new HashSet<>(eventDtos);
    }

    public void fetchAll() {
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/carworkshop/api/events")
                .encode()
                .build()
                .toUri();
        Optional<ApplicationEventDto[]> repairs = Optional.ofNullable(restTemplate.getForObject(url,ApplicationEventDto[].class));
        eventDtos =  new ArrayList(repairs
                .map(Arrays::asList)
                .orElse(new ArrayList<>()));
    }
}
