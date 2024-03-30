package com.sql.authentication.dto;

import lombok.Data;

import java.time.LocalTime;
@Data
public class TimeSlotDto {
    private String startTime;
    private String endTime;

}
