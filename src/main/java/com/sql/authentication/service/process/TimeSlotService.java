package com.sql.authentication.service.process;

import com.sql.authentication.dto.TimeSlotDto;
import com.sql.authentication.model.TimeSlot;

import java.util.List;

public interface TimeSlotService {
    TimeSlot store(TimeSlotDto dto);
    TimeSlot currentTimeSlot();
    List<TimeSlot> list();

}
