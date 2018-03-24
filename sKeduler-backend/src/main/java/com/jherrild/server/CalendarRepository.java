package com.jherrild.server;

import com.jherrild.entity.Calendar;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 * @author jestenh@gmail.com
 * Created on 3/24/18
 */
@ComponentScan(basePackages = {"com.jherrild.entity"})
@EntityScan(basePackageClasses=com.jherrild.entity.Calendar.class)
@EnableAutoConfiguration
@Component
public interface CalendarRepository extends CrudRepository<Calendar, Long> {
    Calendar findByName(String name);
}
