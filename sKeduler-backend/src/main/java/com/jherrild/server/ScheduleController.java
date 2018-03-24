package com.jherrild.server;

import com.jherrild.server.entity.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.StreamSupport;

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
    private Calendar badCalendar = new Calendar();

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
    Map<String, Calendar> getAllCalendars() {
        ConcurrentHashMap<String, Calendar> calendars = new ConcurrentHashMap<>();
        StreamSupport.stream(calendarRepository.findAll().spliterator(), true)
                .forEach(c -> calendars.put(c.getName(), c));

        return calendars;
    }

    /**
     * Returns a Calendar if one already exists with the specified name
     * @return Calendar
     */
    @RequestMapping(value="/calendar/{calendarName}", method = RequestMethod.GET)
    public @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    Calendar getCalendar(@PathVariable String calendarName) {
        return calendarRepository.findByName(calendarName);
    }

    @RequestMapping(value="/calendar/create/{calendarName}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Calendar createCalendar(@PathVariable String calendarName) {
        Calendar found = getCalendar(calendarName);
        if(found == null) {
            Calendar createdCalendar = new Calendar(calendarName, new ArrayList<>());
            calendarRepository.save(createdCalendar);
            return createdCalendar;
        }else {
            return found;
        }
    }

    /*
    @RequestMapping(value="calendar/{calendarName}/events/{newEvent}", method = RequestMethod.GET)
    public @ResponseBody
    Event addEvent(@PathVariable String calendarName, @PathVariable Event newEvent) {
        if(calendars.containsKey(calendarName)) {
            Calendar updated = calendars.get(calendarName);
            //ADD EVENTS TO CALENDAR
            calendars.replace(calendarName, updated);

        }
        return null;
    }
    */

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
