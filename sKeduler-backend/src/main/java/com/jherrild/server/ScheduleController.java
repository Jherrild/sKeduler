package com.jherrild.server;

import com.jherrild.server.entity.Calendar;
import com.jherrild.server.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * @author jestenh@gmail.com
 * Created on 3/22/18
 */
@RestController
@EnableAutoConfiguration
@RequestMapping("/skeduler")
public class ScheduleController {
    @Autowired
    CalendarRepository calendarRepository;
    private HashMap<String, Calendar> calendars = new HashMap<>();

    /**
     * Test endpoint to see if controller is being mapper and served properly
     * @return
     */
    @RequestMapping(value="/test", method = RequestMethod.GET)
    public @ResponseBody
    String test() {
        return "test";
    }


    /**
     * Returns an object representation of all calendars
     * @return HashMap<String, Calendar>
     */
    @RequestMapping(value="/calendars", method = RequestMethod.GET)
    public @ResponseBody
    HashMap<String, Calendar> getAllCalendars() {
        return calendars;
    }

    /**
     * Returns a Calendar if one already exists with the specified name- otherwise creates one
     * @return Calendar
     */
    @RequestMapping(value="/calendar/{calendarName}", method = RequestMethod.GET)
    public @ResponseBody
    Calendar getCalendar(@PathVariable String calendarName) {
        if(calendars.containsKey(calendarName)) {
            return calendars.get(calendarName);
        }else {
            Calendar createdCalendar = new Calendar(calendarName, new ArrayList<>());
            calendars.put(calendarName, createdCalendar);
            return createdCalendar;
        }
    }

    @RequestMapping(value="calendar/{calendarName}/events/{newEvent}", method = RequestMethod.GET)
    public @ResponseBody
    Event addEvent(@PathVariable String calendarName, @PathVariable String newEvent) {
        if(calendars.containsKey(calendarName)) {
            Calendar updated = calendars.get(calendarName);
            //ADD EVENTS TO CALENDAR
            calendars.replace(calendarName, updated);

        }
        return null;
    }

    /**
     * Returns a boolean representation of whether there are conflicts with the given event
     * @return
     */
    @RequestMapping(value="/conflicts/{calendar_name}/{time}", method = RequestMethod.GET)
    public @ResponseBody
    boolean getConflicts(@PathVariable Date time) {
        return false;
    }
}
