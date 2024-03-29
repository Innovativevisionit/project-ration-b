package com.sql.authentication.serviceimplementation.process;

import com.sql.authentication.dto.TimeSlotDto;
import com.sql.authentication.exception.NotFoundException;
import com.sql.authentication.model.Location;
import com.sql.authentication.model.TimeSlot;
import com.sql.authentication.model.User;
import com.sql.authentication.repository.LocationRepository;
import com.sql.authentication.repository.TimeSlotRepository;
import com.sql.authentication.repository.UserRepository;
import com.sql.authentication.service.process.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TimeSlotServiceImpl implements TimeSlotService {
    @Autowired
    private TimeSlotRepository timeSlotRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuditorAware<String> auditorAware;
    public TimeSlot store(TimeSlotDto dto){
        LocalDate date=LocalDate.now();
        Optional<String> currentAuditor = auditorAware.getCurrentAuditor();
        Optional<User> user=userRepository.findByEmail(currentAuditor.get());
//        Location location=locationRepository.findByName(loc).orElseThrow(()->new NotFoundException(loc + "is not found"));
        TimeSlot timeSlot=timeSlotRepository.findByDateAndLocation(date,user.get().getLocation()).orElse(new TimeSlot());
        timeSlot.setDate(date);
        timeSlot.setLocation(user.get().getLocation());
        timeSlot.setStartTime(dto.getStartTime());
        timeSlot.setEndTime(dto.getEndTime());
        timeSlotRepository.save(timeSlot);
        return timeSlot;
    }
    public TimeSlot currentTimeSlot(){
        LocalDate date=LocalDate.now();
        Optional<String> currentAuditor = auditorAware.getCurrentAuditor();
        Optional<User> user=userRepository.findByEmail(currentAuditor.get());
//        Location location=locationRepository.findByName(loc).orElseThrow(()->new NotFoundException(loc + "is not found"));
        return timeSlotRepository.findByDateAndLocation(date,user.get().getLocation()).orElse(null);
    }
    public List<TimeSlot> list(){
        Optional<String> currentAuditor = auditorAware.getCurrentAuditor();
        Optional<User> user=userRepository.findByEmail(currentAuditor.get());
        System.out.println(user.get().getLocation());
        return timeSlotRepository.findByLocation(user.get().getLocation());
    }
}
