package com.sql.authentication.dto;

import lombok.Data;

import java.time.LocalTime;
@Data
public class TimeSlotDto {
    private LocalTime startTime;
    private LocalTime endTime;

}
