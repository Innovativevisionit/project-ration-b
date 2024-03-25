package com.sql.authentication.serviceimplementation.process;

import com.sql.authentication.dto.TimeSlotDto;
import com.sql.authentication.exception.NotFoundException;
import com.sql.authentication.model.Location;
import com.sql.authentication.model.TimeSlot;
import com.sql.authentication.repository.LocationRepository;
import com.sql.authentication.repository.TimeSlotRepository;
import com.sql.authentication.service.process.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
public class TimeSlotServiceImpl implements TimeSlotService {
    @Autowired
    private TimeSlotRepository timeSlotRepository;
    @Autowired
    private LocationRepository locationRepository;
    public TimeSlot store(TimeSlotDto dto){
        LocalDate date=LocalDate.now();
        String loc="Cuddalore";
        Location location=locationRepository.findByName(loc).orElseThrow(()->new NotFoundException(loc + "is not found"));
        TimeSlot timeSlot=timeSlotRepository.findByDateAndLocation(date,location).orElse(new TimeSlot());
        timeSlot.setDate(date);
        timeSlot.setLocation(location);
        timeSlot.setStartTime(dto.getStartTime());
        timeSlot.setEndTime(dto.getEndTime());
        timeSlotRepository.save(timeSlot);
        return timeSlot;
    }
    public TimeSlot currentTimeSlot(){
        LocalDate date=LocalDate.now();
        String loc="Cuddalore";
        Location location=locationRepository.findByName(loc).orElseThrow(()->new NotFoundException(loc + "is not found"));
        return timeSlotRepository.findByDateAndLocation(date,location).orElse(null);
    }
    public List<TimeSlot> list(){
        String loc="Cuddalore";
        Location location=locationRepository.findByName(loc).orElseThrow(()->new NotFoundException(loc + "is not found"));
        return timeSlotRepository.findByLocation(location);
    }
}
