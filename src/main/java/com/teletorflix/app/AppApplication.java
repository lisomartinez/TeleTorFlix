package com.teletorflix.app;

import com.teletorflix.app.model.ScheduleDay;
import com.teletorflix.app.repository.ScheduleDayRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AppApplication implements CommandLineRunner {

//    @Autowired
//    private ScheduleDayRepository scheduleDayRepository;

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Override
    public void run(String... args) throws Exception {
//        scheduleDayRepository.save(ScheduleDay.of("Thursday"));
    }
}
