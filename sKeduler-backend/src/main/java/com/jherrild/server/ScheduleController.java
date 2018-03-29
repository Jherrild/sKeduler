package com.jherrild.server;

import com.jherrild.server.entity.Calendar;
import com.jherrild.server.entity.Event;
import com.jherrild.server.persistence.CalendarRepository;
import com.jherrild.server.persistence.EventRepository;
import lombok.extern.slf4j.Slf4j;
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

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
@Slf4j
public class ScheduleController {
    @Autowired
    private CalendarRepository calendarRepository;
    @Autowired
    private EventRepository eventRepository;


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

    /**
     * Creates a calendar if one does not already exist under the given name
     * @param calendarName
     * @param newCalendar
     * @return
     */
    @RequestMapping(value="/calendars/{calendarName}", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<Calendar> createCalendar(@PathVariable String calendarName, @RequestBody(required = false) Calendar newCalendar) {
        HttpStatus responseStatus = HttpStatus.CREATED;
        ResponseEntity<Calendar> found = getCalendar(calendarName);

        if(found.getBody() == null) { //Calendar name is unique
            if(newCalendar != null) {
                if(!StringUtils.isEmpty(calendarName) && !calendarName.equals(newCalendar.getName())) {
                    return new ResponseEntity<>(newCalendar, HttpStatus.BAD_REQUEST);
                }
            }else {
                newCalendar = new Calendar(calendarName);
            }
            calendarRepository.save(newCalendar);
        }else { //Calendar name is not unique
            responseStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        }

        return new ResponseEntity<>(newCalendar, responseStatus);
    }

    @RequestMapping(value="/calendars/{calendarName}/events/{timestamp}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<Event>> getEventsByCalendarAndDate(@PathVariable("calendarName") String calendarName, @PathVariable("timestamp") long timestamp) {
        HttpStatus status = HttpStatus.OK;
        ArrayList<Event> events = new ArrayList<>();
        Calendar calendar = calendarRepository.findByName(calendarName);

        LocalDate date = new Date(timestamp).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        long start = date.atTime(0,0).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long end = date.atTime(23,59, 59, 999999999).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        if(calendar != null) {
            events.addAll(eventRepository.findAllByCalendarIdAndStartTimeBetween(calendar.getId(), start, end));
        }else {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(events, status);
    }

    @RequestMapping(value="/calendars/{calendarName}/events/test", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<Event> createTestEvent(@PathVariable("calendarName") String calendarName, @RequestBody(required = false) Event event) {
        Calendar calendar = calendarRepository.findByName(calendarName);

        if(calendar != null) {
            Event testEvent = new Event.EventBuilder()
                    .calendarId(calendar.getId()).clientName("John Doe")
                    .startTime(Instant.now().toEpochMilli())
                    .endTime(Instant.now().toEpochMilli() + 3600000)
                    .length(3600000)
                    .description("description")
                    .build();

            return createEvent(calendarName, testEvent);
        }

        return new ResponseEntity<>(new Event(), HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value="/calendars/{calendarName}/events", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<Event> createEvent(@PathVariable("calendarName") String calendarName, @RequestBody Event event) {
        HttpStatus status = HttpStatus.CREATED;
        Calendar calendar = calendarRepository.findByName(calendarName);

        if(calendar != null) {
            if(eventRepository.countConflicts(calendar.getId(), event.getStartTime(), event.getEndTime()) != null) { //Conflicts found
                status = HttpStatus.CONFLICT;
            }else { //Schedule
                try {
                    eventRepository.save(event);
                }catch (Exception e) {
                    log.error("Event could not be saved to database", e);
                    status = HttpStatus.INTERNAL_SERVER_ERROR;
                }
            }
        }else {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(event, status);
    }


    private @ResponseBody
    ResponseEntity<Calendar> notFound() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
