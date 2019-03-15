package com.teletorflix.app.service;

import com.teletorflix.app.exceptions.InvalidScheduleDayException;
import com.teletorflix.app.model.Schedule;
import com.teletorflix.app.model.ScheduleDay;
import com.teletorflix.app.repository.ScheduleDayRepository;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private ScheduleDayRepository scheduleDayRepository;

    @Autowired
    public ScheduleServiceImpl(ScheduleDayRepository scheduleDayRepository) {
        this.scheduleDayRepository = scheduleDayRepository;
    }

    @Override
    public Schedule getScheduleWithSavedDays(Schedule schedule) {
        LogManager.getLogger().info("---------------->" + schedule);
        LogManager.getLogger().info("---------------------------->  " + scheduleDayRepository.findAll().toString());
        Set<ScheduleDay> days = schedule.getDays().stream()
                .map(ScheduleDay::getDay)
                .map(scheduleDayRepository::findByDay)
                .flatMap(optional -> optional.map(Stream::of).orElseThrow(InvalidScheduleDayException::new))
                .collect(Collectors.toSet());
        schedule.setDays(days);
        return schedule;
    }
}
