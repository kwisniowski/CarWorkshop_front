package com.projects.carworkshop_front.domain.dto;

import com.projects.carworkshop_front.domain.ApplicationEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationEventDto {
    private enum EventType {SEND,CREATED,DELETED,UPDATED}

    private long id;
    private String type;
    private String date;
    private String time;
    private String info;
}
