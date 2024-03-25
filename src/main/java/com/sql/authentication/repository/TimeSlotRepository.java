package com.sql.authentication.repository;

import com.sql.authentication.model.Location;
import com.sql.authentication.model.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TimeSlotRepository extends JpaRepository<TimeSlot,Integer> {
    Optional<TimeSlot> findByDateAndLocation(LocalDate date, Location location);
    List<TimeSlot> findByLocation(Location location);

}
