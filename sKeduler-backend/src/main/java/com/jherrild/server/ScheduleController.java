package com.jherrild.server;

import com.jherrild.server.entity.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
    @RequestMapping(value="/calendars/{calendarName}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<Calendar> getCalendar(@PathVariable String calendarName) {
        Calendar found = calendarRepository.findByName(calendarName);

        if(found != null) {
            return new ResponseEntity<>(found, HttpStatus.OK);
        }else {
            return notFound();
        }
    }

    @RequestMapping(value="/calendars/{calendarName}", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<Calendar> createCalendar(@PathVariable String calendarName, @RequestBody(required = false) Calendar newCalendar) {
        HttpStatus responseStatus = HttpStatus.CREATED;
        ResponseEntity<Calendar> found = getCalendar(calendarName);

        if(found.getBody() == null) {
            if(newCalendar != null) {
                if(!StringUtils.isEmpty(calendarName) && !calendarName.equals(newCalendar.getName())) {
                    return new ResponseEntity<>(newCalendar, HttpStatus.BAD_REQUEST);
                }
            }else {
                newCalendar = new Calendar(calendarName, new ArrayList<>());
            }
            calendarRepository.save(newCalendar);
        }else {
            responseStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        }

        return new ResponseEntity<>(newCalendar, responseStatus);
    }

    private @ResponseBody
    ResponseEntity<Calendar> notFound() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
