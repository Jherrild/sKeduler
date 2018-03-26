package com.jherrild.server;

import com.jherrild.server.entity.Calendar;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 * @author jestenh@gmail.com
 * Created on 3/24/18
 */
@Component
public interface CalendarRepository extends CrudRepository<Calendar, Long> {
    Calendar findByName(String name);
}
