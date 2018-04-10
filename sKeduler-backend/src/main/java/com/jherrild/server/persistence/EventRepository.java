package com.jherrild.server.persistence;

import com.jherrild.server.entity.Event;
import io.micrometer.core.lang.Nullable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @author jestenh@gmail.com
 * Created on 3/24/18
 */
@Component
public interface EventRepository extends CrudRepository<Event, Long> {

    Event findById(long id);

    ArrayList<Event> findAllByCalendarIdAndStartTimeBetween(long calendarId, long start, long end);

    @Nullable
    @Query("SELECT e FROM Event e WHERE e.calendarId = :calendarId AND ((e.startTime <= :startTime AND e.endTime > :startTime) OR ( e.startTime >= :startTime AND e.startTime <= :endTime))")
    Event countConflicts(@Param("calendarId") long calendarId,
                         @Param("startTime") long startTime,
                         @Param("endTime") long endTime);

}
