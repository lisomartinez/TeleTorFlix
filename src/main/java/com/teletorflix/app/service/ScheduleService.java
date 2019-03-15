package com.teletorflix.app.service;

import com.teletorflix.app.model.Schedule;

public interface ScheduleService {
    Schedule getScheduleWithSavedDays(Schedule schedule);
}
