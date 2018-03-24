package com.jherrild.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author jestenh@gmail.com
 * Created on 3/23/18
 */
@SpringBootApplication
public class BackendApp {
    @Autowired
    ScheduleController scheduleController;

    public static void main(String[] args) throws Exception{
        // Get Calendars from DB
        syncWithDb();
        SpringApplication.run(BackendApp.class, args);
    }

    private static void syncWithDb() {
        //NOOP
    }
}
